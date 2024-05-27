package com.daqinzhonggong.sb2.admin.modules.mnt.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.daqinzhonggong.sb2.admin.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("mnt_deploy")
public class Deploy extends BaseEntity implements Serializable {

  private static final long serialVersionUID = 5672443639374687664L;

  @TableId(value = "deploy_id", type = IdType.AUTO)
  @ApiModelProperty(value = "ID", hidden = true)
  private Long id;

  @ApiModelProperty(value = "应用编号")
  private Long appId;

  @TableField(exist = false)
  @ApiModelProperty(name = "服务器", hidden = true)
  private Set<Server> deploys;

  @TableField(exist = false)
  private App app;

  public void copy(Deploy source) {
    BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
  }

  public String getServers() {
    if (CollectionUtil.isNotEmpty(deploys)) {
      return deploys.stream().map(Server::getName).collect(Collectors.joining(","));
    }
    return "";
  }

}
