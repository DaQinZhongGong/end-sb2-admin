package com.daqinzhonggong.sb2.admin.modules.mnt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.daqinzhonggong.sb2.admin.modules.mnt.domain.Server;
import com.daqinzhonggong.sb2.admin.modules.mnt.domain.vo.ServerQueryCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ServerMapper extends BaseMapper<Server> {

  Server findByIp(@Param("ip") String ip);

  IPage<Server> findAll(@Param("criteria") ServerQueryCriteria criteria, Page<Object> page);

  List<Server> findAll(@Param("criteria") ServerQueryCriteria criteria);

}
