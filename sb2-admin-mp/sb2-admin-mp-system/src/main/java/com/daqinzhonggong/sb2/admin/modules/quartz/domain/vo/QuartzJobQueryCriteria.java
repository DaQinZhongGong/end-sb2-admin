package com.daqinzhonggong.sb2.admin.modules.quartz.domain.vo;

import java.sql.Timestamp;
import java.util.List;
import lombok.Data;

@Data
public class QuartzJobQueryCriteria {

  private String jobName;

  private Boolean isSuccess;

  private List<Timestamp> createTime;

}
