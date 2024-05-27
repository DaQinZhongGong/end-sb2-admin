package com.daqinzhonggong.sb2.admin.modules.system.mapper;

import com.daqinzhonggong.sb2.admin.modules.system.domain.Role;
import java.util.Set;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserRoleMapper {

  void insertData(@Param("userId") Long userId, @Param("roles") Set<Role> roles);

  void deleteByUserId(@Param("userId") Long userId);

  void deleteByUserIds(@Param("userIds") Set<Long> userIds);

}
