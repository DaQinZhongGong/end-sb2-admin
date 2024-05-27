package com.daqinzhonggong.sb2.admin.modules.system.service.dto;

import java.io.Serializable;
import lombok.Data;

@Data
public class RoleSmallDto implements Serializable {

  private static final long serialVersionUID = 6023043969499519122L;

  private Long id;

  private String name;

  private Integer level;

  private String dataScope;

}
