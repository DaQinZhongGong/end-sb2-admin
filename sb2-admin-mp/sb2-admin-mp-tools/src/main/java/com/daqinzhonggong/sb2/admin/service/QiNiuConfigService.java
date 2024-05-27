package com.daqinzhonggong.sb2.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.daqinzhonggong.sb2.admin.domain.QiniuConfig;

public interface QiNiuConfigService extends IService<QiniuConfig> {

  /**
   * 查询配置
   *
   * @return QiniuConfig
   */
  QiniuConfig getConfig();

  /**
   * 保存
   *
   * @param type 类型
   */
  void saveConfig(QiniuConfig type);

  /**
   * 更新
   *
   * @param type 类型
   */
  void updateType(String type);

}
