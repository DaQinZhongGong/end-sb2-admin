package com.daqinzhonggong.sb2.admin.modules.system.service.dto;

import com.daqinzhonggong.sb2.admin.base.BaseDTO;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeptDto extends BaseDTO implements Serializable {

  private static final long serialVersionUID = 712205428654572600L;

  private Long id;

  private String name;

  private Boolean enabled;

  private Integer deptSort;

  private List<DeptDto> children;

  private Long pid;

  private Integer subCount;

  public Boolean getHasChildren() {
    return subCount > 0;
  }

  public Boolean getLeaf() {
    return subCount <= 0;
  }

  public String getLabel() {
    return name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DeptDto deptDto = (DeptDto) o;
    return Objects.equals(id, deptDto.id) &&
        Objects.equals(name, deptDto.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name);
  }

}