package com.daqinzhonggong.sb2.admin.modules.mnt.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.daqinzhonggong.sb2.admin.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "mnt_app")
public class App extends BaseEntity implements Serializable {

  private static final long serialVersionUID = 2597517756901792247L;

  @Id
  @Column(name = "app_id")
  @ApiModelProperty(value = "ID", hidden = true)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
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
