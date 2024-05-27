package com.daqinzhonggong.sb2.admin.modules.mnt.service.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import lombok.Data;

@Data
public class DeployHistoryDto implements Serializable {

  private static final long serialVersionUID = -1625726207738095286L;

  /**
   * 编号
   */
  private String id;

  /**
   * 应用名称
   */
  private String appName;

  /**
   * 部署IP
   */
  private String ip;

  /**
   * 部署时间
   */
  private Timestamp deployDate;

  /**
   * 部署人员
   */
  private String deployUser;

  /**
   * 部署编号
   */
  private Long deployId;

}
