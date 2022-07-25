package com.el.eldevops.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author YunTao.Li
 * @description:
 * @date 2022/7/22 10:42
 */
@Data
@ApiModel("参数")
public class ParamsInstanceVO {
    @ApiModelProperty("参数标识")
    private String paramInstId;

    @ApiModelProperty("参数名称")
    private String paramName;

    @ApiModelProperty("参数说明")
    private String paramLabel;

    @ApiModelProperty("数据类型String:Integer:Boolean")
    private String dataType;

    @ApiModelProperty("参数值")
    private String paramValue;

    @ApiModelProperty("值类型1常量2变量")
    private Integer valueType;

    @ApiModelProperty("参数类型，1出参，2入参，3全局参数")
    private Integer paramType;

    @ApiModelProperty("1全局参数，2服务参数")
    private Integer paramOf;

    @ApiModelProperty("book_def_id")
    private String bookDefId;

    @ApiModelProperty("service_id")
    private String serviceId;

    @ApiModelProperty("activity_def_id")
    private String activityDefId;

    @ApiModelProperty("param_seq")
    private Integer paramSeq;
}
