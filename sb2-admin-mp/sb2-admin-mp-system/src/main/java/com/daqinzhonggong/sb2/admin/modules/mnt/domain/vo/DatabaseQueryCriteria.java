package com.daqinzhonggong.sb2.admin.modules.mnt.domain.vo;

import java.sql.Timestamp;
import java.util.List;
import lombok.Data;

@Data
public class DatabaseQueryCriteria {

  private String name;

  private String jdbcUrl;

  private List<Timestamp> createTime;

}
