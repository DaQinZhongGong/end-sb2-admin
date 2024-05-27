package com.daqinzhonggong.sb2.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.daqinzhonggong.sb2.admin.domain.SysLog;
import com.daqinzhonggong.sb2.admin.domain.vo.SysLogQueryCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface SysLogMapper extends BaseMapper<SysLog> {

  List<SysLog> queryAll(@Param("criteria") SysLogQueryCriteria criteria);

  IPage<SysLog> queryAll(@Param("criteria") SysLogQueryCriteria criteria, Page<SysLog> page);

  IPage<SysLog> queryAllByUser(@Param("criteria") SysLogQueryCriteria criteria, Page<SysLog> page);

  String getExceptionDetails(@Param("id") Long id);

  void deleteByLevel(@Param("logType") String logType);
}
