package com.daqinzhonggong.sb2.admin.modules.system.service.dto;

import com.daqinzhonggong.sb2.admin.annotation.Query;
import java.sql.Timestamp;
import java.util.List;
import lombok.Data;

@Data
public class RoleQueryCriteria {

  @Query(blurry = "name,description")
  private String blurry;

  @Query(type = Query.Type.BETWEEN)
  private List<Timestamp> createTime;

}
