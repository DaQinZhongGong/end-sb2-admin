package com.daqinzhonggong.sb2.admin.modules.system.service.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.daqinzhonggong.sb2.admin.base.BaseDTO;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto extends BaseDTO implements Serializable {

  private static final long serialVersionUID = -4473180140276169640L;

  private Long id;

  private Set<RoleSmallDto> roles;

  private Set<JobSmallDto> jobs;

  private DeptSmallDto dept;

  private Long deptId;

  private String username;

  private String nickName;

  private String email;

  private String phone;

  private String gender;

  private String avatarName;

  private String avatarPath;

  @JSONField(serialize = false)
  private String password;

  private Boolean enabled;

  @JSONField(serialize = false)
  private Boolean isAdmin = false;

  private Date pwdResetTime;

}
