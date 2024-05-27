package com.daqinzhonggong.sb2.admin.domain.vo;

import java.sql.Timestamp;
import java.util.List;
import lombok.Data;

/**
 * 日志查询类
 */
@Data
public class SysLogQueryCriteria {

  private String blurry;

  private String username;

  private String logType;

  private List<Timestamp> createTime;

}
