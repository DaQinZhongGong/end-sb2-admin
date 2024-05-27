package com.daqinzhonggong.sb2.admin.service.dto;

import com.daqinzhonggong.sb2.admin.annotation.Query;
import java.sql.Timestamp;
import java.util.List;
import lombok.Data;

@Data
public class LocalStorageQueryCriteria {

  @Query(blurry = "name,suffix,type,createBy,size")
  private String blurry;

  @Query(type = Query.Type.BETWEEN)
  private List<Timestamp> createTime;

}