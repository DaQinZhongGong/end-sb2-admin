package com.daqinzhonggong.sb2.admin.modules.system.service;

import java.util.Map;

public interface MonitorService {

  /**
   * 查询数据分页
   *
   * @return Map<String, Object>
   */
  Map<String, Object> getServers();

}
