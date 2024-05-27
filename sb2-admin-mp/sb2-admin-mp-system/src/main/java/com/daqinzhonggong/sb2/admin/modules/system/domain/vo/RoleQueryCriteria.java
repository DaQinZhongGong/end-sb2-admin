package com.daqinzhonggong.sb2.admin.modules.system.domain.vo;

import java.sql.Timestamp;
import java.util.List;
import lombok.Data;

/**
 * 公共查询类
 */
@Data
public class RoleQueryCriteria {

  private String blurry;

  private List<Timestamp> createTime;

  private Long offset;

  private Long size;

}
