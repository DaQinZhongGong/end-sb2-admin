package com.daqinzhonggong.sb2.admin.utils;

import static com.daqinzhonggong.sb2.admin.utils.EncryptUtils.desDecrypt;
import static com.daqinzhonggong.sb2.admin.utils.EncryptUtils.desEncrypt;
import static org.junit.jupiter.api.Assertions.assertEquals;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class EncryptUtilsTest {

  /**
   * 对称加密
   */
  @Test
  public void testDesEncrypt() {
    try {
      assertEquals("7772841DC6099402", desEncrypt("5t6FrY3LmDK584jqqyV8RBQXrcW7JPPP"));
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
  }

  /**
   * 对称解密
   */
  @Test
  public void testDesDecrypt() {
    try {
      assertEquals("5t6FrY3LmDK584jqqyV8RBQXrcW7JPPP", desDecrypt("7772841DC6099402"));
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
  }

}
