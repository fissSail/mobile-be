package com.ruoyi.mobile.reserveExamine.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.mobile.entity.*;
import com.ruoyi.common.mobile.enums.CommonlyUsedEnum;
import com.ruoyi.common.utils.CodeGenerateUtil;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.uuid.IdUtils;

import com.ruoyi.mobile.medicalPackage.mapper.BaseExaminePackageMapper;
import com.ruoyi.mobile.medicalPackage.mapper.BaseExamineRecordApplicationMapper;
import com.ruoyi.mobile.medicalPackage.mapper.BaseExamineRecordCombiProjectMapper;
import com.ruoyi.mobile.medicalPackage.vo.BaseCombiProjectVO;
import com.ruoyi.mobile.medicalPackage.vo.ExamineProjectNameDetailsVO;
import com.ruoyi.mobile.reserveExamine.dto.ExamineFinishListDTO;
import com.ruoyi.mobile.reserveExamine.dto.ReserveExamineDTO;
import com.ruoyi.mobile.reserveExamine.mapper.BaseAbandonCheckRecordMapper;
import com.ruoyi.mobile.reserveExamine.mapper.BaseExamineRecordMapper;
import com.ruoyi.mobile.reserveExamine.mapper.BaseExamineRecordProjectMapper;
import com.ruoyi.mobile.generateCode.service.IBaseCodingService;
import com.ruoyi.mobile.reserveExamine.service.ReserveExamineService;
import com.ruoyi.mobile.reserveExamine.vo.ExamineFinishListVO;
import com.ruoyi.mobile.reserveExamine.vo.ExamineRecordVO;
import com.ruoyi.mobile.reserveExamine.vo.PortfolioProjectInfoVO;
import com.ruoyi.mobile.userInfo.mapper.BasePersonRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ??????????????????
 */
@Service
@RequiredArgsConstructor
public class ReserveExamineServiceImpl implements ReserveExamineService {


    private final BasePersonRecordMapper basePersonRecordMapper;

    private final BaseExamineRecordMapper baseExamineRecordMapper;

    private final BaseExaminePackageMapper baseExaminePackageMapper;

    private final IBaseCodingService iBaseCodingService;

    private final BaseExamineRecordCombiProjectMapper baseExamineRecordCombiProjectMapper;

    private final BaseExamineRecordProjectMapper baseExamineRecordProjectMapper;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    private final BaseAbandonCheckRecordMapper baseAbandonCheckRecordMapper;

    private final BaseExamineRecordApplicationMapper baseExamineRecordApplicationMapper;

    final String lockKey = "lockKey";


    @Value("${ruoyi.occupationalDisease}")
    public String occupationalDisease;

