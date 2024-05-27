package com.daqinzhonggong.sb2.admin.modules.mnt.service;

import com.daqinzhonggong.sb2.admin.modules.mnt.domain.App;
import com.daqinzhonggong.sb2.admin.modules.mnt.service.dto.AppDto;
import com.daqinzhonggong.sb2.admin.modules.mnt.service.dto.AppQueryCriteria;
import com.daqinzhonggong.sb2.admin.utils.PageResult;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Pageable;

public interface AppService {

  /**
   * 分页查询
   *
   * @param criteria 条件
   * @param pageable 分页参数
   * @return /
   */
  PageResult<AppDto> queryAll(AppQueryCriteria criteria, Pageable pageable);

  /**
   * 查询全部数据
   *
   * @param criteria 条件
   * @return /
   */
  List<AppDto> queryAll(AppQueryCriteria criteria);

  /**
   * 根据ID查询
   *
   * @param id /
   * @return /
   */
  AppDto findById(Long id);

  /**
   * 创建
   *
   * @param resources /
   */
  void create(App resources);

  /**
   * 编辑
   *
   * @param resources /
   */
  void update(App resources);

  /**
   * 删除
   *
   * @param ids /
   */
  void delete(Set<Long> ids);

  /**
   * 导出数据
   *
   * @param queryAll /
   * @param response /
   * @throws IOException /
   */
  void download(List<AppDto> queryAll, HttpServletResponse response) throws IOException;
}
