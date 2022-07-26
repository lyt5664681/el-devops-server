package com.el.eldevops.bpm.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author YunTao.Li
 * @version 1.0.0
 * @ClassName ParamInstPOJO.java
 * @Description 已实例化的参数，表示实际执行服务调用时参数的实际值
 * @createTime 2021年12月06日 14:09:00
 */
@Data
@ApiModel("参数实例")
public class ParamInstPOJO {

    @ApiModelProperty("参数实例标识")
    private String paramInstId;

    @ApiModelProperty("参数显示名")
    private String paramLabel;

    @ApiModelProperty("参数名称")
    private String paramName;

    @ApiModelProperty("参数值-实际值")
    private Object paramValue;

    @ApiModelProperty("参数类型")
    private String dataType;

    @ApiModelProperty("参数类型1出参2入参")
    private Integer paramType;

    public String toJSONString() {
        return "{'paramLabel':'" + getParamLabel() + "','paramName':'" + getParamName() + "','paramValue':'" + getParamValue() + "'}";
    }

}
