package com.daqinzhonggong.sb2.admin.utils;

import java.io.Closeable;

/**
 * 用于关闭各种连接
 **/
public class CloseUtil {

  public static void close(Closeable closeable) {
    if (null != closeable) {
      try {
        closeable.close();
      } catch (Exception e) {
        // 静默关闭
      }
    }
  }

  public static void close(AutoCloseable closeable) {
    if (null != closeable) {
      try {
        closeable.close();
      } catch (Exception e) {
        // 静默关闭
      }
    }
  }
}
