package com.daqinzhonggong.sb2.admin.modules.system.service.dto;

import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JobSmallDto implements Serializable {

  private static final long serialVersionUID = -9049139260559162781L;

  private Long id;

  private String name;
}