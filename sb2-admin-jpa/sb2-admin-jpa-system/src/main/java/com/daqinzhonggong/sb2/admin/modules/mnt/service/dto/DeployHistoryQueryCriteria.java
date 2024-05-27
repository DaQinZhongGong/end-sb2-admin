package com.daqinzhonggong.sb2.admin.modules.mnt.service.dto;

import com.daqinzhonggong.sb2.admin.annotation.Query;
import java.sql.Timestamp;
import java.util.List;
import lombok.Data;

@Data
public class DeployHistoryQueryCriteria {

  /**
   * 精确
   */
  @Query(blurry = "appName,ip,deployUser")
  private String blurry;

  @Query
  private Long deployId;

  @Query(type = Query.Type.BETWEEN)
  private List<Timestamp> deployDate;

}
