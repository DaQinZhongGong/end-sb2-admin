package com.daqinzhonggong.sb2.admin.modules.system.domain;

import com.daqinzhonggong.sb2.admin.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "sys_dict_detail")
public class DictDetail extends BaseEntity implements Serializable {

  private static final long serialVersionUID = 3593850732820254940L;

  @Id
  @Column(name = "detail_id")
  @NotNull(groups = Update.class)
  @ApiModelProperty(value = "ID", hidden = true)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JoinColumn(name = "dict_id")
  @ManyToOne(fetch = FetchType.LAZY)
  @ApiModelProperty(value = "字典", hidden = true)
  private Dict dict;

  @ApiModelProperty(value = "字典标签")
  private String label;

  @ApiModelProperty(value = "字典值")
  private String value;

  @ApiModelProperty(value = "排序")
  private Integer dictSort = 999;

}