package com.daqinzhonggong.sb2.admin.modules.mnt.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.daqinzhonggong.sb2.admin.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "mnt_deploy")
public class Deploy extends BaseEntity implements Serializable {

  private static final long serialVersionUID = -6233775638028698869L;

  @Id
  @Column(name = "deploy_id")
  @ApiModelProperty(value = "ID", hidden = true)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToMany
  @ApiModelProperty(name = "服务器", hidden = true)
  @JoinTable(name = "mnt_deploy_server",
      joinColumns = {@JoinColumn(name = "deploy_id", referencedColumnName = "deploy_id")},
      inverseJoinColumns = {@JoinColumn(name = "server_id", referencedColumnName = "server_id")})
  private Set<ServerDeploy> deploys;

  @ManyToOne
  @JoinColumn(name = "app_id")
  @ApiModelProperty(value = "应用编号")
  private App app;

  public void copy(Deploy source) {
    BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
  }

}
