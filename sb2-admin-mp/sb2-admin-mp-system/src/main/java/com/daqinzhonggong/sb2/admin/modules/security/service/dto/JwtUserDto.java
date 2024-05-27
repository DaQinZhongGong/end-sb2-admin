package com.daqinzhonggong.sb2.admin.modules.security.service.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.daqinzhonggong.sb2.admin.modules.system.domain.User;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@AllArgsConstructor
public class JwtUserDto implements UserDetails {

  private static final long serialVersionUID = -4124540401740728366L;

  private final User user;

  private final List<Long> dataScopes;

  private final List<AuthorityDto> authorities;

  public Set<String> getRoles() {
    return authorities.stream().map(AuthorityDto::getAuthority).collect(Collectors.toSet());
  }

  @Override
  @JSONField(serialize = false)
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  @JSONField(serialize = false)
  public String getUsername() {
    return user.getUsername();
  }

  @JSONField(serialize = false)
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @JSONField(serialize = false)
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @JSONField(serialize = false)
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  @JSONField(serialize = false)
  public boolean isEnabled() {
    return user.getEnabled();
  }

}
