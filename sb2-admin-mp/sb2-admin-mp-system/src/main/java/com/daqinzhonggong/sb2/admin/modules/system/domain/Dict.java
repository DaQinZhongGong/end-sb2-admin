package com.daqinzhonggong.sb2.admin.modules.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.daqinzhonggong.sb2.admin.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("sys_dict")
public class Dict extends BaseEntity implements Serializable {

  private static final long serialVersionUID = -1435821178071685982L;

  @NotNull(groups = Update.class)
  @ApiModelProperty(value = "ID", hidden = true)
  @TableId(value = "dict_id", type = IdType.AUTO)
  private Long id;

  @TableField(exist = false)
  private List<DictDetail> dictDetails;

  @NotBlank
  @ApiModelProperty(value = "名称")
  private String name;

  @ApiModelProperty(value = "描述")
  private String description;

}