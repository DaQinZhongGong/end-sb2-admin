package com.daqinzhonggong.sb2.admin.domain;

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
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tool_local_storage")
@NoArgsConstructor
public class LocalStorage extends BaseEntity implements Serializable {

  @Id
  @Column(name = "storage_id")
  @ApiModelProperty(value = "ID", hidden = true)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ApiModelProperty(value = "真实文件名")
  private String realName;

  @ApiModelProperty(value = "文件名")
  private String name;

  @ApiModelProperty(value = "后缀")
  private String suffix;

  @ApiModelProperty(value = "路径")
  private String path;

  @ApiModelProperty(value = "类型")
  private String type;

  @ApiModelProperty(value = "大小")
  private String size;

  public LocalStorage(String realName, String name, String suffix, String path, String type,
      String size) {
    this.realName = realName;
    this.name = name;
    this.suffix = suffix;
    this.path = path;
    this.type = type;
    this.size = size;
  }

  public void copy(LocalStorage source) {
    BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
  }

}