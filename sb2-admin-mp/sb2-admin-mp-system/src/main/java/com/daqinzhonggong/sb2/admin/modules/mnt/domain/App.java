package com.daqinzhonggong.sb2.admin.modules.mnt.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.daqinzhonggong.sb2.admin.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("mnt_app")
public class App extends BaseEntity implements Serializable {

  private static final long serialVersionUID = 2597517756901792247L;

  @TableId(value = "app_id", type = IdType.AUTO)
  @ApiModelProperty(value = "ID", hidden = true)
  private Long id;

  @ApiModelProperty(value = "名称")
  private String name;

  @ApiModelProperty(value = "端口")
  private int port;

  @ApiModelProperty(value = "上传路径")
  private String uploadPath;

  @ApiModelProperty(value = "部署路径")
  private String deployPath;

  @ApiModelProperty(value = "备份路径")
  private String backupPath;

  @ApiModelProperty(value = "启动脚本")
  private String startScript;

  @ApiModelProperty(value = "部署脚本")
  private String deployScript;

  public void copy(App source) {
    BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
  }

}
