package com.daqinzhonggong.sb2.admin.modules.mnt.service.dto;

import com.daqinzhonggong.sb2.admin.annotation.Query;
import java.sql.Timestamp;
import java.util.List;
import lombok.Data;

@Data
public class ServerDeployQueryCriteria {

  /**
   * 模糊
   */
  @Query(blurry = "name,ip,account")
  private String blurry;

  @Query(type = Query.Type.BETWEEN)
  private List<Timestamp> createTime;

}