    /**
     * ????????????????????????
     *
     * @param reserveExamineDTO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public AjaxResult addExamineRecord(ReserveExamineDTO reserveExamineDTO) {
        //??????????????????????????????????????????
        isReserve();
        Date examineDate = DateUtils.parseDate(reserveExamineDTO.getExamineDate());
        //????????????????????????
        BaseExaminePackageDO baseExaminePackageDO = getBaseExaminePackageDO(reserveExamineDTO);
        //??????: setnx
        try {
            if (isSuccess()) {
                List<BaseCombiProjectVO> baseCombiProjectList = baseExaminePackageMapper
                        .findByCombiProject(reserveExamineDTO.getBaseExaminePackageId());
                if (baseCombiProjectList.isEmpty()) {
                    return AjaxResult.error("??????????????????????????????");
                }
                //??????????????????
                String baseExamineRecordId = getBaseExamineRecord(reserveExamineDTO, baseCombiProjectList, baseExaminePackageDO.getDiscount());
                //?????????????????????

                Map<String, Integer> combiProjectNumberMap = baseCombiProjectList.stream()
                        .distinct().collect(Collectors.toMap(BaseCombiProjectVO::getId, t -> t.getNumber() == null ? 0 : t.getNumber()));

                List<String> combiProjectList = new ArrayList<>(combiProjectNumberMap.keySet());

                //?????????????????????????????????????????????
                appointmentCheck(reserveExamineDTO, combiProjectNumberMap, combiProjectList, baseExaminePackageDO.getId(), examineDate);

                //???????????????????????????????????????
                Map<String, BaseCombiProjectVO> combiProjectMap = insertCombiProject(baseCombiProjectList, baseExaminePackageDO, baseExamineRecordId);

                //????????????????????????
                List<ExamineProjectNameDetailsVO> examineProjectNameDetailsVOList = new ArrayList<>();
                for (BaseCombiProjectVO baseCombiProjectVO : baseCombiProjectList) {
                    examineProjectNameDetailsVOList.addAll(baseCombiProjectVO.getExamineProjectNameDetailsVOS());
                }
                //????????????????????????
                insertExamineProject(combiProjectMap, examineProjectNameDetailsVOList, baseExamineRecordId);
                // ??????????????????????????????????????????Map????????????????????????ID???key????????????value
                Map<String, String> barCodeMap = generateBarCode(baseExamineRecordId, baseCombiProjectList);
                if (!barCodeMap.isEmpty()) {
                    insertBaseExamineRecordApplication(baseExamineRecordId, examineProjectNameDetailsVOList, barCodeMap);
                }
                packageSalesNumber(reserveExamineDTO);
            } else {
                throw new RuntimeException("????????????!????????????");
            }

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            redisTemplate.delete(lockKey);
        }
        return AjaxResult.success();
    }

    /**
     * ??????????????????????????????
     */
    private void isReserve() {
        if (baseExamineRecordMapper.selectCount(new QueryWrapper<BaseExamineRecordDO>()
                .eq("person_record_id", SecurityUtils.getMobileUserId())
                .eq("is_end_examine", CommonlyUsedEnum.NOT_END_EXAMINE.val())
                .ge("examine_date",new Date())
                .eq("del_flag", CommonlyUsedEnum.NOT_DELETE.val())) >= 1) {
            throw new RuntimeException("??????????????????,???????????????????????????!");
        }
    }

    /**
     * ???????????????
     *
     * @return ??????????????????
     * @throws InterruptedException
     */
    private Boolean isSuccess() throws InterruptedException {
        Boolean isSuccess = false;
        int time = 0;
        do {
            Thread.sleep(30);
            isSuccess = redisTemplate.opsForValue().setIfAbsent(lockKey, "1");
            time++;
        } while (time <= 10 && !isSuccess);
        return isSuccess;
    }

    /**
     * ??????????????????
     *
     * @param baseExamineRecordId
     * @param examineProjectNameDetailsVOList
     * @param barCodeMap
     */
    private void insertBaseExamineRecordApplication(String baseExamineRecordId, List<ExamineProjectNameDetailsVO> examineProjectNameDetailsVOList, Map<String, String> barCodeMap) {
        List<BaseExamineRecordApplicationDO> examineRecordApplicationVos = new ArrayList<>();
        // ???????????????????????????????????????
        for (int k = 0; k < examineProjectNameDetailsVOList.size(); k++) {
            ExamineProjectNameDetailsVO examineProjectNameDetailsVO = examineProjectNameDetailsVOList.get(k);
            String combiProjectId = examineProjectNameDetailsVO.getCombiProjectId();
            String barCode = barCodeMap.get(combiProjectId);
            if (StringUtils.isEmpty(barCode)) {
                continue;
            }
            baseExamineRecordApplicationList(examineRecordApplicationVos, barCode, combiProjectId, examineProjectNameDetailsVO, baseExamineRecordId);
        }// ??????????????????
        Integer i = baseExamineRecordApplicationMapper.insertBaseExamineRecordApplicationByBatch(examineRecordApplicationVos);
        if (i != examineRecordApplicationVos.size()) {
            throw new RuntimeException("?????????????????????!");
        }
    }

