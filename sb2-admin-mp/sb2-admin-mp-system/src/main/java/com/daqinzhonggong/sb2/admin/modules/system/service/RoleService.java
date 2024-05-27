package com.daqinzhonggong.sb2.admin.modules.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.daqinzhonggong.sb2.admin.modules.security.service.dto.AuthorityDto;
import com.daqinzhonggong.sb2.admin.modules.system.domain.Role;
import com.daqinzhonggong.sb2.admin.modules.system.domain.User;
import com.daqinzhonggong.sb2.admin.modules.system.domain.vo.RoleQueryCriteria;
import com.daqinzhonggong.sb2.admin.utils.PageResult;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;

public interface RoleService extends IService<Role> {

  /**
   * 查询全部数据
   *
   * @return /
   */
  List<Role> queryAll();

  /**
   * 根据ID查询
   *
   * @param id /
   * @return /
   */
  Role findById(long id);

  /**
   * 创建
   *
   * @param resources /
   */
  void create(Role resources);

  /**
   * 编辑
   *
   * @param resources /
   */
  void update(Role resources);

  /**
   * 删除
   *
   * @param ids /
   */
  void delete(Set<Long> ids);

  /**
   * 根据用户ID查询
   *
   * @param userId 用户ID
   * @return /
   */
  List<Role> findByUsersId(Long userId);

  /**
   * 根据角色查询角色级别
   *
   * @param roles /
   * @return /
   */
  Integer findByRoles(Set<Role> roles);

  /**
   * 修改绑定的菜单
   *
   * @param resources /
   */
  void updateMenu(Role resources);

  /**
   * 待条件分页查询
   *
   * @param criteria 条件
   * @param page     分页参数
   * @return /
   */
  PageResult<Role> queryAll(RoleQueryCriteria criteria, Page<Object> page);

  /**
   * 查询全部
   *
   * @param criteria 条件
   * @return /
   */
  List<Role> queryAll(RoleQueryCriteria criteria);

  /**
   * 导出数据
   *
   * @param roles    待导出的数据
   * @param response /
   * @throws IOException /
   */
  void download(List<Role> roles, HttpServletResponse response) throws IOException;

  /**
   * 获取用户权限信息
   *
   * @param user 用户信息
   * @return 权限信息
   */
  List<AuthorityDto> mapToGrantedAuthorities(User user);

  /**
   * 验证是否被用户关联
   *
   * @param ids /
   */
  void verification(Set<Long> ids);

  /**
   * 根据菜单Id查询
   *
   * @param menuId /
   * @return /
   */
  List<Role> findByMenuId(Long menuId);

}
