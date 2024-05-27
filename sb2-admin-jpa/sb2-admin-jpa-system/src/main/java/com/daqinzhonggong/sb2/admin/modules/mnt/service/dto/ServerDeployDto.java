package com.daqinzhonggong.sb2.admin.modules.mnt.service.dto;

import com.daqinzhonggong.sb2.admin.base.BaseDTO;
import java.io.Serializable;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServerDeployDto extends BaseDTO implements Serializable {

  private static final long serialVersionUID = -320332709377571499L;

  private Long id;

  private String name;

  private String ip;

  private Integer port;

  private String account;

  private String password;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ServerDeployDto that = (ServerDeployDto) o;
    return Objects.equals(id, that.id) &&
        Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name);
  }

}
