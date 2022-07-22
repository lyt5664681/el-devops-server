package com.el.eldevops.service;

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
}
