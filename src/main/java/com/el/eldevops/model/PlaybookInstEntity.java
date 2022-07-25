package com.el.eldevops.model;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author YunTao.Li
 * @description:
 * @date 2022/7/25 10:01
 */
@TableName("el_playbook_instance")
@Data
@ApiModel("剧本执行实例-PlaybookInstEntity")
public class PlaybookInstEntity {

    @ApiModelProperty("剧本实例标识")
    @TableId(type = IdType.UUID, value = "book_inst_id")
    private String bookInstId;

    @ApiModelProperty("流程实例ID")
    @TableField("process_inst_id")
    private String processInstId;

    @TableField("root_process_inst_id")
    private String rootProcessInstId;

    @TableField("book_def_id")
    private String bookDefId;

    @TableField("book_def_name")
    private String bookDefName;

    @TableField("book_inst_name")
    private String bookInstName;

    @TableField("book_inst_desc")
    private String bookInstDesc;

    @TableField("status")
    private Integer status;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @TableField("related_data")
    private String relatedData;
}
