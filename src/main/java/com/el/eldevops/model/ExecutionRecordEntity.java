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
 * @date 2022/7/25 13:53
 */
@TableName("el_execution_record")
@Data
@ApiModel("执行记录-ExecutionRecordEntity")
public class ExecutionRecordEntity {
    @ApiModelProperty("参数标识")
    @TableId(type = IdType.UUID, value = "record_id")
    private String recordId;

    @ApiModelProperty("流程实例ID")
    @TableField("process_inst_id")
    private String processInstId;

    @ApiModelProperty("活动实例ID")
    @TableField("activity_inst_id")
    private String activityInstId;

    @ApiModelProperty("任务ID")
    @TableField("task_id")
    private String taskId;

    @ApiModelProperty("服务ID")
    @TableField("service_id")
    private String serviceId;

    @ApiModelProperty("服务名称")
    @TableField("service_name")
    private String serviceName;

    @ApiModelProperty("3待处理4正常5异常6已终止")
    @TableField("record_status")
    private Integer recordStatus;

    @ApiModelProperty("执行消息")
    @TableField("record_message")
    private String recordMessage;

    @ApiModelProperty("参数信息")
    @TableField("param_json")
    private String paramJson;

    @ApiModelProperty("处理人")
    @TableField("assignee")
    private String assignee;
}
