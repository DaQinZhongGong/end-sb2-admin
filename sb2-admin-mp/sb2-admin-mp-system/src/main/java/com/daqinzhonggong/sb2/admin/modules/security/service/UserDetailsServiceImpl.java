package com.daqinzhonggong.sb2.admin.modules.security.service;

import com.daqinzhonggong.sb2.admin.exception.BadRequestException;
import com.daqinzhonggong.sb2.admin.exception.EntityNotFoundException;
import com.daqinzhonggong.sb2.admin.modules.security.service.dto.JwtUserDto;
import com.daqinzhonggong.sb2.admin.modules.system.domain.User;
import com.daqinzhonggong.sb2.admin.modules.system.service.DataService;
import com.daqinzhonggong.sb2.admin.modules.system.service.RoleService;
import com.daqinzhonggong.sb2.admin.modules.system.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserService userService;
  private final RoleService roleService;
  private final DataService dataService;
  private final UserCacheManager userCacheManager;

  @Override
  public JwtUserDto loadUserByUsername(String username) {
    JwtUserDto jwtUserDto = userCacheManager.getUserCache(username);
    if (jwtUserDto == null) {
      User user;
      try {
        user = userService.getLoginData(username);
      } catch (EntityNotFoundException e) {
        // SpringSecurity会自动转换UsernameNotFoundException为BadCredentialsException
        throw new UsernameNotFoundException(username, e);
      }
      if (user == null) {
        throw new UsernameNotFoundException("");
      } else {
        if (!user.getEnabled()) {
          throw new BadRequestException("账号未激活！");
        }
        jwtUserDto = new JwtUserDto(
            user,
            dataService.getDeptIds(user),
            roleService.mapToGrantedAuthorities(user)
        );
        // 添加缓存数据
        userCacheManager.addUserCache(username, jwtUserDto);
      }
    }
    return jwtUserDto;
  }

}
