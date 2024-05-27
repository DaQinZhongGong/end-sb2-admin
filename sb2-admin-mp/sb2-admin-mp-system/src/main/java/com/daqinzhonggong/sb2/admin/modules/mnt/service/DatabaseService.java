package com.daqinzhonggong.sb2.admin.modules.mnt.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.daqinzhonggong.sb2.admin.modules.mnt.domain.Database;
import com.daqinzhonggong.sb2.admin.modules.mnt.domain.vo.DatabaseQueryCriteria;
import com.daqinzhonggong.sb2.admin.utils.PageResult;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;

public interface DatabaseService extends IService<Database> {

  /**
   * 分页查询
   *
   * @param criteria 条件
   * @param page     分页参数
   * @return /
   */
  PageResult<Database> queryAll(DatabaseQueryCriteria criteria, Page<Object> page);

  /**
   * 查询全部
   *
   * @param criteria 条件
   * @return /
   */
  List<Database> queryAll(DatabaseQueryCriteria criteria);

  /**
   * 创建
   *
   * @param resources /
   */
  void create(Database resources);

  /**
   * 编辑
   *
   * @param resources /
   */
  void update(Database resources);

  /**
   * 删除
   *
   * @param ids /
   */
  void delete(Set<String> ids);

  /**
   * 测试连接数据库
   *
   * @param resources /
   * @return /
   */
  boolean testConnection(Database resources);

  /**
   * 导出数据
   *
   * @param queryAll /
   * @param response /
   * @throws IOException e
   */
  void download(List<Database> queryAll, HttpServletResponse response) throws IOException;

}
