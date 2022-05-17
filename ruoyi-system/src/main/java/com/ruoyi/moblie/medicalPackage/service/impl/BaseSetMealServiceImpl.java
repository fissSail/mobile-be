package com.ruoyi.moblie.medicalPackage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.mobile.entity.BaseExaminePackageDO;
import com.ruoyi.common.mobile.entity.BaseSetMealDO;
import com.ruoyi.common.mobile.entity.BaseTagDO;
import com.ruoyi.common.mobile.enums.CommonlyUsedEnum;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.moblie.baseTag.mapper.BaseSetMealTagMapper;
import com.ruoyi.moblie.baseTag.mapper.BaseTagMapper;
import com.ruoyi.moblie.medicalPackage.domain.dto.BaseSetMealAddAndUpdateDTO;
import com.ruoyi.moblie.medicalPackage.domain.dto.BaseSetMealDTO;
import com.ruoyi.moblie.medicalPackage.domain.vo.BaseSetMealVO;
import com.ruoyi.moblie.medicalPackage.mapper.BaseExaminePackageSystemMapper;
import com.ruoyi.moblie.medicalPackage.mapper.BaseSetMealMapper;
import com.ruoyi.moblie.medicalPackage.service.BaseSetMealService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BaseSetMealServiceImpl extends ServiceImpl<BaseSetMealMapper, BaseSetMealDO> implements BaseSetMealService {

    private final BaseSetMealMapper baseSetMealMapper;
    private final BaseExaminePackageSystemMapper baseExaminePackageSystemMapper;
    private final BaseTagMapper baseTagMapper;
    private final BaseSetMealTagMapper baseSetMealTagMapper;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;


    /**
     * 获取套餐列表
     *
     * @param baseSetMealDTO
     * @return
     */
    @Override
    public Page<BaseSetMealVO> list(BaseSetMealDTO baseSetMealDTO) {
        Page baseSetMealMapperByList = baseSetMealMapper.findByList(baseSetMealDTO.queryWrapper(baseSetMealDTO),
                new Page<>(baseSetMealDTO.getPageNum(), baseSetMealDTO.getPageSize()));
        return baseSetMealMapperByList;
    }

    /**
     * 更新或添加移动套餐
     *
     * @param baseSetMealAddAndUpdateDTO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public AjaxResult updateCombo(BaseSetMealAddAndUpdateDTO baseSetMealAddAndUpdateDTO) {
        Date date = new Date();
        //校验参数是否存在
        try {
            if (checkParameters(baseSetMealAddAndUpdateDTO)) {
                BaseSetMealDO baseSetMealDO = new BaseSetMealDO();
                if (StringUtils.isNotEmpty(baseSetMealAddAndUpdateDTO.getId())) {

                    baseSetMealTagMapper.insertList(baseSetMealAddAndUpdateDTO.getTagIdList(), baseSetMealAddAndUpdateDTO.getId());

                    BeanUtils.copyProperties(baseSetMealAddAndUpdateDTO, baseSetMealDO);
                    if (baseSetMealMapper.updateById(baseSetMealDO) != 1) {
                        throw new RuntimeException("更新出错！");
                    }
                } else {
                    BeanUtils.copyProperties(baseSetMealAddAndUpdateDTO, baseSetMealDO);
                    String id = IdUtils.fastSimpleUUID();
                    baseSetMealDO.setId(id);
                    baseSetMealDO.setCreatedTime(date);
                    baseSetMealDO.setUpdateTime(date);
                    baseSetMealDO.setDelFlag(CommonlyUsedEnum.NOT_DELETE.val().toString());
                    if (baseSetMealMapper.insert(baseSetMealDO) != 1) {
                        throw new RuntimeException("添加出错！");
                    }
                    baseSetMealTagMapper.insertList(baseSetMealAddAndUpdateDTO.getTagIdList(), id);

                }
            } else {
                throw new RuntimeException("标签或套餐不存在!");
            }
        } catch (DuplicateKeyException e) {
            throw new RuntimeException(baseSetMealAddAndUpdateDTO.getTitle()+"已存在");
        }

        return AjaxResult.success();
    }

    /**
     * 校验参数存在
     *
     * @param baseSetMealAddAndUpdateDTO
     * @return
     */
    private boolean checkParameters(BaseSetMealAddAndUpdateDTO baseSetMealAddAndUpdateDTO) {
        BaseExaminePackageDO baseExaminePackageDO = baseExaminePackageSystemMapper
                .selectOne(new QueryWrapper<BaseExaminePackageDO>()
                        .eq("id", baseSetMealAddAndUpdateDTO.getBaseExaminePackageId())
                        .eq("del_flag", CommonlyUsedEnum.NOT_DELETE.val()));
        List<BaseTagDO> baseTagDOList = baseTagMapper.selectList(new QueryWrapper<BaseTagDO>()
                .in("id", baseSetMealAddAndUpdateDTO.getTagIdList()));
        if (baseExaminePackageDO == null || baseTagDOList == null || baseTagDOList.size() != baseSetMealAddAndUpdateDTO.getTagIdList().size()) {
            return false;
        }
        return true;
    }

    /**
     * 更新套餐销量
     */
    @Override
    public void packageSales() {
        BoundHashOperations<String, Object, Object> packageSales = redisTemplate.boundHashOps("packageSales");
        try {
            Date date = new Date();
            packageSales.keys().stream().forEach(t -> {
                Integer number = (Integer) packageSales.get(t);
                if (number != null) {
                    BaseSetMealDO baseSetMealDO = baseSetMealMapper.selectOne(new QueryWrapper<BaseSetMealDO>()
                            .eq("base_examine_package_id", t)
                            .eq("del_flag", CommonlyUsedEnum.NOT_DELETE.val()));
                    if (baseSetMealDO != null) {
                        baseSetMealDO.setSaleNumber(baseSetMealDO.getSaleNumber() + number);
                        baseSetMealDO.setUpdateTime(date);
                        baseSetMealMapper.updateById(baseSetMealDO);
                    }
                }
                packageSales.delete(t);
            });
        } catch (Exception e) {
            e.getMessage();
        }
    }

}

