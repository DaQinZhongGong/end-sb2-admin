package com.daqinzhonggong.sb2.admin.modules.system.service.dto;

import com.daqinzhonggong.sb2.admin.annotation.Query;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Data;

@Data
public class UserQueryCriteria implements Serializable {

  private static final long serialVersionUID = -2905596651760402770L;

  @Query
  private Long id;

  @Query(propName = "id", type = Query.Type.IN, joinName = "dept")
  private Set<Long> deptIds = new HashSet<>();

  @Query(blurry = "email,username,nickName")
  private String blurry;

  @Query
  private Boolean enabled;

  private Long deptId;

  @Query(type = Query.Type.BETWEEN)
  private List<Timestamp> createTime;

}
