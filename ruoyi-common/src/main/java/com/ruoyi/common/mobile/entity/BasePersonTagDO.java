package com.ruoyi.common.mobile.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("base_person_tag")
@ApiModel("用户绑定标签")
public class BasePersonTagDO {
    @ApiModelProperty("用户id")
    private String personRecordId;
    @ApiModelProperty("标签id")
    private String tagId;
    @ApiModelProperty("删除标志 0存在 1删")
    @TableLogic
    private String delFlag;
}
