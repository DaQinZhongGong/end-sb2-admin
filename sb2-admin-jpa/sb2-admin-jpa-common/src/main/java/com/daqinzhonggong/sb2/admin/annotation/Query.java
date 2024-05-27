package com.daqinzhonggong.sb2.admin.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>自定义查询注解</p>
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Query {

  /**
   * 基本对象的属性名
   */
  String propName() default "";

  /**
   * 查询方式
   */
  Type type() default Type.EQUAL;

  /**
   * 连接查询的属性名，如User类中的dept
   */
  String joinName() default "";

  /**
   * 默认左连接
   */
  Join join() default Join.LEFT;

  /**
   * 多字段模糊搜索，仅支持String类型字段，多个用逗号隔开, 如@Query(blurry = "email,username")
   */
  String blurry() default "";

  enum Type {
    /**
     * 相等
     */
    EQUAL
    /**
     * 大于等于
     */
    , GREATER_THAN
    /**
     * 小于等于
     */
    , LESS_THAN
    /**
     * 中模糊查询
     */
    , INNER_LIKE
    /**
     * 左模糊查询
     */
    , LEFT_LIKE
    /**
     * 右模糊查询
     */
    , RIGHT_LIKE
    /**
     * 小于
     */
    , LESS_THAN_NQ
    /**
     * 包含
     */
    , IN
    /**
     * 不包含
     */
    , NOT_IN
    /**
     * 不等于
     */
    , NOT_EQUAL
    /**
     * between
     */
    , BETWEEN
    /**
     * 不为空
     */
    , NOT_NULL
    /**
     * 为空
     */
    , IS_NULL,
    /**
     * 对应示例SQL: SELECT * FROM table WHERE FIND_IN_SET('querytag', table.tags);
     * FIND_IN_SET()是一个MySQL特有的字符串函数，它用于在逗号分隔的字符串列表中查找一个指定的值，并返回该值的位置索引（从1开始计数）。
     * 如果找不到，则返回0。在这里，它检查table表中tags列的值里是否包含'querytag'这个项。 注意，tags列的值应该是由逗号分隔的字符串列表，比如
     * 'tag1,tag2,tag3'。
     */
    FIND_IN_SET
  }

  /**
   * 适用于简单连接查询，复杂的请自定义该注解，或者使用sql查询
   */
  enum Join {
    LEFT, RIGHT, INNER
  }

}

