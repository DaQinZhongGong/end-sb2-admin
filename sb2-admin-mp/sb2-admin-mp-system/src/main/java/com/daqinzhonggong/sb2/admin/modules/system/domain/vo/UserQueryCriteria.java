package com.daqinzhonggong.sb2.admin.modules.system.domain.vo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Data;

@Data
public class UserQueryCriteria implements Serializable {

  private static final long serialVersionUID = 5338485508453320848L;

  private Long id;

  private Set<Long> deptIds = new HashSet<>();

  private String blurry;

  private Boolean enabled;

  private Long deptId;

  private List<Timestamp> createTime;

  private Long offset;

  private Long size;

}
