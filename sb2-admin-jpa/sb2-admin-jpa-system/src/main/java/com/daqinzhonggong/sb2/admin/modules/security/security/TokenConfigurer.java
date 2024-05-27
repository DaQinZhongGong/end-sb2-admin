package com.daqinzhonggong.sb2.admin.modules.security.security;

import com.daqinzhonggong.sb2.admin.modules.security.config.bean.SecurityProperties;
import com.daqinzhonggong.sb2.admin.modules.security.service.OnlineUserService;
import com.daqinzhonggong.sb2.admin.modules.security.service.UserCacheManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class TokenConfigurer extends
    SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

  private final TokenProvider tokenProvider;
  private final SecurityProperties properties;
  private final OnlineUserService onlineUserService;
  private final UserCacheManager userCacheManager;

  @Override
  public void configure(HttpSecurity http) {
    TokenFilter customFilter = new TokenFilter(tokenProvider, properties, onlineUserService,
        userCacheManager);
    http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
  }

}
