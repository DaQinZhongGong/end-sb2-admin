package com.daqinzhonggong.sb2.admin.modules.system.service.dto;

import com.daqinzhonggong.sb2.admin.base.BaseDTO;
import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JobDto extends BaseDTO implements Serializable {

  private static final long serialVersionUID = 6197426237444473810L;

  private Long id;

  private Integer jobSort;

  private String name;

  private Boolean enabled;

  public JobDto(String name, Boolean enabled) {
    this.name = name;
    this.enabled = enabled;
  }

}