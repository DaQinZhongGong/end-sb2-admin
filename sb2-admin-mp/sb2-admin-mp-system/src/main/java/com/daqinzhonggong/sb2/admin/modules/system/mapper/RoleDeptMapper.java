package com.daqinzhonggong.sb2.admin.modules.system.mapper;

import com.daqinzhonggong.sb2.admin.modules.system.domain.Dept;
import java.util.Set;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RoleDeptMapper {

  void insertData(@Param("roleId") Long roleId, @Param("depts") Set<Dept> depts);

  void deleteByRoleId(@Param("roleId") Long roleId);

  void deleteByRoleIds(@Param("roleIds") Set<Long> roleIds);

}
