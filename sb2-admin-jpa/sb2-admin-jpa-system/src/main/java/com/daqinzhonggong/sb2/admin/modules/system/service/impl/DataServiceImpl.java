package com.daqinzhonggong.sb2.admin.modules.system.service.impl;

import com.daqinzhonggong.sb2.admin.modules.system.domain.Dept;
import com.daqinzhonggong.sb2.admin.modules.system.service.DataService;
import com.daqinzhonggong.sb2.admin.modules.system.service.DeptService;
import com.daqinzhonggong.sb2.admin.modules.system.service.RoleService;
import com.daqinzhonggong.sb2.admin.modules.system.service.dto.RoleSmallDto;
import com.daqinzhonggong.sb2.admin.modules.system.service.dto.UserDto;
import com.daqinzhonggong.sb2.admin.utils.enums.DataScopeEnum;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * 数据权限服务实现
 **/
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "data")
public class DataServiceImpl implements DataService {

  private final RoleService roleService;
  private final DeptService deptService;

  /**
   * 用户角色和用户部门改变时需清理缓存
   *
   * @param user /
   * @return /
   */
  @Override
  @Cacheable(key = "'user:' + #p0.id")
  public List<Long> getDeptIds(UserDto user) {
    // 用于存储部门id
    Set<Long> deptIds = new HashSet<>();
    // 查询用户角色
    List<RoleSmallDto> roleSet = roleService.findByUsersId(user.getId());
    // 获取对应的部门ID
    for (RoleSmallDto role : roleSet) {
      DataScopeEnum dataScopeEnum = DataScopeEnum.find(role.getDataScope());
      switch (Objects.requireNonNull(dataScopeEnum)) {
        case THIS_LEVEL:
          deptIds.add(user.getDept().getId());
          break;
        case CUSTOMIZE:
          deptIds.addAll(getCustomize(deptIds, role));
          break;
        default:
          return new ArrayList<>();
      }
    }
    return new ArrayList<>(deptIds);
  }

  /**
   * 获取自定义的数据权限
   *
   * @param deptIds 部门ID
   * @param role    角色
   * @return 数据权限ID
   */
  public Set<Long> getCustomize(Set<Long> deptIds, RoleSmallDto role) {
    Set<Dept> depts = deptService.findByRoleId(role.getId());
    for (Dept dept : depts) {
      deptIds.add(dept.getId());
      List<Dept> deptChildren = deptService.findByPid(dept.getId());
      if (deptChildren != null && deptChildren.size() != 0) {
        deptIds.addAll(deptService.getDeptChildren(deptChildren));
      }
    }
    return deptIds;
  }

}
