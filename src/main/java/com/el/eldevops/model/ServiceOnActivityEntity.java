package com.el.eldevops.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author YunTao.Li
 * @description:
 * @date 2022/7/21 13:45
 */
@TableName("el_service_on_activity")
@Data
public class ServiceOnActivityEntity {
    @TableId(type = IdType.UUID, value = "sid")
    private String sid;

    @TableField("activity_id")
    private String activityId;

    @TableField("service_id")
    private String serviceId;

    @TableField("book_id")
    private String bookId;
}
