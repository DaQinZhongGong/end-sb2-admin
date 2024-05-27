package com.daqinzhonggong.sb2.admin.service.dto;

import com.daqinzhonggong.sb2.admin.annotation.Query;
import java.sql.Timestamp;
import java.util.List;
import lombok.Data;

/**
 * 日志查询类
 */
@Data
public class SysLogQueryCriteria {

  @Query(blurry = "username,description,address,requestIp,method,params")
  private String blurry;

  @Query
  private String username;

  @Query
  private String logType;

  @Query(type = Query.Type.BETWEEN)
  private List<Timestamp> createTime;
}
