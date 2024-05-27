package com.daqinzhonggong.sb2.admin.modules.system.domain.vo;

import java.sql.Timestamp;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JobQueryCriteria {

  private String name;

  private Boolean enabled;

  private List<Timestamp> createTime;

}