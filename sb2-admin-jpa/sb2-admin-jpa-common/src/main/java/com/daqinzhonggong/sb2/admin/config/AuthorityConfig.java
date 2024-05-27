package com.daqinzhonggong.sb2.admin.config;

import com.daqinzhonggong.sb2.admin.utils.SecurityUtils;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Service(value = "el")
public class AuthorityConfig {

  public Boolean check(String... permissions) {
    // 获取当前用户的所有权限
    List<String> elPermissions = SecurityUtils.getCurrentUser().getAuthorities().stream()
        .map(GrantedAuthority::getAuthority).collect(Collectors.toList());
    // 判断当前用户的所有权限是否包含接口上定义的权限
    return elPermissions.contains("admin") || Arrays.stream(permissions)
        .anyMatch(elPermissions::contains);
  }

}
