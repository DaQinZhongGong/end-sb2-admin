package com.daqinzhonggong.sb2.admin.modules.system.service;

import com.daqinzhonggong.sb2.admin.modules.system.domain.User;
import com.daqinzhonggong.sb2.admin.modules.system.service.dto.UserDto;
import com.daqinzhonggong.sb2.admin.modules.system.service.dto.UserLoginDto;
import com.daqinzhonggong.sb2.admin.modules.system.service.dto.UserQueryCriteria;
import com.daqinzhonggong.sb2.admin.utils.PageResult;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

  /**
   * 根据ID查询
   *
   * @param id ID
   * @return /
   */
  UserDto findById(long id);

  /**
   * 新增用户
   *
   * @param resources /
   */
  void create(User resources);

  /**
   * 编辑用户
   *
   * @param resources /
   * @throws Exception /
   */
  void update(User resources) throws Exception;

  /**
   * 删除用户
   *
   * @param ids /
   */
  void delete(Set<Long> ids);

  /**
   * 根据用户名查询
   *
   * @param userName /
   * @return /
   */
  UserDto findByName(String userName);

  /**
   * 根据用户名查询
   *
   * @param userName /
   * @return /
   */
  UserLoginDto getLoginData(String userName);

  /**
   * 修改密码
   *
   * @param username        用户名
   * @param encryptPassword 密码
   */
  void updatePass(String username, String encryptPassword);

  /**
   * 修改头像
   *
   * @param file 文件
   * @return /
   */
  Map<String, String> updateAvatar(MultipartFile file);

  /**
   * 修改邮箱
   *
   * @param username 用户名
   * @param email    邮箱
   */
  void updateEmail(String username, String email);

  /**
   * 查询全部
   *
   * @param criteria 条件
   * @param pageable 分页参数
   * @return /
   */
  PageResult<UserDto> queryAll(UserQueryCriteria criteria, Pageable pageable);

  /**
   * 查询全部不分页
   *
   * @param criteria 条件
   * @return /
   */
  List<UserDto> queryAll(UserQueryCriteria criteria);

  /**
   * 导出数据
   *
   * @param queryAll 待导出的数据
   * @param response /
   * @throws IOException /
   */
  void download(List<UserDto> queryAll, HttpServletResponse response) throws IOException;

  /**
   * 用户自助修改资料
   *
   * @param resources /
   */
  void updateCenter(User resources);

  /**
   * 重置密码
   *
   * @param ids 用户id
   * @param pwd 密码
   */
  void resetPwd(Set<Long> ids, String pwd);

}
