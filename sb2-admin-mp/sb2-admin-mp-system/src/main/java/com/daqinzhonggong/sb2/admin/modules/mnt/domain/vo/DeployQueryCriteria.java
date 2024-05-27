package com.daqinzhonggong.sb2.admin.modules.mnt.domain.vo;

import java.sql.Timestamp;
import java.util.List;
import lombok.Data;

@Data
public class DeployQueryCriteria {

  private String appName;

  private List<Timestamp> createTime;

  private Long offset;

  private Long size;

}
