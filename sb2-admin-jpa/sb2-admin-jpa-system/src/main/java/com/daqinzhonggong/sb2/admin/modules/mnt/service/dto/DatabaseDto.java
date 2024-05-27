package com.daqinzhonggong.sb2.admin.modules.mnt.service.dto;

import com.daqinzhonggong.sb2.admin.base.BaseDTO;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DatabaseDto extends BaseDTO implements Serializable {

  private static final long serialVersionUID = -5131698471892608394L;

  /**
   * id
   */
  private String id;

  /**
   * 数据库名称
   */
  private String name;

  /**
   * 数据库连接地址
   */
  private String jdbcUrl;

  /**
   * 数据库密码
   */
  private String pwd;

  /**
   * 用户名
   */
  private String userName;

}
