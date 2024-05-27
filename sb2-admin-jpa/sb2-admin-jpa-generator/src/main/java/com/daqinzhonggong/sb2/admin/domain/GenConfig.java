package com.daqinzhonggong.sb2.admin.domain;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 代码生成配置
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "code_gen_config")
public class GenConfig implements Serializable {

  public GenConfig(String tableName) {
    this.tableName = tableName;
  }

  @Id
  @Column(name = "config_id")
  @ApiModelProperty(value = "ID", hidden = true)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @ApiModelProperty(value = "表名")
  private String tableName;

  @ApiModelProperty(value = "接口名称")
  private String apiAlias;

  @NotBlank
  @ApiModelProperty(value = "包路径")
  private String pack;

  @NotBlank
  @ApiModelProperty(value = "模块名")
  private String moduleName;

  @NotBlank
  @ApiModelProperty(value = "前端文件路径")
  private String path;

  @ApiModelProperty(value = "前端文件路径")
  private String apiPath;

  @ApiModelProperty(value = "作者")
  private String author;

  @ApiModelProperty(value = "表前缀")
  private String prefix;

  @ApiModelProperty(value = "是否覆盖")
  private Boolean cover = false;
}
