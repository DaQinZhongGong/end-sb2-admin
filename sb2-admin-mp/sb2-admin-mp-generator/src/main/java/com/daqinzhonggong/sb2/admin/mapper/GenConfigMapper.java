package com.daqinzhonggong.sb2.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.daqinzhonggong.sb2.admin.domain.GenConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface GenConfigMapper extends BaseMapper<GenConfig> {

  GenConfig findByTableName(@Param("tableName") String tableName);

}
