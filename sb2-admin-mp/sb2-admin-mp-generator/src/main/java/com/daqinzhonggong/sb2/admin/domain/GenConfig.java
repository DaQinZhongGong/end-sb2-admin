package com.daqinzhonggong.sb2.admin.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 代码生成配置
 */
@Getter
@Setter
@NoArgsConstructor
@TableName("code_gen_config")
public class GenConfig implements Serializable {

  private static final long serialVersionUID = 80955528626949528L;

  public GenConfig(String tableName) {
    this.tableName = tableName;
  }

  @ApiModelProperty(value = "ID", hidden = true)
  @TableId(value = "config_id", type = IdType.AUTO)
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
