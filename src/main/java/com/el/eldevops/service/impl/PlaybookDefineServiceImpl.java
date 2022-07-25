package com.el.eldevops.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.el.eldevops.config.exception.BusinessException;
import com.el.eldevops.mapper.PlaybookDefineMapper;
import com.el.eldevops.model.PlaybookDefineEntity;
import com.el.eldevops.service.IPlaybookDefineService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author YunTao.Li
 * @description:
 * @date 2022/7/25 10:07
 */
@Primary
@Component
public class PlaybookDefineServiceImpl implements IPlaybookDefineService {

    @Resource
    private PlaybookDefineMapper playbookDefineMapper;

    @Override
    public PlaybookDefineEntity getPlaybookDefineByProcessDefId(String processDefId) {
        Optional op = Optional.ofNullable(processDefId);
        if (!op.isPresent()) {
            throw new BusinessException("PlaybookDefineServiceImpl:getPlaybookDefineByProcessDefId:流程定义ID不能为空");
        }

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("process_def_id", processDefId);

        return playbookDefineMapper.selectOne(queryWrapper);
    }
}
