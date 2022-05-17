package com.ruoyi.mobile.reserveExamine.dto;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.mobile.common.Page;
import com.ruoyi.common.mobile.enums.CommonlyUsedEnum;
import com.ruoyi.common.mobile.enums.ExamineTypeEnum;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel("查询体检完成列表")
public class ExamineFinishListDTO extends Page {
    private String startTime;
    private String endTime;

    /**
     * 体检完成列表
     *
     * @param examineFinishListDTO
     * @param occupationalDisease
     * @return
     */
    public static QueryWrapper condition(ExamineFinishListDTO examineFinishListDTO, String occupationalDisease, Integer isEndExamine) {
        QueryWrapper<Object> wrapper = new QueryWrapper<>();
        wrapper.eq("ber.person_record_id", SecurityUtils.getMobileUserId())
                .eq("ber.del_flag", CommonlyUsedEnum.NOT_DELETE.val());
        if (CommonlyUsedEnum.END_EXAMINE.val().equals(isEndExamine)) {
            wrapper.eq("ber.is_end_examine", CommonlyUsedEnum.END_EXAMINE.val());
        }
        if ("false".equals(occupationalDisease)) {
            wrapper.ne("bet.code", ExamineTypeEnum.OCCUPATIONAL_HEALTH_CHECK.getVal());
        }
        if (StringUtils.isNotEmpty(examineFinishListDTO.getStartTime()) && StringUtils.isNotEmpty(examineFinishListDTO.getEndTime())) {
            wrapper.between("ber.examine_date", examineFinishListDTO.getStartTime(), examineFinishListDTO.getEndTime());
        }
        wrapper.groupBy("ber.id");
        return wrapper;
    }
}
