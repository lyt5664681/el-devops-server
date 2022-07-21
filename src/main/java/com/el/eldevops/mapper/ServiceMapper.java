package com.el.eldevops.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.el.eldevops.model.ServiceEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author YunTao.Li
 * @description:
 * @date 2022/7/21 10:03
 */
@Mapper
public interface ServiceMapper extends BaseMapper<ServiceEntity> {
}
