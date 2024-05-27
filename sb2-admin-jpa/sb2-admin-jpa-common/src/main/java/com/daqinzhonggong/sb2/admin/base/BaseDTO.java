package com.daqinzhonggong.sb2.admin.base;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 所有DTO继承的基类
 */
@Getter
@Setter
public class BaseDTO implements Serializable {

  private static final long serialVersionUID = -8271297273392100605L;

  private String createBy;

  private String updateBy;

  private Timestamp createTime;

  private Timestamp updateTime;

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
