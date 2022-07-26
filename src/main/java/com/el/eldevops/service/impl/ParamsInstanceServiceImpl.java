package com.el.eldevops.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.el.eldevops.mapper.ParamsInstanceMapper;
import com.el.eldevops.model.ParamsInstanceEntity;
import com.el.eldevops.model.vo.ParamsInstanceVO;
import com.el.eldevops.service.IParamsInstanceService;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author YunTao.Li
 * @description:
 * @date 2022/7/21 10:34
 */
@Primary
@Component
public class ParamsInstanceServiceImpl implements IParamsInstanceService {

    @Resource
    private ParamsInstanceMapper paramsInstanceMapper;


    @Override
    public void paramsNewInstance(ParamsInstanceVO paramsInstanceVO) throws Exception {
        ParamsInstanceEntity paramsInstanceEntity = new ParamsInstanceEntity();
        BeanUtils.copyProperties(paramsInstanceVO, paramsInstanceEntity);
        paramsInstanceMapper.insert(paramsInstanceEntity);
    }

    /**
     * @param bookDefId     剧本定义ID
     * @param activityDefId 活动定义ID
     * @param serviceId     服务ID
     * @return java.util.List<com.el.eldevops.model.ParamsInstanceEntity>
     * @description 查询服务绑定的参数实例列表
     * @author YunTao.Li
     * @date 2022/7/25 17:15
     */
    @Override
    public List<ParamsInstanceEntity> getParamsInstance(String bookDefId, String activityDefId, String serviceId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("book_def_id", bookDefId);
        queryWrapper.eq("activity_def_id", activityDefId);
        queryWrapper.eq("service_id", serviceId);
        queryWrapper.orderByAsc("param_seq");

        List<ParamsInstanceEntity> paramsInstance = paramsInstanceMapper.selectList(queryWrapper);
        return paramsInstance;
    }
}
