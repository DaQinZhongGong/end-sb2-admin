package com.daqinzhonggong.sb2.admin.modules.system.mapper;

import com.daqinzhonggong.sb2.admin.modules.system.domain.Menu;
import java.util.Set;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RoleMenuMapper {

  void insertData(@Param("roleId") Long roleId, @Param("menus") Set<Menu> menus);

  void deleteByRoleId(@Param("roleId") Long roleId);

  void deleteByRoleIds(@Param("roleIds") Set<Long> roleIds);

  void deleteByMenuId(@Param("menuId") Long menuId);

}
