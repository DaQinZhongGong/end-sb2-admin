package com.daqinzhonggong.sb2.admin.service.dto;

import com.daqinzhonggong.sb2.admin.annotation.Query;
import java.sql.Timestamp;
import java.util.List;
import lombok.Data;

@Data
public class QiniuQueryCriteria {

  @Query(type = Query.Type.INNER_LIKE)
  private String key;

  @Query(type = Query.Type.BETWEEN)
  private List<Timestamp> createTime;

}
