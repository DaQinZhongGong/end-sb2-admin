package com.daqinzhonggong.sb2.admin.modules.quartz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.daqinzhonggong.sb2.admin.modules.quartz.domain.QuartzJob;
import com.daqinzhonggong.sb2.admin.modules.quartz.domain.vo.QuartzJobQueryCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface QuartzJobMapper extends BaseMapper<QuartzJob> {

  IPage<QuartzJob> findAll(@Param("criteria") QuartzJobQueryCriteria criteria, Page<Object> page);

  List<QuartzJob> findAll(@Param("criteria") QuartzJobQueryCriteria criteria);

  List<QuartzJob> findByIsPauseIsFalse();

}
