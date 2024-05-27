package com.daqinzhonggong.sb2.admin.modules.system.service.dto;

import com.daqinzhonggong.sb2.admin.base.BaseDTO;
import java.io.Serializable;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DictDto extends BaseDTO implements Serializable {

  private static final long serialVersionUID = -6913225997780854160L;

  private Long id;

  private List<DictDetailDto> dictDetails;

  private String name;

  private String description;
}
