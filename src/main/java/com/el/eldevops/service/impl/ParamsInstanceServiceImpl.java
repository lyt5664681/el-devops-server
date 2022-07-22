package com.el.eldevops.service.impl;

import com.el.eldevops.mapper.ParamsInstanceMapper;
import com.el.eldevops.model.ParamsInstanceEntity;
import com.el.eldevops.model.ParamsInstanceVO;
import com.el.eldevops.service.IParamsInstanceService;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author YunTao.Li
 * @description:
 * @date 2022/7/21 10:34
 */
@Primary
@Component
public class ParamsInstanceServiceImpl implements IParamsInstanceService {

    @Resource
    private ParamsInstanceMapper soarParamsDefEntity;


    @Override
    public void paramsNewInstance(ParamsInstanceVO paramsInstanceVO) throws Exception {
        ParamsInstanceEntity paramsInstanceEntity = new ParamsInstanceEntity();
        BeanUtils.copyProperties(paramsInstanceVO, paramsInstanceEntity);
        soarParamsDefEntity.insert(paramsInstanceEntity);
    }
}
