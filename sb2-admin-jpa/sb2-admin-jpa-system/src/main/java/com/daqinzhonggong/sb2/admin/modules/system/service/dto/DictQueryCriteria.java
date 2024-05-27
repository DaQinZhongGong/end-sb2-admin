package com.daqinzhonggong.sb2.admin.modules.system.service.dto;

import com.daqinzhonggong.sb2.admin.annotation.Query;
import lombok.Data;

/**
 * Dict 公共查询类
 */
@Data
public class DictQueryCriteria {

  @Query(blurry = "name,description")
  private String blurry;

}
