package com.daqinzhonggong.sb2.admin.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.daqinzhonggong.sb2.admin.modules.system.domain.Role;
import com.daqinzhonggong.sb2.admin.modules.system.domain.vo.RoleQueryCriteria;
import java.util.List;
import java.util.Set;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {

  List<Role> queryAll();

  Role findById(@Param("roleId") Long roleId);

  Role findByName(@Param("name") String name);

  List<Role> findByUserId(@Param("userId") Long userId);

  Long countAll(@Param("criteria") RoleQueryCriteria criteria);

  List<Role> findAll(@Param("criteria") RoleQueryCriteria criteria);

  int countByDepts(@Param("deptIds") Set<Long> deptIds);

  @Select("SELECT role.role_id as id FROM sys_role role, sys_roles_menus rm " +
      "WHERE role.role_id = rm.role_id AND rm.menu_id = #{menuId}")
  List<Role> findByMenuId(@Param("menuId") Long menuId);

}
