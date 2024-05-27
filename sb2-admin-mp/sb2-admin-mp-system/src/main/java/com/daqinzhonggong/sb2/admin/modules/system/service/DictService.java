package com.daqinzhonggong.sb2.admin.modules.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.daqinzhonggong.sb2.admin.modules.system.domain.Dict;
import com.daqinzhonggong.sb2.admin.modules.system.domain.vo.DictQueryCriteria;
import com.daqinzhonggong.sb2.admin.utils.PageResult;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;

public interface DictService extends IService<Dict> {

  /**
   * 分页查询
   *
   * @param criteria 条件
   * @param page     分页参数
   * @return /
   */
  PageResult<Dict> queryAll(DictQueryCriteria criteria, Page<Object> page);

  /**
   * 查询全部数据
   *
   * @param criteria /
   * @return /
   */
  List<Dict> queryAll(DictQueryCriteria criteria);

  /**
   * 创建
   *
   * @param resources /
   */
  void create(Dict resources);

  /**
   * 编辑
   *
   * @param resources /
   */
  void update(Dict resources);

  /**
   * 删除
   *
   * @param ids /
   */
  void delete(Set<Long> ids);

  /**
   * 导出数据
   *
   * @param queryAll 待导出的数据
   * @param response /
   * @throws IOException /
   */
  void download(List<Dict> queryAll, HttpServletResponse response) throws IOException;

}