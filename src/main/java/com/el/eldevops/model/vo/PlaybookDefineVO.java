package com.el.eldevops.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author YunTao.Li
 * @description:
 * @date 2022/7/27 9:53
 */
@Data
public class PlaybookDefineVO {
    @ApiModelProperty("剧本定义标识")
    private String bookDefId;

    @ApiModelProperty("剧本定义key")
    private String bookDefKey;

    @ApiModelProperty("剧本定义名称")
    private String bookName;

    @ApiModelProperty("剧本定义说明")
    private String bookDesc;

    @ApiModelProperty("状态:0新建,1已创建，2已发布(上线)，3已挂起(下线)，4草稿")
    private int bookStatus;

    @ApiModelProperty("流程定义标识")
    private String processDefId;

    @ApiModelProperty("流程定义key")
    private String processDefKey;

    @ApiModelProperty("流程设计文件")
    private String bookXml;

    @ApiModelProperty("创建时间")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty("更新时间")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
