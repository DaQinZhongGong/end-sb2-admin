package com.daqinzhonggong.sb2.admin.modules.system.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.daqinzhonggong.sb2.admin.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("sys_dept")
public class Dept extends BaseEntity implements Serializable {

  private static final long serialVersionUID = 5148142643520828440L;

  @NotNull(groups = Update.class)
  @TableId(value = "dept_id", type = IdType.AUTO)
  @ApiModelProperty(value = "ID", hidden = true)
  private Long id;

  @TableField(exist = false)
  @JSONField(serialize = false)
  @ApiModelProperty(value = "角色")
  private Set<Role> roles;

  @TableField(exist = false)
  private List<Dept> children;

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

  public Boolean getHasChildren() {
    return subCount > 0;
  }

  public Boolean getLeaf() {
    return subCount <= 0;
  }

  public String getLabel() {
    return name;
  }

}