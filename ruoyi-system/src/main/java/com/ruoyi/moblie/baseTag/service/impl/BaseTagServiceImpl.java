package com.ruoyi.moblie.baseTag.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.mobile.entity.BaseSetMealTagDO;
import com.ruoyi.common.mobile.entity.BaseTagDO;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.moblie.baseTag.domain.dto.BaseTagDTO;
import com.ruoyi.moblie.medicalPackage.mapper.BaseSetMealMapper;
import com.ruoyi.moblie.baseTag.mapper.BaseSetMealTagMapper;
import com.ruoyi.moblie.baseTag.mapper.BaseTagMapper;
import com.ruoyi.moblie.baseTag.service.BaseTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BaseTagServiceImpl implements BaseTagService {


    private final BaseTagMapper baseTagMapper;
    private final BaseSetMealMapper baseSetMealMapper;
    private final BaseSetMealTagMapper baseSetMealTagMapper;

    /**
     * 获取便签
     *
     * @param
     * @param baseTagDTO
     * @return
     */
    @Override
    public Page<BaseTagDO> list(BaseTagDTO baseTagDTO) {
        QueryWrapper<BaseTagDO> wrapper = getBaseTagDOQueryWrapper(baseTagDTO);
        return baseTagMapper.selectPage(new Page<>(baseTagDTO.getPageNum(), baseTagDTO.getPageSize()), wrapper);
    }

    /**
     * 封装查询
     *
     * @param baseTagDTO
     * @return
     */
    private QueryWrapper<BaseTagDO> getBaseTagDOQueryWrapper(BaseTagDTO baseTagDTO) {
        QueryWrapper<BaseTagDO> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(baseTagDTO.getContent())) {
            wrapper.like("content", baseTagDTO.getContent());
        }
        if (baseTagDTO.getMinAge() != null && baseTagDTO.getMaxAge() != null) {
            wrapper.eq("min_apply_age", baseTagDTO.getMinAge())
                    .eq("max_apply_age", baseTagDTO.getMaxAge());
        }
        if (StringUtils.isNotEmpty(baseTagDTO.getSex())) {
            wrapper.eq("apply_sex", baseTagDTO.getSex());
        }
        return wrapper;
    }

    /**
     * 添加或更新标签
     *
     * @param baseTagDO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public AjaxResult updateAndAdd(BaseTagDO baseTagDO) {

        if (StringUtils.isNotEmpty(baseTagDO.getId())) {
            if (baseSetMealTagMapper.selectCount(new QueryWrapper<BaseSetMealTagDO>()
                    .eq("tag_id", baseTagDO.getId())) >= 1) {
                throw new RuntimeException("当前标签已关联套餐，暂不能更新！");
            }
            if (baseTagMapper.updateById(baseTagDO) != 1) {
                throw new RuntimeException("更新失败！");
            }
            return AjaxResult.success(baseTagDO);
        } else {
            BaseTagDO baseTag = new BaseTagDO();
            BeanUtils.copyProperties(baseTagDO, baseTag);
            baseTag.setId(IdUtils.fastSimpleUUID());
            if (baseTagMapper.insert(baseTag) != 1) {
                throw new RuntimeException("添加失败！");
            }
            return AjaxResult.success(baseTag);
        }

    }
}
