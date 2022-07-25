package com.el.eldevops.service;

import com.el.eldevops.model.vo.ParamsInstanceVO;

/**
 * @author YunTao.Li
 * @description:
 * @date 2022/7/21 10:34
 */
public interface IParamsInstanceService {

    /**
     * @description 新增服务参数实例

     * @return void
     * @author YunTao.Li
     * @date 2022/7/22 14:49
     */
    public void paramsNewInstance(ParamsInstanceVO paramsInstanceVO) throws Exception;
}
