package com.daqinzhonggong.sb2.admin.modules.system.domain.vo;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MenuMetaVo implements Serializable {

  private static final long serialVersionUID = -815289210981902826L;

  private String title;

  private String icon;

  private Boolean noCache;
}
