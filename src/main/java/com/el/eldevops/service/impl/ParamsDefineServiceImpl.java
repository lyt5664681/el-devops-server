package com.el.eldevops.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.el.eldevops.mapper.ParamsDefineMapper;
import com.el.eldevops.model.ParamsDefineEntity;
import com.el.eldevops.service.IParamsDefineService;
import com.el.eldevops.util.Constants;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author YunTao.Li
 * @description:
 * @date 2022/7/21 10:36
 */
@Primary
@Component
public class ParamsDefineServiceImpl implements IParamsDefineService {

    @Resource
    private ParamsDefineMapper paramsDefineMapper;

    @Override
    public List<ParamsDefineEntity> getParamsByService(String serviceId) throws Throwable {
        QueryWrapper<ParamsDefineEntity> paramsDefineEntityQueryWrapper = new QueryWrapper<>();
        paramsDefineEntityQueryWrapper.eq("service_id", serviceId);
        paramsDefineEntityQueryWrapper.eq("param_of", Constants.PARAMS_OF_SERVICE);
        paramsDefineEntityQueryWrapper.orderByAsc("param_seq");

        List<ParamsDefineEntity> paramsDefineEntities = paramsDefineMapper.selectList(paramsDefineEntityQueryWrapper);
        return paramsDefineEntities;
    }

    @Override
    public List<ParamsDefineEntity> getParamsByBook(String bookDefId) throws Throwable {
        QueryWrapper<ParamsDefineEntity> paramsDefineEntityQueryWrapper = new QueryWrapper<>();
        paramsDefineEntityQueryWrapper.eq("book_def_id", bookDefId);
        paramsDefineEntityQueryWrapper.eq("param_of", Constants.PARAMS_OF_GLOBAL);
        paramsDefineEntityQueryWrapper.orderByAsc("param_seq");

        List<ParamsDefineEntity> paramsDefineEntities = paramsDefineMapper.selectList(paramsDefineEntityQueryWrapper);
        return paramsDefineEntities;
    }
}
