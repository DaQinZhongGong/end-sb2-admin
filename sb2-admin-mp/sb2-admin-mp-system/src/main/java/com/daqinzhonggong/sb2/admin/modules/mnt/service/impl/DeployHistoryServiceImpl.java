package com.daqinzhonggong.sb2.admin.modules.mnt.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.daqinzhonggong.sb2.admin.modules.mnt.domain.DeployHistory;
import com.daqinzhonggong.sb2.admin.modules.mnt.domain.vo.DeployHistoryQueryCriteria;
import com.daqinzhonggong.sb2.admin.modules.mnt.mapper.DeployHistoryMapper;
import com.daqinzhonggong.sb2.admin.modules.mnt.service.DeployHistoryService;
import com.daqinzhonggong.sb2.admin.utils.DateUtil;
import com.daqinzhonggong.sb2.admin.utils.FileUtil;
import com.daqinzhonggong.sb2.admin.utils.PageResult;
import com.daqinzhonggong.sb2.admin.utils.PageUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeployHistoryServiceImpl extends
    ServiceImpl<DeployHistoryMapper, DeployHistory> implements
    DeployHistoryService {

  private final DeployHistoryMapper deployhistoryMapper;

  @Override
  public PageResult<DeployHistory> queryAll(DeployHistoryQueryCriteria criteria,
      Page<Object> page) {
    return PageUtil.toPage(deployhistoryMapper.findAll(criteria, page));
  }

  @Override
  public List<DeployHistory> queryAll(DeployHistoryQueryCriteria criteria) {
    return deployhistoryMapper.findAll(criteria);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void create(DeployHistory resources) {
    resources.setId(IdUtil.simpleUUID());
    resources.setDeployDate(DateUtil.getTimeStamp());
    save(resources);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void delete(Set<String> ids) {
    removeBatchByIds(ids);
  }

  @Override
  public void download(List<DeployHistory> deployHistories, HttpServletResponse response)
      throws IOException {
    List<Map<String, Object>> list = new ArrayList<>();
    for (DeployHistory deployHistory : deployHistories) {
      Map<String, Object> map = new LinkedHashMap<>();
      map.put("部署编号", deployHistory.getDeployId());
      map.put("应用名称", deployHistory.getAppName());
      map.put("部署IP", deployHistory.getIp());
      map.put("部署时间", deployHistory.getDeployDate());
      map.put("部署人员", deployHistory.getDeployUser());
      list.add(map);
    }
    FileUtil.downloadExcel(list, response);
  }

}
