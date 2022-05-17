package com.ruoyi.mobile.medicalPackage.dto;

import cn.hutool.http.HttpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.mobile.common.Page;
import com.ruoyi.common.mobile.entity.BasePersonTagDO;
import com.ruoyi.common.mobile.enums.CommonlyUsedEnum;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@ApiModel("获取体检套餐")
@Data
public class MedicalPackageDTO extends Page {
    @ApiModelProperty("套餐id")
    private String id;
    @ApiModelProperty("体检类型")
    private String examineType;
    @ApiModelProperty("性别 1 男 2女 3通用")
    private Integer sex;
    @ApiModelProperty("最小年龄")
    private Integer minAge;
    @ApiModelProperty("最大年龄")
    private Integer maxAge;
    @ApiModelProperty("最低价格")
    private BigDecimal minPrice;
    @ApiModelProperty("最高价格")
    private BigDecimal maxPrice;
    @ApiModelProperty("销量  1 降  2升")
    private Integer sales;

    /**
     * 获取条件
     *
     * @param medicalPackageDto
     * @return
     */
    public QueryWrapper getCondition(MedicalPackageDTO medicalPackageDto, List<BasePersonTagDO> basePersonTagDOList) {
        boolean condition = true;
        QueryWrapper<Object> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(medicalPackageDto.getExamineType())) {
            wrapper.eq("bet.code", medicalPackageDto.getExamineType());
            wrapper.eq("bet.del_flag", CommonlyUsedEnum.NOT_DELETE.val());
            wrapper.eq("bet.status", CommonlyUsedEnum.ENABLE.val());
            condition = false;
        }

        if (medicalPackageDto.getMinAge() != null && medicalPackageDto.getMaxAge() != null) {
            wrapper.le("bt.min_apply_age", medicalPackageDto.getMinAge())
                    .ge("bt.max_apply_age", medicalPackageDto.getMaxAge());
            condition = false;
        } else if (medicalPackageDto.getMinAge() == null && medicalPackageDto.getMinAge() != null) {
            wrapper.ge("bt.max_apply_age", medicalPackageDto.getMaxAge());
            condition = false;
        } else if (medicalPackageDto.getMaxAge() == null && medicalPackageDto.getMinAge() != null) {
            wrapper.le("bt.min_apply_age", medicalPackageDto.getMinAge());
            condition = false;
        }
        if (medicalPackageDto.getMinPrice() != null) {
            wrapper.ge("bep.amount_receivable", medicalPackageDto.getMinPrice());
            condition = false;
        }
        if (medicalPackageDto.getMaxPrice() != null) {
            wrapper.le("bep.amount_receivable", medicalPackageDto.getMaxPrice());
            condition = false;
        }
        if (medicalPackageDto.getSex() != null) {
            condition = false;
            if (CommonlyUsedEnum.MALE.val().equals(medicalPackageDto.getSex())) {
                wrapper.eq("bt.apply_sex", medicalPackageDto.getSex());
            } else if (CommonlyUsedEnum.FEMALE.val().equals(medicalPackageDto.getSex())) {
                wrapper.eq("bt.apply_sex", medicalPackageDto.getSex());
            } else if (CommonlyUsedEnum.UNIVERSAL.val().equals(medicalPackageDto.getSex())) {
                wrapper.eq("bt.apply_sex", medicalPackageDto.getSex());
            }
        }
        if (medicalPackageDto.getSales() != null) {
            if (CommonlyUsedEnum.DROP.val().equals(medicalPackageDto.getSales())) {
                wrapper.orderByDesc("bsm.sale_number");
            } else if (CommonlyUsedEnum.RISE.val().equals(medicalPackageDto.getSales())) {
                wrapper.orderByAsc("bsm.sale_number");
            }
        }
        if (condition) {
            if (!basePersonTagDOList.isEmpty()) {
                wrapper.or().in("bt.id", basePersonTagDOList.stream()
                        .map(t -> t.getTagId()).collect(Collectors.toSet()));
            }
        }
        wrapper.eq("bep.del_flag", CommonlyUsedEnum.NOT_DELETE.val());
        wrapper.eq("bsm.del_flag", CommonlyUsedEnum.NOT_DELETE.val());
        wrapper.groupBy("bsm.id");
        return wrapper;
    }
}
