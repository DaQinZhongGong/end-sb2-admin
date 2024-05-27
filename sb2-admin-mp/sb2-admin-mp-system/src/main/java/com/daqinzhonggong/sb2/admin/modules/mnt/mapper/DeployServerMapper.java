package com.daqinzhonggong.sb2.admin.modules.mnt.mapper;

import com.daqinzhonggong.sb2.admin.modules.mnt.domain.Server;
import java.util.Set;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DeployServerMapper {

  void insertData(@Param("deployId") Long deployId, @Param("servers") Set<Server> servers);

  void deleteByDeployId(@Param("deployId") Long deployId);

  void deleteByDeployIds(@Param("deployIds") Set<Long> deployIds);

  void deleteByServerIds(@Param("serverIds") Set<Long> serverIds);
}
