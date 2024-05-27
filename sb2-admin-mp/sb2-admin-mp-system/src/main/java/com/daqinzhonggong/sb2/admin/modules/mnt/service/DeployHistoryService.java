package com.daqinzhonggong.sb2.admin.modules.mnt.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.daqinzhonggong.sb2.admin.modules.mnt.domain.DeployHistory;
import com.daqinzhonggong.sb2.admin.modules.mnt.domain.vo.DeployHistoryQueryCriteria;
import com.daqinzhonggong.sb2.admin.utils.PageResult;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;

public interface DeployHistoryService extends IService<DeployHistory> {

  /**
   * 分页查询
   *
   * @param criteria 条件
   * @param page     分页参数
   * @return /
   */
  PageResult<DeployHistory> queryAll(DeployHistoryQueryCriteria criteria, Page<Object> page);

  /**
   * 查询全部
   *
   * @param criteria 条件
   * @return /
   */
  List<DeployHistory> queryAll(DeployHistoryQueryCriteria criteria);

  /**
   * 创建
   *
   * @param resources /
   */
  void create(DeployHistory resources);

  /**
   * 删除
   *
   * @param ids /
   */
  void delete(Set<String> ids);

  /**
   * 导出数据
   *
   * @param queryAll /
   * @param response /
   * @throws IOException /
   */
  void download(List<DeployHistory> queryAll, HttpServletResponse response) throws IOException;

}
