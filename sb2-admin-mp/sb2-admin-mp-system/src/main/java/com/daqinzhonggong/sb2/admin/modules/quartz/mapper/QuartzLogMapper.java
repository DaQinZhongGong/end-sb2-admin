package com.daqinzhonggong.sb2.admin.modules.quartz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.daqinzhonggong.sb2.admin.modules.quartz.domain.QuartzLog;
import com.daqinzhonggong.sb2.admin.modules.quartz.domain.vo.QuartzJobQueryCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface QuartzLogMapper extends BaseMapper<QuartzLog> {

  IPage<QuartzLog> findAll(@Param("criteria") QuartzJobQueryCriteria criteria, Page<Object> page);

  List<QuartzLog> findAll(@Param("criteria") QuartzJobQueryCriteria criteria);

}
