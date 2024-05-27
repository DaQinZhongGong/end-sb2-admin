package com.daqinzhonggong.sb2.admin.modules.mnt.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("mnt_deploy_history")
public class DeployHistory implements Serializable {

  private static final long serialVersionUID = -5363653386079198733L;

  @TableId(value = "history_id", type = IdType.AUTO)
  @ApiModelProperty(value = "ID", hidden = true)
  private String id;

  @ApiModelProperty(value = "应用名称")
  private String appName;

  @ApiModelProperty(value = "IP")
  private String ip;

  @ApiModelProperty(value = "部署时间")
  private Timestamp deployDate;

  @ApiModelProperty(value = "部署者")
  private String deployUser;

  @ApiModelProperty(value = "部署ID")
  private Long deployId;

  public void copy(DeployHistory source) {
    BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
  }

}
