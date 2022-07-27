package com.el.eldevops.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.el.eldevops.config.exception.BusinessException;
import com.el.eldevops.mapper.PlaybookDefineMapper;
import com.el.eldevops.model.PlaybookDefineEntity;
import com.el.eldevops.model.vo.PlaybookDefineVO;
import com.el.eldevops.service.IPlaybookDefineService;
import com.el.eldevops.util.Constants;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.repository.DeploymentBuilder;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.repository.ProcessDefinitionQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

/**
 * @author YunTao.Li
 * @description:
 * @date 2022/7/25 10:07
 */
@Primary
@Component
public class PlaybookDefineServiceImpl implements IPlaybookDefineService {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

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

    @Override
    public void createPlaybookDefine(PlaybookDefineVO playbookDefineVO) {

    }

    @Override
    public void deployPlaybookByDefId(String bookDefId) {
        // step1 : 根据剧本id查询剧本信息
        PlaybookDefineEntity playbookDefineEntity = playbookDefineMapper.selectById(bookDefId);
        if (playbookDefineEntity == null) {
            throw new BusinessException("部署剧本失败，未根据剧本id找到剧本");
        }

        // step2 : 整理剧本信息
        String playbookName = playbookDefineEntity.getBookName();
        String xmlString = playbookDefineEntity.getBookXml();
        String bookKey = playbookDefineEntity.getBookDefKey();
        final String suffix = ".bpmn20.xml";
        String processDefId = "";
        String processDefKey = "";
        try {
            // step3 : 调用引擎接口部署剧本为流程定义
            DeploymentBuilder builder = repositoryService.createDeployment().addString(bookKey + suffix, xmlString);
            builder.name(playbookName);
            Deployment deploy = builder.deploy();
            String deployId = deploy.getId();

            // step4 : 查询定义是否部署成功
            ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery().deploymentId(deployId);
            ProcessDefinition processDefinition = processDefinitionQuery.singleResult();
            processDefId = processDefinition.getId();
            processDefKey = processDefinition.getKey();
            if (processDefinition == null) {
                throw new BusinessException("剧本部署失败");
            }
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }

        // step5: 部署成功更新剧本信息:回写流程定义id,更改剧本状态
        playbookDefineEntity.setProcessDefId(processDefId);
        playbookDefineEntity.setBookStatus(Constants.PLAYBOOK_STATUS_DEPLOYED);
        playbookDefineEntity.setProcessDefKey(processDefKey);

        this.playbookDefineMapper.updateById(playbookDefineEntity);
    }

    @Override
    public void startPlaybookByDefId(String bookDefId) {

    }

    @Override
    public void startPlaybookWithCondition(String bookDefId, Map condition) {

    }
}
