package com.daqinzhonggong.sb2.admin.modules.system.domain.vo;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * 构建前端路由时用到
 */
@Data
public class MenuVo implements Serializable {

  private static final long serialVersionUID = -7424532450682797340L;

  private String name;

  private String path;

  private Boolean hidden;

  private String redirect;

  private String component;

  private Boolean alwaysShow;

  private MenuMetaVo meta;

  private List<MenuVo> children;

}
