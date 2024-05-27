package com.daqinzhonggong.sb2.admin.modules.mnt.service;

import com.daqinzhonggong.sb2.admin.modules.mnt.domain.Database;
import com.daqinzhonggong.sb2.admin.modules.mnt.service.dto.DatabaseDto;
import com.daqinzhonggong.sb2.admin.modules.mnt.service.dto.DatabaseQueryCriteria;
import com.daqinzhonggong.sb2.admin.utils.PageResult;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Pageable;

public interface DatabaseService {

  /**
   * 分页查询
   *
   * @param criteria 条件
   * @param pageable 分页参数
   * @return /
   */
  PageResult<DatabaseDto> queryAll(DatabaseQueryCriteria criteria, Pageable pageable);

  /**
   * 查询全部
   *
   * @param criteria 条件
   * @return /
   */
  List<DatabaseDto> queryAll(DatabaseQueryCriteria criteria);

  /**
   * 根据ID查询
   *
   * @param id /
   * @return /
   */
  DatabaseDto findById(String id);

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
  void download(List<DatabaseDto> queryAll, HttpServletResponse response) throws IOException;
}
