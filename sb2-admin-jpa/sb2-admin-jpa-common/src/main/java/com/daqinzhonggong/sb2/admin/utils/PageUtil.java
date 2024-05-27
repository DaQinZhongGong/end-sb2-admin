package com.daqinzhonggong.sb2.admin.utils;

import java.util.Collections;
import java.util.List;
import org.springframework.data.domain.Page;

/**
 * 分页工具
 */
public class PageUtil extends cn.hutool.core.util.PageUtil {

  /**
   * List 分页
   */
  public static <T> List<T> paging(int page, int size, List<T> list) {
    int fromIndex = page * size;
    int toIndex = page * size + size;
    if (fromIndex > list.size()) {
      return Collections.emptyList();
    } else if (toIndex >= list.size()) {
      return list.subList(fromIndex, list.size());
    } else {
      return list.subList(fromIndex, toIndex);
    }
  }

  /**
   * Page 数据处理，预防redis反序列化报错
   */
  public static <T> PageResult<T> toPage(Page<T> page) {
    return new PageResult<>(page.getContent(), page.getTotalElements());
  }

  /**
   * 自定义分页
   */
  public static <T> PageResult<T> toPage(List<T> list, long totalElements) {
    return new PageResult<>(list, totalElements);
  }

  /**
   * 返回空数据
   */
  public static <T> PageResult<T> noData() {
    return new PageResult<>(null, 0);
  }
}
