package com.daqinzhonggong.sb2.admin.modules.mnt.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.daqinzhonggong.sb2.admin.modules.mnt.domain.Server;
import com.daqinzhonggong.sb2.admin.modules.mnt.domain.vo.ServerQueryCriteria;
import com.daqinzhonggong.sb2.admin.utils.PageResult;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;

public interface ServerService extends IService<Server> {

  /**
   * 分页查询
   *
   * @param criteria 条件
   * @param page     分页参数
   * @return /
   */
  PageResult<Server> queryAll(ServerQueryCriteria criteria, Page<Object> page);

  /**
   * 查询全部数据
   *
   * @param criteria 条件
   * @return /
   */
  List<Server> queryAll(ServerQueryCriteria criteria);

  /**
   * 创建
   *
   * @param resources /
   */
  void create(Server resources);

  /**
   * 编辑
   *
   * @param resources /
   */
  void update(Server resources);

  /**
   * 删除
   *
   * @param ids /
   */
  void delete(Set<Long> ids);

  /**
   * 根据IP查询
   *
   * @param ip /
   * @return /
   */
  Server findByIp(String ip);

  /**
   * 测试登录服务器
   *
   * @param resources /
   * @return /
   */
  Boolean testConnect(Server resources);

  /**
   * 导出数据
   *
   * @param queryAll /
   * @param response /
   * @throws IOException /
   */
  void download(List<Server> queryAll, HttpServletResponse response) throws IOException;

}
