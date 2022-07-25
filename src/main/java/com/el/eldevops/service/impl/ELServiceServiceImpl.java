package com.el.eldevops.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.el.eldevops.config.exception.BusinessException;
import com.el.eldevops.mapper.ELServiceMapper;
import com.el.eldevops.mapper.ServiceOnActivityMapper;
import com.el.eldevops.model.ELServiceEntity;
import com.el.eldevops.model.ServiceOnActivityEntity;
import com.el.eldevops.service.IELServiceService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @author YunTao.Li
 * @description:
 * @date 2022/7/21 10:03
 */
@Primary
@Component
public class ELServiceServiceImpl implements IELServiceService {

    @Resource
    private ServiceOnActivityMapper serviceOnActivityMapper;

    @Resource
    private ELServiceMapper elServiceMapper;

    /**
     * @return boolean
     * @description 服务绑定
     * @author YunTao.Li
     * @date 2022/7/22 10:24
     */
    @Override
    public boolean serviceInstBind(String bookDefId, String activityDefId, String serviceId) throws Exception {
        ServiceOnActivityEntity serviceOnActivityEntity = new ServiceOnActivityEntity();
        serviceOnActivityEntity.setBookId(bookDefId);
        serviceOnActivityEntity.setActivityId(activityDefId);
        serviceOnActivityEntity.setServiceId(serviceId);

        int executeRow = serviceOnActivityMapper.insert(serviceOnActivityEntity);

        if (executeRow > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean serviceInstUnBind(String bookDefId, String activityDefId, String serviceId) throws Exception {
        Optional op = Optional.ofNullable(bookDefId);
        if (!op.isPresent()) {
            throw new BusinessException("剧本定义ID不能为空");
        }
        Optional op2 = Optional.ofNullable(activityDefId);
        if (!op2.isPresent()) {
            throw new BusinessException("活动定义ID不能为空");
        }
        Optional op3 = Optional.ofNullable(serviceId);
        if (!op3.isPresent()) {
            throw new BusinessException("服务ID不能为空");
        }

        QueryWrapper<ServiceOnActivityEntity> queryWrapper = new QueryWrapper<ServiceOnActivityEntity>();
        queryWrapper.eq("book_id", bookDefId);
        queryWrapper.eq("activity_id", activityDefId);
        queryWrapper.eq("service_id", serviceId);

        int executeRow = serviceOnActivityMapper.delete(queryWrapper);

        if (executeRow > 0) {
            return true;
        }
        return false;
    }

    @Override
    public List<ELServiceEntity> getActivityService(String bookId, String activityId) {
        return this.elServiceMapper.getActivityService(bookId, activityId);
    }
}
