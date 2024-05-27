package com.daqinzhonggong.sb2.admin.modules.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.daqinzhonggong.sb2.admin.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("sys_dict_detail")
public class DictDetail extends BaseEntity implements Serializable {

  private static final long serialVersionUID = 8074653058366982454L;

  @NotNull(groups = Update.class)
  @ApiModelProperty(value = "ID", hidden = true)
  @TableId(value = "detail_id", type = IdType.AUTO)
  private Long id;

  @TableField(value = "dict_id")
  @ApiModelProperty(hidden = true)
  private Long dictId;

  @TableField(exist = false)
  private Dict dict;

  @ApiModelProperty(value = "字典标签")
  private String label;

  @ApiModelProperty(value = "字典值")
  private String value;

  @ApiModelProperty(value = "排序")
  private Integer dictSort = 999;

}