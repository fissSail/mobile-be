package com.ruoyi.mobile.medicalPackage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.mobile.entity.BaseExamineTypeDO;
import com.ruoyi.common.mobile.entity.BasePersonTagDO;
import com.ruoyi.common.mobile.entity.BaseSetMealDO;
import com.ruoyi.common.mobile.enums.CommonlyUsedEnum;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.mobile.medicalPackage.dto.MedicalPackageDTO;
import com.ruoyi.mobile.reserveExamine.mapper.BaseExamineTypeMapper;
import com.ruoyi.mobile.medicalPackage.mapper.MedicalPackageMapper;
import com.ruoyi.mobile.medicalPackage.service.MedicalPackageService;
import com.ruoyi.mobile.medicalPackage.vo.BaseSetMealVO;
import com.ruoyi.mobile.medicalPackage.vo.PackageDetailsVO;
import com.ruoyi.mobile.userInfo.mapper.BasePersonTagMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicalPackageServiceImpl implements MedicalPackageService {


    private final MedicalPackageMapper medicalPackageMapper;


    private final BaseExamineTypeMapper baseExamineTypeMapper;

    private final BasePersonTagMapper basePersonTagMapper;

    @Value("${ruoyi.imageUrl}")
    private String imageUrl;

    /**
     * 获取套餐
     *
     * @param medicalPackageDto
     * @return
     */
    @Override
    public Page<BaseSetMealVO> findByList(MedicalPackageDTO medicalPackageDto) {
        //获取标签
        List<BasePersonTagDO> basePersonTagDOList = basePersonTagMapper.selectList(new QueryWrapper<BasePersonTagDO>()
                .eq("person_record_id", SecurityUtils.getMobileUserId())
                .eq("del_flag", CommonlyUsedEnum.NOT_DELETE.val()));
        return medicalPackageMapper.findByList(medicalPackageDto.getCondition(medicalPackageDto,basePersonTagDOList)
                , new Page<>(medicalPackageDto.getPageNum(), medicalPackageDto.getPageSize()));

    }

    /**
     * 获取体检套餐详情
     *
     * @param id
     * @return
     */
    @Override
    public PackageDetailsVO packageDetails(String id) {

        BaseSetMealDO baseSetMealDO = medicalPackageMapper.selectById(id);
        if (baseSetMealDO == null) {
            return new PackageDetailsVO();
        }

        return medicalPackageMapper.packageDetails(id);

    }

    /**
     * 根据标签id获取套餐推荐
     *
     * @param tagId
     * @return
     */
    @Override
    public List<BaseSetMealVO> packageRecommendation(List<String> tagId, Integer pageSize, Integer pageNum) {
        if (tagId.isEmpty()) {
            return null;
        }
        Page<BaseSetMealVO> setMealPage = medicalPackageMapper
                .selectPackageRecommendation(new Page<>(pageNum, pageSize), new QueryWrapper<>()
                        .in("bsm.id", medicalPackageMapper.findBaseSetMealId(tagId)));
        if (setMealPage == null) {
            //根据销量高低推荐
            setMealPage = medicalPackageMapper.selectSetMealSaleNumber(new Page<>(pageNum, pageSize));
        }
        return setMealPage.getRecords();
    }

    /**
     * 获取体检类型
     *
     * @param pageSize
     * @param pageNum
     * @return
     */
    @Override
    public Page<BaseExamineTypeDO> findByExamineType(Integer pageSize, Integer pageNum) {
        return baseExamineTypeMapper.selectPage(new Page<>(pageNum, pageSize), new QueryWrapper<BaseExamineTypeDO>()
                .eq("del_flag", CommonlyUsedEnum.NOT_DELETE.val())
                .eq("status", CommonlyUsedEnum.ENABLE.val()));

    }

    /**
     * 获取标题图片
     *
     * @param baseSetMealId
     * @param response
     */
    @Override
    public void titleImage(String baseSetMealId, HttpServletResponse response) throws IOException {
        QueryWrapper<BaseSetMealDO> wrapper = new QueryWrapper<BaseSetMealDO>()
                .eq("id", baseSetMealId)
                .eq("del_flag", CommonlyUsedEnum.NOT_DELETE.val());
        BaseSetMealDO baseSetMealDO = medicalPackageMapper.selectOne(wrapper);
        if (baseSetMealDO != null) {
            String banner = baseSetMealDO.getImage();
            if (StringUtils.isNotEmpty(banner)) {
                String bannerUrl = imageUrl + "/" + "upload" + "/" + banner.split("/", 4)[3];
                File file = new File(bannerUrl);
                if (file.exists()) {
                    byte[] bytes = new byte[1024];
                    int len = 0;
                    FileInputStream inputStream = new FileInputStream(file);
                    try {
                        while ((len = inputStream.read(bytes)) != -1) {
                            response.getOutputStream().write(bytes, 0, len);
                        }
                    } catch (Exception e) {
                    } finally {
                        inputStream.close();
                    }
                }
            }
        }
    }

}
