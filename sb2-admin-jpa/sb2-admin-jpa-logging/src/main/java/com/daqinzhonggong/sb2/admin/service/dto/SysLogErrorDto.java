package com.daqinzhonggong.sb2.admin.service.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import lombok.Data;

@Data
public class SysLogErrorDto implements Serializable {

  private static final long serialVersionUID = -2244587344580260688L;

  private Long id;

  private String username;

  private String description;

  private String method;

  private String params;

  private String browser;

  private String requestIp;

  private String address;

  private Timestamp createTime;
}