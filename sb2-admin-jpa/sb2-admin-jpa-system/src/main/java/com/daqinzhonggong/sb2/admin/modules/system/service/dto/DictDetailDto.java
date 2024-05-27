package com.daqinzhonggong.sb2.admin.modules.system.service.dto;

import com.daqinzhonggong.sb2.admin.base.BaseDTO;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DictDetailDto extends BaseDTO implements Serializable {

  private static final long serialVersionUID = 6010326236772667677L;

  private Long id;

  private DictSmallDto dict;

  private String label;

  private String value;

  private Integer dictSort;
}