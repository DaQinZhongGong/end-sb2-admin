package com.daqinzhonggong.sb2.admin.modules.system.domain;

import com.daqinzhonggong.sb2.admin.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "sys_user")
public class User extends BaseEntity implements Serializable {

  private static final long serialVersionUID = 398514619498998291L;

  @Id
  @Column(name = "user_id")
  @NotNull(groups = Update.class)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @ApiModelProperty(value = "ID", hidden = true)
  private Long id;

  @ManyToMany(fetch = FetchType.EAGER)
  @ApiModelProperty(value = "用户角色")
  @JoinTable(name = "sys_users_roles",
      joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
      inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "role_id")})
  private Set<Role> roles;

  @ManyToMany(fetch = FetchType.EAGER)
  @ApiModelProperty(value = "用户岗位")
  @JoinTable(name = "sys_users_jobs",
      joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
      inverseJoinColumns = {@JoinColumn(name = "job_id", referencedColumnName = "job_id")})
  private Set<Job> jobs;

  @OneToOne
  @JoinColumn(name = "dept_id")
  @ApiModelProperty(value = "用户部门")
  private Dept dept;

  @NotBlank
  @Column(unique = true)
  @ApiModelProperty(value = "用户名称")
  private String username;

  @NotBlank
  @ApiModelProperty(value = "用户昵称")
  private String nickName;

  @Email
  @NotBlank
  @ApiModelProperty(value = "邮箱")
  private String email;

  @NotBlank
  @ApiModelProperty(value = "电话号码")
  private String phone;

  @ApiModelProperty(value = "用户性别")
  private String gender;

  @ApiModelProperty(value = "头像真实名称", hidden = true)
  private String avatarName;

  @ApiModelProperty(value = "头像存储的路径", hidden = true)
  private String avatarPath;

  @ApiModelProperty(value = "密码")
  private String password;

  @NotNull
  @ApiModelProperty(value = "是否启用")
  private Boolean enabled;

  @ApiModelProperty(value = "是否为admin账号", hidden = true)
  private Boolean isAdmin = false;

  @Column(name = "pwd_reset_time")
  @ApiModelProperty(value = "最后修改密码的时间", hidden = true)
  private Date pwdResetTime;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User user = (User) o;
    return Objects.equals(id, user.id) &&
        Objects.equals(username, user.username);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, username);
  }

}