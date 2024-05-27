package com.daqinzhonggong.sb2.admin.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.sql.Timestamp;
import lombok.Data;

/**
 * 上传成功后，存储结果
 */
@Data
@TableName("tool_qiniu_content")
public class QiniuContent implements Serializable {

  private static final long serialVersionUID = 8225611252903177975L;

  @TableId(value = "content_id", type = IdType.AUTO)
  @ApiModelProperty(value = "ID", hidden = true)
  private Long id;

  @TableField("name")
  @ApiModelProperty(value = "文件名")
  private String key;

  @ApiModelProperty(value = "空间名")
  private String bucket;

  @ApiModelProperty(value = "大小")
  private String size;

  @ApiModelProperty(value = "文件地址")
  private String url;

  @ApiModelProperty(value = "文件类型")
  private String suffix;

  @ApiModelProperty(value = "空间类型：公开/私有")
  private String type = "公开";

  @TableField(fill = FieldFill.INSERT_UPDATE)
  @ApiModelProperty(value = "创建或更新时间")
  private Timestamp updateTime;

}
