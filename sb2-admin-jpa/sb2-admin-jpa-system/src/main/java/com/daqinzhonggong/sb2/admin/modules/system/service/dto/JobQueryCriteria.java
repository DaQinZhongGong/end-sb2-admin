package com.daqinzhonggong.sb2.admin.modules.system.service.dto;

import com.daqinzhonggong.sb2.admin.annotation.Query;
import java.sql.Timestamp;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JobQueryCriteria {

  @Query(type = Query.Type.INNER_LIKE)
  private String name;

  @Query
  private Boolean enabled;

  @Query(type = Query.Type.BETWEEN)
  private List<Timestamp> createTime;

}