package com.dcits.user.constant;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.stereotype.Component;

/**
 * Created by gaojunc on 2018/3/13 20:17.
 * Created Reason: 返回信息状态码
 */
@Component
@ApiModel("ResultInfo")
public class ResultStatus {
    @ApiModelProperty(name = "statusCode", value = "返回信息状态码", required = true, dataType = "java.lang.String")
    private String statusCode;
    @ApiModelProperty(name = "statusInfo", value = "返回信息", required = true, dataType = "java.lang.String")
    private String statusInfo;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusInfo() {
        return statusInfo;
    }

    public void setStatusInfo(String statusInfo) {
        this.statusInfo = statusInfo;
    }
}
