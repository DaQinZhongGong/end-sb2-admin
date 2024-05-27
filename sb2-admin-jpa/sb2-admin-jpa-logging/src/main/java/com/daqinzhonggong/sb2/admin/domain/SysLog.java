package com.daqinzhonggong.sb2.admin.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Getter
@Setter
@Table(name = "sys_log")
@NoArgsConstructor
public class SysLog implements Serializable {

  @Id
  @Column(name = "log_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * 操作用户
   */
  private String username;

  /**
   * 描述
   */
  private String description;

  /**
   * 方法名
   */
  private String method;

  /**
   * 参数
   */
  private String params;

  /**
   * 日志类型
   */
  private String logType;

  /**
   * 请求ip
   */
  private String requestIp;

  /**
   * 地址
   */
  private String address;

  /**
   * 浏览器
   */
  private String browser;

  /**
   * 请求耗时
   */
  private Long time;

  /**
   * 异常详细
   */
  private byte[] exceptionDetail;

  /**
   * 创建日期
   */
  @CreationTimestamp
  private Timestamp createTime;

  public SysLog(String logType, Long time) {
    this.logType = logType;
    this.time = time;
  }
}
