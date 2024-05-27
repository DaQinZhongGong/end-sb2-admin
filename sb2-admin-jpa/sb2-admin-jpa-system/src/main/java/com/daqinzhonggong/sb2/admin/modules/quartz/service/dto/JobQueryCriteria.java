package com.daqinzhonggong.sb2.admin.modules.quartz.service.dto;

import com.daqinzhonggong.sb2.admin.annotation.Query;
import java.sql.Timestamp;
import java.util.List;
import lombok.Data;

@Data
public class JobQueryCriteria {

  @Query(type = Query.Type.INNER_LIKE)
  private String jobName;

  @Query
  private Boolean isSuccess;

  @Query(type = Query.Type.BETWEEN)
  private List<Timestamp> createTime;
}