    /**
     * ??????????????????
     *
     * @param reserveExamineDTO
     */
    private void packageSalesNumber(ReserveExamineDTO reserveExamineDTO) {
        BoundHashOperations<String, Object, Object> packageSales = redisTemplate.boundHashOps("packageSales");
        Integer sales = (Integer) packageSales.get(reserveExamineDTO.getBaseExaminePackageId());
        if (sales == null) {
            packageSales.put(reserveExamineDTO.getBaseExaminePackageId(), 1);
        } else {
            packageSales.put(reserveExamineDTO.getBaseExaminePackageId(), 1 + sales);
        }
    }


    /**
     * ?????????????????????
     *
     * @param examineDate      ??????????????????
     * @param combiProjectList ????????????
     * @return
     */
    private List<BaseAbandonCheckRecordDO> getDelayTimeNumber(Date examineDate, List<String> combiProjectList) {
        return baseAbandonCheckRecordMapper.selectList(new QueryWrapper<BaseAbandonCheckRecordDO>()
                .eq("del_flag", CommonlyUsedEnum.NOT_DELETE.val())
                .eq("abandon_type", "01")
                .eq("delay_time", DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, examineDate))
                .in("combi_project_id", combiProjectList));

    }

    /**
     * ??????????????????
     *
     * @param reserveExamineDTO
     * @param combiProjectNumberMap ??????id
     * @param baseExaminePackageId  ???????????????id
     * @param examineDate           ?????????????????????
     */
    private void appointmentCheck(ReserveExamineDTO reserveExamineDTO, Map<String, Integer> combiProjectNumberMap, List<String> combiProjectList, String baseExaminePackageId, Date examineDate) {
        //???????????????????????????????????????????????????
        List<PortfolioProjectInfoVO> portfolioProjectInfoVOList = baseExamineRecordCombiProjectMapper
                .portfolioProjectInfo(baseExaminePackageId, examineDate);

        //????????????????????????
        List<BaseAbandonCheckRecordDO> delayNumberList = getDelayTimeNumber(examineDate, combiProjectList);

        Set<String> isExceed = new HashSet<>();

        Map<String, Integer> remainMap = new HashMap<>(combiProjectList.size());
        if (!delayNumberList.isEmpty()) {
            Map<String, List<BaseAbandonCheckRecordDO>> delayTimeNumberMap = delayNumberList
                    .stream()
                    .collect(Collectors.groupingBy(t -> t.getCombiProjectId()));
            for (String combiProjectId : combiProjectList) {
                //???????????????????????????
                List<BaseAbandonCheckRecordDO> baseAbandonCheckRecordDOList = delayTimeNumberMap.get(combiProjectId);
                //????????????????????????
                Integer integer = combiProjectNumberMap.get(combiProjectId);
                //?????????????????????????????????????????????
                if (integer <= baseAbandonCheckRecordDOList.size()) {
                    isExceed.add(combiProjectId);
                } else {
                    remainMap.put(combiProjectId, baseAbandonCheckRecordDOList.size());
                }
            }

            check(reserveExamineDTO, combiProjectList, isExceed);
        }

        if (!portfolioProjectInfoVOList.isEmpty()) {
            portfolioProjectInfoVOList.stream().forEach(t ->
            {
                //????????????????????????
                if (CommonlyUsedEnum.EXCEED.val().equals(t.getIsExceed())) {
                    isExceed.add(t.getCombiProjectId());
                } else {
                    //????????????????????????????????????????????????
                    Integer integer = remainMap.get(t.getCombiProjectId());
                    if (integer != null) {
                        if (t.getProjectNumber() + integer >= t.getNumber()) {
                            isExceed.add(t.getCombiProjectId());
                        }
                    }
                }
            });
        }
        check(reserveExamineDTO, combiProjectList, isExceed);

    }

    /**
     * ??????
     *
     * @param reserveExamineDTO
     * @param combiProjectList  ????????????
     * @param isExceed          ???????????????
     */
    private void check(ReserveExamineDTO reserveExamineDTO, List<String> combiProjectList, Set<String> isExceed) {
        if (isExceed.size() != 0) {
            if (isExceed.size() == combiProjectList.size()) {
                //???1?????????????????????????????????????????????????????????
                throw new RuntimeException("1");
            } else if (isExceed.size() < combiProjectList.size() && CommonlyUsedEnum.FULL.val().equals(reserveExamineDTO.getIsFull())) {
                //???2??????????????????????????????????????????????????????????????????
                throw new RuntimeException("2");
            }
        }
    }

    /**
     * ??????????????????
     *
     * @param reserveExamineDTO
     * @return
     */
    private BaseExaminePackageDO getBaseExaminePackageDO(ReserveExamineDTO reserveExamineDTO) {
        //????????????????????????
        BaseExaminePackageDO baseExaminePackageDO = baseExaminePackageMapper
                .selectOne(new QueryWrapper<BaseExaminePackageDO>()
                        .eq("id", reserveExamineDTO.getBaseExaminePackageId())
                        .eq("del_flag", CommonlyUsedEnum.NOT_DELETE.val()));
        if (baseExaminePackageDO == null) {
            throw new RuntimeException("??????????????????????????????????????????");
        }
        return baseExaminePackageDO;
    }


    /**
     * ????????????
     *
     * @return
     */
    private void baseExamineRecordApplicationList(List<BaseExamineRecordApplicationDO> examineRecordApplicationVos, String barCode, String combiProjectId, ExamineProjectNameDetailsVO examineProjectNameDetailsVO, String baseExamineRecordId) {
        examineRecordApplicationVos.add(BaseExamineRecordApplicationDO.builder()
                .examineRecordId(baseExamineRecordId)
                .barcode(barCode)
                .id(IdUtils.simpleUUID())
                .createBy(SecurityUtils.getMobileUsername())
//                .createId(SecurityUtils.getMobileUserId())
                .createTime(new Date())
                .combiProjectId(combiProjectId)
                .combiProjectName(examineProjectNameDetailsVO.getProjectName())
                .projectId(examineProjectNameDetailsVO.getProjectId())
                .projectName(examineProjectNameDetailsVO.getProjectName())
                .projectCode(examineProjectNameDetailsVO.getProjectCode())
                .isPrint(Integer.valueOf(examineProjectNameDetailsVO.getIsPrint()))
                .build());
    }


    /**
     * @return java.util.Map<java.lang.String, java.lang.String>
     * @Author Administrator
     * @Description ??????????????????????????????????????????????????????
     * @Date 16:24 2022/2/17
     * @Param [examineRecordId, examineRecordCombiProjectVos]
     **/
    public Map<String, String> generateBarCode(String examineRecordId, List<BaseCombiProjectVO> examineRecordCombiProject) {
        // ??????????????????????????????????????????Map????????????????????????ID???key????????????value
        Map<String, String> barCodeMap = new HashMap<>(examineRecordCombiProject.size());
        // ????????????
        for (int j = 0; j < examineRecordCombiProject.size(); j++) {
            BaseCombiProjectVO examineRecordCombiProjectVoJ = examineRecordCombiProject.get(j);
            // ????????????????????????????????????????????????????????????
            if (examineRecordCombiProjectVoJ.getIsApply() != null && examineRecordCombiProjectVoJ.getIsApply() == 1) {
                String barCode = null;
                // ???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                for (int k = 0; k < examineRecordCombiProject.size(); k++) {
                    BaseCombiProjectVO examineRecordCombiProjectVoK = examineRecordCombiProject.get(k);

                    String tubeType = examineRecordCombiProjectVoJ.getTubeType();
                    String tubeType2 = examineRecordCombiProjectVoK.getTubeType();
                    String projectMark = examineRecordCombiProjectVoJ.getProjectMark();
                    String projectMark2 = examineRecordCombiProjectVoK.getProjectMark();

                    // ????????????????????????????????????????????????????????????????????????????????????????????????
                    if (StringUtils.isNotEmpty(tubeType) && tubeType.equals(tubeType2) &&
                            StringUtils.isNotEmpty(projectMark) && projectMark.equals(projectMark2)) {
                        // ??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                        // ????????????????????????????????????????????????????????????????????????
                        if (k < j) {
                            // ???????????????????????????
                            barCode = barCodeMap.get(examineRecordCombiProjectVoK.getId());
                            barCodeMap.put(examineRecordCombiProjectVoJ.getId(), barCode);
                        } else {
                            break;
                        }
                    }
                }
                // ????????????????????????????????????????????????
                if (barCode == null) {

                    // ???????????????????????????????????????????????????
                    barCode = baseExamineRecordApplicationMapper.selectBarCodeByExamineRecordIdAndCombiProjectId(examineRecordId, examineRecordCombiProjectVoJ.getId());
                    // ?????????????????????????????????????????????????????????????????????????????????
                    if (barCode == null) {
                        Long maxId = iBaseCodingService.getLastOrderNumByCodeType("1");
                        barCode = CodeGenerateUtil.genderationCode("", 10, maxId, false);
                    }
                    barCodeMap.put(examineRecordCombiProjectVoJ.getId(), barCode);
                }
            }
        }

        return barCodeMap;
    }

    /**
     * ????????????????????????
     *
     * @param combiProjectMap
     * @param examineProjectNameDetailsVOList
     * @param baseExamineRecordId
     */
    private void insertExamineProject(Map<String, BaseCombiProjectVO> combiProjectMap, List<ExamineProjectNameDetailsVO> examineProjectNameDetailsVOList, String baseExamineRecordId) {
        Date date = new Date();
        List<BaseExamineRecordProjectDO> projectList = new ArrayList<>();
        for (ExamineProjectNameDetailsVO examineProjectNameDetailsVO : examineProjectNameDetailsVOList) {
            BaseCombiProjectVO BaseCombiProjectVO = combiProjectMap.get(examineProjectNameDetailsVO.getCombiProjectId());
            projectList.add(BaseExamineRecordProjectDO.builder()
                    .departmentId(BaseCombiProjectVO.getDepartmentId())
                    .delFlag(CommonlyUsedEnum.NOT_DELETE.val().toString())
                    .createBy(SecurityUtils.getMobileUsername())
                    .createTime(date)
//                    .createId(SecurityUtils.getMobileUserId())
                    .id(IdUtils.fastSimpleUUID())
                    .updateBy(SecurityUtils.getMobileUsername())
                    .updateTime(date)
//                    .updateId(SecurityUtils.getMobileUserId())
                    .projectId(examineProjectNameDetailsVO.getProjectId())
                    .projectName(examineProjectNameDetailsVO.getProjectName())
                    .combiProjectId(BaseCombiProjectVO.getId())
                    .combiProjectName(BaseCombiProjectVO.getProjectName())
                    .examineRecordId(baseExamineRecordId)
                    .projectUnit(examineProjectNameDetailsVO.getProjectUnit())
                    .inspectBy(SecurityUtils.getMobileUsername())
//                    .inspectId(SecurityUtils.getMobileUserId())
                    .build());

        }
        Integer i = baseExamineRecordProjectMapper.insertProjectList(projectList);
        if (i != projectList.size()) {
            throw new RuntimeException("???????????????");
        }


    }

    /**
     * ????????????????????????
     */
    private Map<String, BaseCombiProjectVO> insertCombiProject(List<BaseCombiProjectVO> baseCombiProjectList, BaseExaminePackageDO baseExaminePackageDO, String baseExamineRecord) {
        Date date = new Date();

        Map<String, BaseCombiProjectVO> map = new HashMap<>(baseCombiProjectList.size());

        List<BaseExamineRecordCombiProjectDO> combiProjectList = new ArrayList<>();

        for (BaseCombiProjectVO baseCombiProjectVO : baseCombiProjectList) {
            map.put(baseCombiProjectVO.getId(), baseCombiProjectVO);
            BigDecimal price = BigDecimal.ZERO;
            BigDecimal bigDecimal = new BigDecimal("0.1");
            if (baseCombiProjectVO.getDiscount() == null) {
                price = baseCombiProjectVO.getPrice();
            } else {
                price = (baseCombiProjectVO.getDiscount().multiply(bigDecimal)).multiply(baseCombiProjectVO.getPrice());
            }
            combiProjectList.add(BaseExamineRecordCombiProjectDO.builder()
                    .updateTime(date)
                    .id(IdUtils.fastSimpleUUID())
                    .examinePackageId(baseExaminePackageDO.getId())
                    .examinePackageName(baseExaminePackageDO.getName())
                    .examineRecordId(baseExamineRecord)
                    .combiProjectId(baseCombiProjectVO.getId())
                    .combiProjectName(baseCombiProjectVO.getProjectName())
                    .createTime(date)
                    .createBy(SecurityUtils.getMobileUsername())
                    .amountPaid(price)
                    .delFlag(CommonlyUsedEnum.NOT_DELETE.val().toString())
                    .discount(baseCombiProjectVO.getDiscount())
                    .updateBy(SecurityUtils.getMobileUsername())
                    .isAbandon(0)
                    .createId(SecurityUtils.getMobileUserId())
                    .price(baseCombiProjectVO.getPrice())
                    .departmentId(baseCombiProjectVO.getDepartmentId())
                    .build());

        }
        Integer i = baseExamineRecordCombiProjectMapper.insertCombiProjectList(combiProjectList);
        if (i != combiProjectList.size()) {
            throw new RuntimeException("????????????");
        }
        return map;
    }

    /**
     * ??????????????????
     *
     * @param reserveExamineDTO
     * @param
     * @param
     * @return
     */
    private String getBaseExamineRecord(ReserveExamineDTO reserveExamineDTO, List<BaseCombiProjectVO> baseCombiProjectList, BigDecimal discount) {
        BigDecimal amountPaid = BigDecimal.ZERO;
        BigDecimal amountTotal = BigDecimal.ZERO;
        for (BaseCombiProjectVO baseCombiProjectVo : baseCombiProjectList) {
            amountTotal = (baseCombiProjectVo.getPrice() == null ? BigDecimal.ZERO : baseCombiProjectVo.getPrice()).add(amountTotal);
        }
        if (discount == null) {
            amountPaid = amountTotal;
        } else {
            amountPaid = amountTotal.multiply(discount.multiply(new BigDecimal("0.1")));
        }
        long maxId = iBaseCodingService.getLastOrderNumByCodeType("4");
        BaseExamineRecordDO examineRecordDO = BaseExamineRecordDO.builder()
                .id(IdUtils.simpleUUID())
                .amountPaid(amountPaid)
                .amountTotal(amountTotal)
                .personRecordId(SecurityUtils.getMobileUserId())
                .examineCode(CodeGenerateUtil.genderationCode("TJ", 12, maxId, false))
                .examineStatus("1")
                .examineDate(DateUtils.parseDate(reserveExamineDTO.getExamineDate()))
//                .createId(SecurityUtils.getUserId())
                .createTime(DateUtils.getNowDate())
                .createBy(SecurityUtils.getMobileUsername())
                .registerTime(DateUtils.dateTime(DateUtils.YYYY_MM_DD, DateUtils.getDate()))
                .examineTypeId(reserveExamineDTO.getExamineTypeId())
                .isForm(0)
                .isDelete(0)
                .delFlag(CommonlyUsedEnum.NOT_DELETE.val().toString())
                .build();
        if (baseExamineRecordMapper.insert(examineRecordDO) != 1) {
            throw new RuntimeException("???????????????");
        }
        return examineRecordDO.getId();

    }

    /**
     * ????????????????????????
     *
     * @param
     * @return
     */
    @Override
    public AjaxResult findByList(Integer pageNum, Integer pageSize, Integer type) {
//       ?????????
        QueryWrapper<Object> wrapper = new QueryWrapper<>();
        if (CommonlyUsedEnum.PENDING_MEDICAL_EXAMINATION.val().equals(type)) {
            wrapper.eq("ber.person_record_id", SecurityUtils.getMobileUserId())
                    .eq("ber.del_flag", CommonlyUsedEnum.NOT_DELETE.val())
                    .eq("ber.is_form", "0")
                    .eq("ber.is_delete", 0)
                    .apply("NOW()<= ber.examine_date")
                    .groupBy("ber.id").orderByDesc("ber.update_time");
        } else if (CommonlyUsedEnum.EXPIRED.val().equals(type)) {
            wrapper.eq("ber.person_record_id", SecurityUtils.getMobileUserId());
            wrapper.and(w -> w
                            .apply("NOW()>= ber.examine_date")
                            .or()
                            .eq("ber.is_delete", 1))
                    .groupBy("ber.id").orderByDesc("ber.update_time");
        } else {
            return AjaxResult.success();
        }
        Page<ExamineRecordVO> examineRecordVOList = baseExamineRecordMapper.findByList(wrapper, new Page<>(pageNum, pageSize));
        return AjaxResult.success(examineRecordVOList);
    }

    /**
     * ??????????????????
     *
     * @param examineRecordId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public AjaxResult packageDetails(String examineRecordId, Integer pageNum, Integer pageSize) {
        Page<ExamineRecordVO> examineRecordPage = baseExamineRecordMapper
                .packageDetails(new Page<>(pageNum, pageSize), examineRecordId);
        return AjaxResult.success(examineRecordPage);
    }

    /**
     * ??????????????????
     *
     * @param examineRecordId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void cancelAppointment(String examineRecordId) {
        BaseExamineRecordDO baseExamineRecordDO = new BaseExamineRecordDO();
        baseExamineRecordDO.setIsDelete(CommonlyUsedEnum.DELETE.val());
//        baseExamineRecordDO.setUpdateId(SecurityUtils.getUserId());
        baseExamineRecordDO.setUpdateTime(new Date());
        baseExamineRecordDO.setDelFlag(CommonlyUsedEnum.DELETE.val().toString());
        if (baseExamineRecordMapper.update(baseExamineRecordDO,
                new QueryWrapper<BaseExamineRecordDO>()
                        .eq("id", examineRecordId)
                        .eq("person_record_id", SecurityUtils.getMobileUserId())) != 1) {
            throw new RuntimeException("???????????????");
        }
    }

    /**
     * ????????????????????????
     *
     * @param examineFinishListDTO
     * @return
     */
    @Override
    public Page<ExamineFinishListVO> examineFinishList(ExamineFinishListDTO examineFinishListDTO) {

        return baseExamineRecordMapper.selectExamineFinishList(new Page<>(examineFinishListDTO.getPageNum()
                , examineFinishListDTO.getPageSize()),  ExamineFinishListDTO.condition(examineFinishListDTO,occupationalDisease,CommonlyUsedEnum.END_EXAMINE.val()));
    }
    /**
     * ??????????????????
     *
     * @param examineFinishListDTO
     * @return
     */
    @Override
    public Page<ExamineFinishListVO> examineList(ExamineFinishListDTO examineFinishListDTO) {
        return baseExamineRecordMapper.selectExamineFinishList(new Page<>(examineFinishListDTO.getPageNum()
                , examineFinishListDTO.getPageSize()),  ExamineFinishListDTO.condition(examineFinishListDTO,occupationalDisease,null));

    }
}
