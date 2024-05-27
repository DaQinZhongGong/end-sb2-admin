package com.daqinzhonggong.sb2.admin.modules.mnt.service.dto;

import com.daqinzhonggong.sb2.admin.annotation.Query;
import java.sql.Timestamp;
import java.util.List;
import lombok.Data;

@Data
public class AppQueryCriteria {

  /**
   * 模糊
   */
  @Query(type = Query.Type.INNER_LIKE)
  private String name;

  @Query(type = Query.Type.BETWEEN)
  private List<Timestamp> createTime;

}
