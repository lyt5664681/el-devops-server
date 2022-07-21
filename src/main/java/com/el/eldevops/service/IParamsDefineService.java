package com.el.eldevops.service;

import com.el.eldevops.model.ParamsDefineEntity;

import java.util.List;

/**
 * @author YunTao.Li
 * @description:
 * @date 2022/7/21 10:35
 */
public interface IParamsDefineService {

    /**
     * @param serviceId todo
     * @return java.util.List<com.el.eldevops.model.ParamsDefineEntity>
     * @description 根据服务查询该服务的参数定义
     * @author YunTao.Li
     * @date 2022/7/21 10:54
     */
    public List<ParamsDefineEntity> getParamsByService(String serviceId) throws Throwable;

    /**
     * @param bookDefId todo
     * @return java.util.List<com.el.eldevops.model.ParamsDefineEntity>
     * @description 根据剧本查询该剧本的参数定义
     * @author YunTao.Li
     * @date 2022/7/21 10:54
     */
    public List<ParamsDefineEntity> getParamsByBook(String bookDefId) throws Throwable;


}
