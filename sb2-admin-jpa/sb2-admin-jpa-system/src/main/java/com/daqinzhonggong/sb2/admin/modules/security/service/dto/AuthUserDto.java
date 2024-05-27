package com.daqinzhonggong.sb2.admin.modules.security.service.dto;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthUserDto {

  @NotBlank
  private String username;

  @NotBlank
  private String password;

  private String code;

  private String uuid = "";

}
