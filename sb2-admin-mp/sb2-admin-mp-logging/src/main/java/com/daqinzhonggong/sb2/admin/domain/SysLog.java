package com.daqinzhonggong.sb2.admin.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.sql.Timestamp;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@TableName("sys_log")
public class SysLog implements Serializable {

  private static final long serialVersionUID = -8659482646623891965L;

  @TableId(value = "log_id", type = IdType.AUTO)
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
  @JSONField(serialize = false)
  private String exceptionDetail;

  /**
   * 创建日期
   */
  @TableField(fill = FieldFill.INSERT)
  private Timestamp createTime;

  public SysLog(String logType, Long time) {
    this.logType = logType;
    this.time = time;
  }

}
