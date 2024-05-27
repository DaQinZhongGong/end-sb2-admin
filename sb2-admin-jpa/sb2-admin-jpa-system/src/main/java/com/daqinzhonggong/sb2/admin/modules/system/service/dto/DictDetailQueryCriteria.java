package com.daqinzhonggong.sb2.admin.modules.system.service.dto;

import com.daqinzhonggong.sb2.admin.annotation.Query;
import lombok.Data;

@Data
public class DictDetailQueryCriteria {

  @Query(type = Query.Type.INNER_LIKE)
  private String label;

  @Query(propName = "name", joinName = "dict")
  private String dictName;

}