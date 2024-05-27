package com.daqinzhonggong.sb2.admin.modules.security.service.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 在线用户
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OnlineUserDto {

  /**
   * 用户名
   */
  private String userName;

  /**
   * 昵称
   */
  private String nickName;

  /**
   * 岗位
   */
  private String dept;

  /**
   * 浏览器
   */
  private String browser;

  /**
   * IP
   */
  private String ip;

  /**
   * 地址
   */
  private String address;

  /**
   * token
   */
  private String key;

  /**
   * 登录时间
   */
  private Date loginTime;


}
