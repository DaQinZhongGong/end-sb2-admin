package com.daqinzhonggong.sb2.admin.annotation;

import com.daqinzhonggong.sb2.admin.aspect.LimitType;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>自定义限流注解</p>
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Limit {

  /**
   * 资源名称，用于描述接口功能
   */
  String name() default "";

  /**
   * 资源 key
   */
  String key() default "";

  /**
   * key 前缀
   */
  String prefix() default "";

  /**
   * 时间的，单位秒
   */
  int period();

  /**
   * 限制访问次数
   */
  int count();

  /**
   * 限制类型（默认）
   */
  LimitType limitType() default LimitType.CUSTOMER;

}
