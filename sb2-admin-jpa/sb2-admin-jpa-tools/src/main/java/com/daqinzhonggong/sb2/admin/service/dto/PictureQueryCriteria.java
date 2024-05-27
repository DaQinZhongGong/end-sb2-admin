package com.daqinzhonggong.sb2.admin.service.dto;

import com.daqinzhonggong.sb2.admin.annotation.Query;
import java.sql.Timestamp;
import java.util.List;
import lombok.Data;

@Data
public class PictureQueryCriteria {

  @Query(type = Query.Type.INNER_LIKE)
  private String filename;

  @Query(type = Query.Type.INNER_LIKE)
  private String username;

  @Query(type = Query.Type.BETWEEN)
  private List<Timestamp> createTime;

}
