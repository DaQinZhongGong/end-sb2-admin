package com.daqinzhonggong.sb2.admin.modules.system.domain;

import com.daqinzhonggong.sb2.admin.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "sys_job")
public class Job extends BaseEntity implements Serializable {

  private static final long serialVersionUID = -316370843346773740L;

  @Id
  @Column(name = "job_id")
  @NotNull(groups = Update.class)
  @ApiModelProperty(value = "ID", hidden = true)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @ApiModelProperty(value = "岗位名称")
  private String name;

  @NotNull
  @ApiModelProperty(value = "岗位排序")
  private Long jobSort;

  @NotNull
  @ApiModelProperty(value = "是否启用")
  private Boolean enabled;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Job job = (Job) o;
    return Objects.equals(id, job.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

}