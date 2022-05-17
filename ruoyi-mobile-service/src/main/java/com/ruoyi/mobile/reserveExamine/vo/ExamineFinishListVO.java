package com.ruoyi.mobile.reserveExamine.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("用户体检完成列表返回")
public class ExamineFinishListVO {

    private String examineCode;
    private String examineRecordId;
    private String personRecordName;
    private String examineType;
    private String examinePackageName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String endTime;
    private String postType;
    private String examineTypeCode;
    private String isEndExamine;


}
