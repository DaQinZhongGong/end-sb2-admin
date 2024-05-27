package com.daqinzhonggong.sb2.admin.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 列的数据信息
 */
@Getter
@Setter
@NoArgsConstructor
@TableName("code_column_config")
public class ColumnInfo implements Serializable {

  private static final long serialVersionUID = -7386626980172276557L;

  @ApiModelProperty(value = "ID", hidden = true)
  @TableId(value = "column_id", type = IdType.AUTO)
  private Long id;

  @ApiModelProperty(value = "表名")
  private String tableName;

  @ApiModelProperty(value = "数据库字段名称")
  private String columnName;

  @ApiModelProperty(value = "数据库字段类型")
  private String columnType;

  @ApiModelProperty(value = "数据库字段键类型")
  private String keyType;

  @ApiModelProperty(value = "字段额外的参数")
  private String extra;

  @ApiModelProperty(value = "数据库字段描述")
  private String remark;

  @ApiModelProperty(value = "是否必填")
  private Boolean notNull;

  @ApiModelProperty(value = "是否在列表显示")
  private Boolean listShow = true;

  @ApiModelProperty(value = "是否表单显示")
  private Boolean formShow = true;

  @ApiModelProperty(value = "表单类型")
  private String formType;

  @ApiModelProperty(value = "查询 1:模糊 2：精确")
  private String queryType;

  @ApiModelProperty(value = "字典名称")
  private String dictName;
}
