package com.daqinzhonggong.sb2.admin.modules.mnt.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.daqinzhonggong.sb2.admin.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "mnt_database")
public class Database extends BaseEntity implements Serializable {

  private static final long serialVersionUID = -4696157701308317511L;

  @Id
  @Column(name = "db_id")
  @ApiModelProperty(value = "ID", hidden = true)
  private String id;

  @ApiModelProperty(value = "数据库名称")
  private String name;

  @ApiModelProperty(value = "数据库连接地址")
  private String jdbcUrl;

  @ApiModelProperty(value = "数据库密码")
  private String pwd;

  @ApiModelProperty(value = "用户名")
  private String userName;

  public void copy(Database source) {
    BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
  }

}
