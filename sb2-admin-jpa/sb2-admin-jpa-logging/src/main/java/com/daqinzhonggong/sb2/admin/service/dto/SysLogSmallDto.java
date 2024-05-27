package com.daqinzhonggong.sb2.admin.service.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import lombok.Data;

@Data
public class SysLogSmallDto implements Serializable {

  private static final long serialVersionUID = -6924127575717917816L;

  private String description;

  private String requestIp;

  private Long time;

  private String address;

  private String browser;

  private Timestamp createTime;
}
