package com.daqinzhonggong.sb2.admin.modules.system.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.daqinzhonggong.sb2.admin.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "sys_dept")
public class Dept extends BaseEntity implements Serializable {

  private static final long serialVersionUID = -1422716786004053062L;

  @Id
  @Column(name = "dept_id")
  @NotNull(groups = Update.class)
  @ApiModelProperty(value = "ID", hidden = true)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JSONField(serialize = false)
  @ManyToMany(mappedBy = "depts")
  @ApiModelProperty(value = "角色")
  private Set<Role> roles;

  @ApiModelProperty(value = "排序")
  private Integer deptSort;

  @NotBlank
  @ApiModelProperty(value = "部门名称")
  private String name;

  @NotNull
  @ApiModelProperty(value = "是否启用")
  private Boolean enabled;

  @ApiModelProperty(value = "上级部门")
  private Long pid;

  @ApiModelProperty(value = "子节点数目", hidden = true)
  private Integer subCount = 0;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Dept dept = (Dept) o;
    return Objects.equals(id, dept.id) &&
        Objects.equals(name, dept.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name);
  }

}