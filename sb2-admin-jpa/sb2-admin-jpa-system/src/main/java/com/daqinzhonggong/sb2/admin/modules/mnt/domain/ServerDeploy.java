package com.daqinzhonggong.sb2.admin.modules.mnt.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.daqinzhonggong.sb2.admin.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Objects;
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
@Table(name = "mnt_server")
public class ServerDeploy extends BaseEntity implements Serializable {

  private static final long serialVersionUID = 4903460771877862234L;

  @Id
  @Column(name = "server_id")
  @ApiModelProperty(value = "ID", hidden = true)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ApiModelProperty(value = "服务器名称")
  private String name;

  @ApiModelProperty(value = "IP")
  private String ip;

  @ApiModelProperty(value = "端口")
  private Integer port;

  @ApiModelProperty(value = "账号")
  private String account;

  @ApiModelProperty(value = "密码")
  private String password;

  public void copy(ServerDeploy source) {
    BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ServerDeploy that = (ServerDeploy) o;
    return Objects.equals(id, that.id) &&
        Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name);
  }
}
