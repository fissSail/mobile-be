package com.ruoyi.mobile.userInfo.vo;

import com.ruoyi.common.mobile.entity.BasePersonRecordDO;
import com.ruoyi.mobile.login.vo.LoginReturnVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel("用户信息")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BasePersonRecordAndOpenIdVO {
    @ApiModelProperty("用户登录信息")
    private LoginReturnVO loginReturnVo;
    @ApiModelProperty("用户信息")
    private BasePersonRecordDO basePersonRecordDO;

}
