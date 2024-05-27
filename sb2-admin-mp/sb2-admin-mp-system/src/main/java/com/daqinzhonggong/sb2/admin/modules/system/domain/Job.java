package com.daqinzhonggong.sb2.admin.modules.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.daqinzhonggong.sb2.admin.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("sys_job")
public class Job extends BaseEntity implements Serializable {

  private static final long serialVersionUID = -316370843346773740L;

  @NotNull(groups = Update.class)
  @TableId(value = "job_id", type = IdType.AUTO)
  @ApiModelProperty(value = "ID", hidden = true)
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