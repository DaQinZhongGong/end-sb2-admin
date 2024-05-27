package com.daqinzhonggong.sb2.admin.modules.mnt.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.daqinzhonggong.sb2.admin.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("mnt_server")
public class Server extends BaseEntity implements Serializable {

  private static final long serialVersionUID = -3074035426977043666L;

  @TableId(value = "server_id", type = IdType.AUTO)
  @ApiModelProperty(value = "ID", hidden = true)
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

  public void copy(Server source) {
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
    Server that = (Server) o;
    return Objects.equals(id, that.id) &&
        Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name);
  }

}
