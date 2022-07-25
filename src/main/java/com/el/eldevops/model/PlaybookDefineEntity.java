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
@TableName("el_playbook_define")
@Data
@ApiModel("剧本定义-PlaybookDefineEntity")
public class PlaybookDefineEntity {
    @ApiModelProperty("剧本定义标识")
    @TableId(type = IdType.UUID, value = "book_def_id")
    private String bookDefId;

    @ApiModelProperty("剧本定义key")
    @TableField("book_def_key")
    private String bookDefKey;

    @ApiModelProperty("剧本定义名称")
    @TableField("book_name")
    private String bookName;

    @ApiModelProperty("剧本定义说明")
    @TableField("book_desc")
    private String bookDesc;

    @ApiModelProperty("状态:0新建,1已创建，2已发布(上线)，3已挂起(下线)，4草稿")
    @TableField("book_status")
    private int bookStatus;

    @ApiModelProperty("流程定义标识")
    @TableField("process_def_id")
    private String processDefId;

    @ApiModelProperty("流程定义key")
    @TableField("process_def_key")
    private String processDefKey;

    @ApiModelProperty("流程设计文件")
    @TableField("book_xml")
    private String bookXml;

    @ApiModelProperty("创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty("更新时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
