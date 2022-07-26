package com.el.eldevops.service;

import com.el.eldevops.model.ParamsInstanceEntity;
import com.el.eldevops.model.vo.ParamsInstanceVO;

import java.util.List;

/**
 * @author YunTao.Li
 * @description:
 * @date 2022/7/21 10:34
 */
public interface IParamsInstanceService {

    /**
     * @return void
     * @description 新增服务参数实例
     * @author YunTao.Li
     * @date 2022/7/22 14:49
     */
    public void paramsNewInstance(ParamsInstanceVO paramsInstanceVO) throws Exception;

    /**
     * @return java.util.List<com.el.eldevops.model.ParamsInstanceEntity>
     * @description
     * @author YunTao.Li
     * @date 2022/7/25 17:07
     */

    /**
     * @param bookDefId     剧本定义ID
     * @param activityDefId 活动定义ID
     * @param serviceId     服务ID
     * @return java.util.List<com.el.eldevops.model.ParamsInstanceEntity>
     * @description 查询服务绑定的参数实例列表
     * @author YunTao.Li
     * @date 2022/7/25 17:15
     */
    List<ParamsInstanceEntity> getParamsInstance(String bookDefId, String activityDefId, String serviceId);
}
