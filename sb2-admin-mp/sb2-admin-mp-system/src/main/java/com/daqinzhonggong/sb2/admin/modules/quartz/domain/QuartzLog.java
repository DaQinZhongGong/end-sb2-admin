package com.daqinzhonggong.sb2.admin.modules.quartz.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.sql.Timestamp;
import lombok.Data;

@Data
@TableName("sys_quartz_log")
public class QuartzLog implements Serializable {

  @TableId(value = "log_id", type = IdType.AUTO)
  @ApiModelProperty(value = "ID", hidden = true)
  private Long id;

  @ApiModelProperty(value = "任务名称", hidden = true)
  private String jobName;

  @ApiModelProperty(value = "bean名称", hidden = true)
  private String beanName;

  @ApiModelProperty(value = "方法名称", hidden = true)
  private String methodName;

  @ApiModelProperty(value = "参数", hidden = true)
  private String params;

  @ApiModelProperty(value = "cron表达式", hidden = true)
  private String cronExpression;

  @ApiModelProperty(value = "状态", hidden = true)
  private Boolean isSuccess;

  @ApiModelProperty(value = "异常详情", hidden = true)
  private String exceptionDetail;

  @ApiModelProperty(value = "执行耗时", hidden = true)
  private Long time;

  @TableField(fill = FieldFill.INSERT)
  @ApiModelProperty(value = "创建时间", hidden = true)
  private Timestamp createTime;

}
