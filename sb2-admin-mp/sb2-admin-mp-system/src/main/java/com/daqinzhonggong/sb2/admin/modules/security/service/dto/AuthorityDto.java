package com.daqinzhonggong.sb2.admin.modules.security.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

/**
 * 避免序列化问题
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorityDto implements GrantedAuthority {

  private static final long serialVersionUID = -1180665622654803875L;

  private String authority;
}
