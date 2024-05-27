package com.daqinzhonggong.sb2.admin.modules.system.domain.vo;

import java.sql.Timestamp;
import java.util.List;
import lombok.Data;

@Data
public class DeptQueryCriteria {

  private List<Long> ids;

  private String name;

  private Boolean enabled;

  private Long pid;

  private Boolean pidIsNull;

  private List<Timestamp> createTime;

}