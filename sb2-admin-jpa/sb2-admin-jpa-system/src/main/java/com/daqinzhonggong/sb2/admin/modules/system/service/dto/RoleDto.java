package com.daqinzhonggong.sb2.admin.modules.system.service.dto;

import com.daqinzhonggong.sb2.admin.base.BaseDTO;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleDto extends BaseDTO implements Serializable {

  private static final long serialVersionUID = 4327652698590783537L;

  private Long id;

  private Set<MenuDto> menus;

  private Set<DeptDto> depts;

  private String name;

  private String dataScope;

  private Integer level;

  private String description;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RoleDto roleDto = (RoleDto) o;
    return Objects.equals(id, roleDto.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

}
