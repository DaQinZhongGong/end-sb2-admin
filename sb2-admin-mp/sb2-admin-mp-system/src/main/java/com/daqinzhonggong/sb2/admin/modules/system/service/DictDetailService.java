package com.daqinzhonggong.sb2.admin.modules.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.daqinzhonggong.sb2.admin.modules.system.domain.DictDetail;
import com.daqinzhonggong.sb2.admin.modules.system.domain.vo.DictDetailQueryCriteria;
import com.daqinzhonggong.sb2.admin.utils.PageResult;
import java.util.List;

public interface DictDetailService extends IService<DictDetail> {

  /**
   * 创建
   *
   * @param resources /
   */
  void create(DictDetail resources);

  /**
   * 编辑
   *
   * @param resources /
   */
  void update(DictDetail resources);

  /**
   * 删除
   *
   * @param id /
   */
  void delete(Long id);

  /**
   * 分页查询
   *
   * @param criteria 条件
   * @param page     分页参数
   * @return /
   */
  PageResult<DictDetail> queryAll(DictDetailQueryCriteria criteria, Page<Object> page);

  /**
   * 根据字典名称获取字典详情
   *
   * @param name 字典名称
   * @return /
   */
  List<DictDetail> getDictByName(String name);

}