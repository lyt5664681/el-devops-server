package com.el.eldevops.service.impl;

import com.el.eldevops.mapper.PlaybookInstanceMapper;
import com.el.eldevops.model.PlaybookInstEntity;
import com.el.eldevops.service.IPlaybookInstanceService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author YunTao.Li
 * @description:
 * @date 2022/7/25 11:03
 */
@Primary
@Component
public class PlaybookInstanceServiceImpl implements IPlaybookInstanceService {

    @Resource
    private PlaybookInstanceMapper playbookInstanceMapper;

    @Override
    public void insert(PlaybookInstEntity playbookInstEntity) {
        playbookInstanceMapper.insert(playbookInstEntity);
    }
}
