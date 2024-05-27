package com.daqinzhonggong.sb2.admin.modules.system.service;

import com.daqinzhonggong.sb2.admin.modules.system.domain.Job;
import com.daqinzhonggong.sb2.admin.modules.system.service.dto.JobDto;
import com.daqinzhonggong.sb2.admin.modules.system.service.dto.JobQueryCriteria;
import com.daqinzhonggong.sb2.admin.utils.PageResult;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Pageable;

public interface JobService {

  /**
   * 根据ID查询
   *
   * @param id /
   * @return /
   */
  JobDto findById(Long id);

  /**
   * 创建
   *
   * @param resources /
   * @return /
   */
  void create(Job resources);

  /**
   * 编辑
   *
   * @param resources /
   */
  void update(Job resources);

  /**
   * 删除
   *
   * @param ids /
   */
  void delete(Set<Long> ids);

  /**
   * 分页查询
   *
   * @param criteria 条件
   * @param pageable 分页参数
   * @return /
   */
  PageResult<JobDto> queryAll(JobQueryCriteria criteria, Pageable pageable);

  /**
   * 查询全部数据
   *
   * @param criteria /
   * @return /
   */
  List<JobDto> queryAll(JobQueryCriteria criteria);

  /**
   * 导出数据
   *
   * @param queryAll 待导出的数据
   * @param response /
   * @throws IOException /
   */
  void download(List<JobDto> queryAll, HttpServletResponse response) throws IOException;

  /**
   * 验证是否被用户关联
   *
   * @param ids /
   */
  void verification(Set<Long> ids);

}
