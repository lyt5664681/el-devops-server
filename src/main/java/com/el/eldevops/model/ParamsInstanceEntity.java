package com.el.eldevops.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author YunTao.Li
 * @description:
 * @date 2022/7/21 10:33
 */
@TableName("el_params_inst")
@Data
@ApiModel("参数实例-ParamsInstanceEntity")
public class ParamsInstanceEntity {
    @ApiModelProperty("参数标识")
    @TableId(type = IdType.UUID, value = "param_inst_id")
    private String paramInstId;

    @ApiModelProperty("参数名称")
    @TableField("param_name")
    private String paramName;

    @ApiModelProperty("参数说明")
    @TableField("param_label")
    private String paramLabel;

    //数据类型String:Integer:Boolean
    @TableField("data_type")
    private String dataType;

    // 参数值
    @TableField("param_value")
    private String paramValue;

    //值类型1常量2变量
    @TableField("value_type")
    private Integer valueType;

    // 参数类型，1出参，2入参，3全局参数
    @TableField("param_type")
    private Integer paramType;

    //1全局参数，2服务参数
    @TableField("param_of")
    private Integer paramOf;

    @TableField("book_def_id")
    private String bookDefId;

    @TableField("service_id")
    private String serviceId;

    @TableField("activity_def_id")
    private String activityDefId;

    @TableField("param_seq")
    private Integer paramSeq;
}
