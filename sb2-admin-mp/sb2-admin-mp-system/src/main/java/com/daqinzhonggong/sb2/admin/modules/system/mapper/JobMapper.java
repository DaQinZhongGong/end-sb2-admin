package com.daqinzhonggong.sb2.admin.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.daqinzhonggong.sb2.admin.modules.system.domain.Job;
import com.daqinzhonggong.sb2.admin.modules.system.domain.vo.JobQueryCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface JobMapper extends BaseMapper<Job> {

  @Select("select job_id as id from sys_job where name = #{name}")
  Job findByName(@Param("name") String name);

  List<Job> findAll(@Param("criteria") JobQueryCriteria criteria);

  IPage<Job> findAll(@Param("criteria") JobQueryCriteria criteria, Page<Object> page);

}