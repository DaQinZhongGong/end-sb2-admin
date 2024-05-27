package com.daqinzhonggong.sb2.admin.modules.system.service.dto;

import com.daqinzhonggong.sb2.admin.annotation.DataPermission;
import com.daqinzhonggong.sb2.admin.annotation.Query;
import java.sql.Timestamp;
import java.util.List;
import lombok.Data;


@Data
@DataPermission(fieldName = "id")
public class DeptQueryCriteria {

  @Query(type = Query.Type.INNER_LIKE)
  private String name;

  @Query
  private Boolean enabled;

  @Query
  private Long pid;

  @Query(type = Query.Type.IS_NULL, propName = "pid")
  private Boolean pidIsNull;

  @Query(type = Query.Type.BETWEEN)
  private List<Timestamp> createTime;

}