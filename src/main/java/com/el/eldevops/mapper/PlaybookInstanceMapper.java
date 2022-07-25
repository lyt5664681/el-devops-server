package com.el.eldevops.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.el.eldevops.model.PlaybookInstEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author YunTao.Li
 * @description:
 * @date 2022/7/25 11:04
 */
@Mapper
public interface PlaybookInstanceMapper extends BaseMapper<PlaybookInstEntity> {
}
