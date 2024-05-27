package com.daqinzhonggong.sb2.admin.modules.system.service;

import com.daqinzhonggong.sb2.admin.modules.system.domain.DictDetail;
import com.daqinzhonggong.sb2.admin.modules.system.service.dto.DictDetailDto;
import com.daqinzhonggong.sb2.admin.modules.system.service.dto.DictDetailQueryCriteria;
import com.daqinzhonggong.sb2.admin.utils.PageResult;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface DictDetailService {

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
   * @param pageable 分页参数
   * @return /
   */
  PageResult<DictDetailDto> queryAll(DictDetailQueryCriteria criteria, Pageable pageable);

  /**
   * 根据字典名称获取字典详情
   *
   * @param name 字典名称
   * @return /
   */
  List<DictDetailDto> getDictByName(String name);

}
