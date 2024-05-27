package com.daqinzhonggong.sb2.admin.modules.system.rest;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.daqinzhonggong.sb2.admin.annotation.Log;
import com.daqinzhonggong.sb2.admin.config.RsaProperties;
import com.daqinzhonggong.sb2.admin.exception.BadRequestException;
import com.daqinzhonggong.sb2.admin.modules.system.domain.Dept;
import com.daqinzhonggong.sb2.admin.modules.system.domain.Role;
import com.daqinzhonggong.sb2.admin.modules.system.domain.User;
import com.daqinzhonggong.sb2.admin.modules.system.domain.vo.UserPassVo;
import com.daqinzhonggong.sb2.admin.modules.system.domain.vo.UserQueryCriteria;
import com.daqinzhonggong.sb2.admin.modules.system.service.DataService;
import com.daqinzhonggong.sb2.admin.modules.system.service.DeptService;
import com.daqinzhonggong.sb2.admin.modules.system.service.RoleService;
import com.daqinzhonggong.sb2.admin.modules.system.service.UserService;
import com.daqinzhonggong.sb2.admin.modules.system.service.VerifyService;
import com.daqinzhonggong.sb2.admin.utils.PageResult;
import com.daqinzhonggong.sb2.admin.utils.PageUtil;
import com.daqinzhonggong.sb2.admin.utils.RsaUtils;
import com.daqinzhonggong.sb2.admin.utils.SecurityUtils;
import com.daqinzhonggong.sb2.admin.utils.enums.CodeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "系统：用户管理")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

  private final PasswordEncoder passwordEncoder;
  private final UserService userService;
  private final DataService dataService;
  private final DeptService deptService;
  private final RoleService roleService;
  private final VerifyService verificationCodeService;

  @ApiOperation("导出用户数据")
  @GetMapping(value = "/download")
  @PreAuthorize("@el.check('user:list')")
  public void exportUser(HttpServletResponse response, UserQueryCriteria criteria)
      throws IOException {
    userService.download(userService.queryAll(criteria), response);
  }

  @ApiOperation("查询用户")
  @GetMapping
  @PreAuthorize("@el.check('user:list')")
  public ResponseEntity<PageResult<User>> queryUser(UserQueryCriteria criteria, Page<Object> page) {
    if (!ObjectUtils.isEmpty(criteria.getDeptId())) {
      criteria.getDeptIds().add(criteria.getDeptId());
      // 先查找是否存在子节点
      List<Dept> data = deptService.findByPid(criteria.getDeptId());
      // 然后把子节点的ID都加入到集合中
      criteria.getDeptIds().addAll(deptService.getDeptChildren(data));
    }
    // 数据权限
    List<Long> dataScopes = dataService.getDeptIds(
        userService.findByName(SecurityUtils.getCurrentUsername()));
    // criteria.getDeptIds() 不为空并且数据权限不为空则取交集
    if (!CollectionUtils.isEmpty(criteria.getDeptIds()) && !CollectionUtils.isEmpty(dataScopes)) {
      // 取交集
      criteria.getDeptIds().retainAll(dataScopes);
      if (!CollectionUtil.isEmpty(criteria.getDeptIds())) {
        return new ResponseEntity<>(userService.queryAll(criteria, page), HttpStatus.OK);
      }
    } else {
      // 否则取并集
      criteria.getDeptIds().addAll(dataScopes);
      return new ResponseEntity<>(userService.queryAll(criteria, page), HttpStatus.OK);
    }
    return new ResponseEntity<>(PageUtil.noData(), HttpStatus.OK);
  }

  @Log("新增用户")
  @ApiOperation("新增用户")
  @PostMapping
  @PreAuthorize("@el.check('user:add')")
  public ResponseEntity<Object> createUser(@Validated @RequestBody User resources) {
    checkLevel(resources);
    // 默认密码 5t6FrY3LmDK584jqqyV8RBQXrcW7JPPP
    resources.setPassword(passwordEncoder.encode("5t6FrY3LmDK584jqqyV8RBQXrcW7JPPP"));
    userService.create(resources);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @Log("修改用户")
  @ApiOperation("修改用户")
  @PutMapping
  @PreAuthorize("@el.check('user:edit')")
  public ResponseEntity<Object> updateUser(
      @Validated(User.Update.class) @RequestBody User resources) throws Exception {
    checkLevel(resources);
    userService.update(resources);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @Log("修改用户：个人中心")
  @ApiOperation("修改用户：个人中心")
  @PutMapping(value = "center")
  public ResponseEntity<Object> centerUser(
      @Validated(User.Update.class) @RequestBody User resources) {
    if (!resources.getId().equals(SecurityUtils.getCurrentUserId())) {
      throw new BadRequestException("不能修改他人资料");
    }
    userService.updateCenter(resources);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @Log("删除用户")
  @ApiOperation("删除用户")
  @DeleteMapping
  @PreAuthorize("@el.check('user:del')")
  public ResponseEntity<Object> deleteUser(@RequestBody Set<Long> ids) {
    for (Long id : ids) {
      Integer currentLevel = Collections.min(
          roleService.findByUsersId(SecurityUtils.getCurrentUserId()).stream().map(
              Role::getLevel).collect(Collectors.toList()));
      Integer optLevel = Collections.min(
          roleService.findByUsersId(id).stream().map(Role::getLevel).collect(Collectors.toList()));
      if (currentLevel > optLevel) {
        throw new BadRequestException(
            "角色权限不足，不能删除：" + userService.findById(id).getUsername());
      }
    }
    userService.delete(ids);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @ApiOperation("修改密码")
  @PostMapping(value = "/updatePass")
  public ResponseEntity<Object> updateUserPass(@RequestBody UserPassVo passVo) throws Exception {
    String oldPass = RsaUtils.decryptByPrivateKey(RsaProperties.privateKey, passVo.getOldPass());
    String newPass = RsaUtils.decryptByPrivateKey(RsaProperties.privateKey, passVo.getNewPass());
    User user = userService.findByName(SecurityUtils.getCurrentUsername());
    if (!passwordEncoder.matches(oldPass, user.getPassword())) {
      throw new BadRequestException("修改失败，旧密码错误");
    }
    if (passwordEncoder.matches(newPass, user.getPassword())) {
      throw new BadRequestException("新密码不能与旧密码相同");
    }
    userService.updatePass(user.getUsername(), passwordEncoder.encode(newPass));
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @ApiOperation("重置密码")
  @PutMapping(value = "/resetPwd")
  public ResponseEntity<Object> resetPwd(@RequestBody Set<Long> ids) {
    String pwd = passwordEncoder.encode("5t6FrY3LmDK584jqqyV8RBQXrcW7JPPP");
    userService.resetPwd(ids, pwd);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @ApiOperation("修改头像")
  @PostMapping(value = "/updateAvatar")
  public ResponseEntity<Object> updateUserAvatar(@RequestParam MultipartFile avatar) {
    return new ResponseEntity<>(userService.updateAvatar(avatar), HttpStatus.OK);
  }

  @Log("修改邮箱")
  @ApiOperation("修改邮箱")
  @PostMapping(value = "/updateEmail/{code}")
  public ResponseEntity<Object> updateUserEmail(@PathVariable String code,
      @RequestBody User resources) throws Exception {
    String password = RsaUtils.decryptByPrivateKey(RsaProperties.privateKey,
        resources.getPassword());
    User user = userService.findByName(SecurityUtils.getCurrentUsername());
    if (!passwordEncoder.matches(password, user.getPassword())) {
      throw new BadRequestException("密码错误");
    }
    verificationCodeService.validated(CodeEnum.EMAIL_RESET_EMAIL_CODE.getKey() + user.getEmail(),
        code);
    userService.updateEmail(user.getUsername(), user.getEmail());
    return new ResponseEntity<>(HttpStatus.OK);
  }

  /**
   * 如果当前用户的角色级别低于创建用户的角色级别，则抛出权限不足的错误
   *
   * @param resources /
   */
  private void checkLevel(User resources) {
    Integer currentLevel = Collections.min(
        roleService.findByUsersId(SecurityUtils.getCurrentUserId()).stream().map(Role::getLevel)
            .collect(Collectors.toList()));
    Integer optLevel = roleService.findByRoles(resources.getRoles());
    if (currentLevel > optLevel) {
      throw new BadRequestException("角色权限不足");
    }
  }

}
