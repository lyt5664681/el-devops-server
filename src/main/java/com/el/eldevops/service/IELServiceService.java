package com.el.eldevops.service;

import com.el.eldevops.model.ELServiceEntity;

import java.util.List;

/**
 * @author YunTao.Li
 * @description:
 * @date 2022/7/21 10:02
 */
public interface IELServiceService {

    /**
     * @return boolean
     * @description 服务绑定
     * @author YunTao.Li
     * @date 2022/7/22 10:24
     */
    public boolean serviceInstBind(String bookDefId, String activityDefId, String serviceId) throws Exception;

    /**
     * @return boolean
     * @description 服务解绑
     * @author YunTao.Li
     * @date 2022/7/22 10:24
     */
    public boolean serviceInstUnBind(String bookDefId, String activityDefId, String serviceId) throws Exception;

    /**
     * 查询剧本、活动关联的服务,通常来说每个活动只关联一条服务
     *
     * @param bookId     :
     * @param activityId :
     * @return : java.util.List
     * @author : YunTao.Li
     * @date : 2021/9/16 2021/9/16
     */
    List<ELServiceEntity> getActivityService(String bookId, String activityId);
}
