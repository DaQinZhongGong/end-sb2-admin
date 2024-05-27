package com.daqinzhonggong.sb2.admin.service.dto;

import com.daqinzhonggong.sb2.admin.base.BaseDTO;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocalStorageDto extends BaseDTO implements Serializable {

  private static final long serialVersionUID = 655346608072584586L;

  private Long id;

  private String realName;

  private String name;

  private String suffix;

  private String type;

  private String size;

}