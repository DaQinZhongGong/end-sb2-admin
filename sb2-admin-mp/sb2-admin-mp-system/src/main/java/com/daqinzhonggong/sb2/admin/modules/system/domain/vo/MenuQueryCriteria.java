package com.daqinzhonggong.sb2.admin.modules.system.domain.vo;

import java.sql.Timestamp;
import java.util.List;
import lombok.Data;

@Data
public class MenuQueryCriteria {

  private String blurry;

  private List<Timestamp> createTime;

  private Boolean pidIsNull;

  private Long pid;

}
