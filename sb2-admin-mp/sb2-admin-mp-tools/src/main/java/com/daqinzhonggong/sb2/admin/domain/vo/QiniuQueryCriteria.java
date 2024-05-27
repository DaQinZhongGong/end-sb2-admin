package com.daqinzhonggong.sb2.admin.domain.vo;

import java.sql.Timestamp;
import java.util.List;
import lombok.Data;

@Data
public class QiniuQueryCriteria {

  private String key;

  private List<Timestamp> createTime;

}
