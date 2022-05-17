package com.ruoyi.moblie.medicalPackage.domain.dto;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.mobile.common.Page;
import com.ruoyi.common.mobile.enums.CommonlyUsedEnum;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.moblie.medicalPackage.domain.vo.BaseSetMealVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("后台查询套餐")
@Data
public class BaseSetMealDTO extends Page {
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("标签id")
    private String tagId;
    @ApiModelProperty("套餐名称")
    private String examinePackageName;
    @ApiModelProperty("套餐大写字母")
    private String inputCode;

    /**
     * 封装查询套餐条件
     * @param baseSetMealDTO
     * @return
     */
    public   QueryWrapper queryWrapper(BaseSetMealDTO baseSetMealDTO){
        QueryWrapper<BaseSetMealVO> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(baseSetMealDTO.getTitle())) {
            wrapper.like("bsm.title", baseSetMealDTO.getTitle());
        }
        if (StringUtils.isNotEmpty(baseSetMealDTO.getExaminePackageName())) {
            wrapper.like("bep.name", baseSetMealDTO.getExaminePackageName());
        }
        if (StringUtils.isNotEmpty(baseSetMealDTO.getInputCode())) {
            wrapper.likeRight("bep.input_code", baseSetMealDTO.getInputCode());
        }
        wrapper.eq("bsm.del_flag",CommonlyUsedEnum.NOT_DELETE.val());
        wrapper.orderByDesc("bsm.created_time");
        return wrapper;
    }

}
