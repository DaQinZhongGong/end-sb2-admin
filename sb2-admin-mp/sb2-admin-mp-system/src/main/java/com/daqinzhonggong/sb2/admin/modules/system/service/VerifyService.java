package com.daqinzhonggong.sb2.admin.modules.system.service;

import com.daqinzhonggong.sb2.admin.domain.vo.EmailVo;

public interface VerifyService {

  /**
   * 发送验证码
   *
   * @param email /
   * @param key   /
   * @return /
   */
  EmailVo sendEmail(String email, String key);


  /**
   * 验证
   *
   * @param code /
   * @param key  /
   */
  void validated(String key, String code);

}
