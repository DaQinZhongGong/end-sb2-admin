package com.daqinzhonggong.sb2.admin.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

/**
 * 实体通用字段基类，根据需求自行添加
 */
@Getter
@Setter
public class BaseEntity implements Serializable {

  @CreatedBy
  @TableField(fill = FieldFill.INSERT)
  @ApiModelProperty(value = "创建人", hidden = true)
  private String createBy;

  @LastModifiedBy
  @TableField(fill = FieldFill.INSERT_UPDATE)
  @ApiModelProperty(value = "更新人", hidden = true)
  private String updateBy;

  @TableField(fill = FieldFill.INSERT)
  @ApiModelProperty(value = "创建时间", hidden = true)
  private Timestamp createTime;

  @TableField(fill = FieldFill.INSERT_UPDATE)
  @ApiModelProperty(value = "更新时间", hidden = true)
  private Timestamp updateTime;

  /* 分组校验 */
  public @interface Create {

  }

  /* 分组校验 */
  public @interface Update {

  }

  @Override
  public String toString() {
    ToStringBuilder builder = new ToStringBuilder(this);
    Field[] fields = this.getClass().getDeclaredFields();
    try {
      for (Field f : fields) {
        f.setAccessible(true);
        builder.append(f.getName(), f.get(this)).append("\n");
      }
    } catch (Exception e) {
      builder.append("toString builder encounter an error");
    }
    return builder.toString();
  }

}
