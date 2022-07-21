package com.el.eldevops.model;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import lombok.Data;


/**
 * @author YunTao.Li
 * @description:
 * @date 2022/7/21 10:02
 */
@TableName("el_service")
@Data
@ApiModel("服务")
public class ServiceEntity {
    @TableId(type = IdType.UUID, value = "service_id")
    private String serviceId;
    
    @TableField("app_id")
    private String appId;

    @TableField("service_ch_name")
    private String serviceChName;

    @TableField("service_name")
    private String serviceName;

    @TableField("service_desc")
    private String serviceDesc;

    @TableField("service_type")
    private int serviceType;

    @TableField("url")
    private String url;

    @TableField("method")
    private String method;

    @TableField("protocol")
    private String protocol;

    @TableField("script")
    private String script;
}
