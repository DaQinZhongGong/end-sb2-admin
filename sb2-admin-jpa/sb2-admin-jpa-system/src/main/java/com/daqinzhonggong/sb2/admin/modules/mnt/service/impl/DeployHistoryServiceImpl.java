package com.daqinzhonggong.sb2.admin.modules.mnt.service.impl;

import cn.hutool.core.util.IdUtil;
import com.daqinzhonggong.sb2.admin.modules.mnt.domain.DeployHistory;
import com.daqinzhonggong.sb2.admin.modules.mnt.repository.DeployHistoryRepository;
import com.daqinzhonggong.sb2.admin.modules.mnt.service.DeployHistoryService;
import com.daqinzhonggong.sb2.admin.modules.mnt.service.dto.DeployHistoryDto;
import com.daqinzhonggong.sb2.admin.modules.mnt.service.dto.DeployHistoryQueryCriteria;
import com.daqinzhonggong.sb2.admin.modules.mnt.service.mapstruct.DeployHistoryMapper;
import com.daqinzhonggong.sb2.admin.utils.FileUtil;
import com.daqinzhonggong.sb2.admin.utils.PageResult;
import com.daqinzhonggong.sb2.admin.utils.PageUtil;
import com.daqinzhonggong.sb2.admin.utils.QueryHelp;
import com.daqinzhonggong.sb2.admin.utils.ValidationUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeployHistoryServiceImpl implements DeployHistoryService {

  private final DeployHistoryRepository deployhistoryRepository;
  private final DeployHistoryMapper deployhistoryMapper;

  @Override
  public PageResult<DeployHistoryDto> queryAll(DeployHistoryQueryCriteria criteria,
      Pageable pageable) {
    Page<DeployHistory> page = deployhistoryRepository.findAll(
        (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria,
            criteriaBuilder), pageable);
    return PageUtil.toPage(page.map(deployhistoryMapper::toDto));
  }

  @Override
  public List<DeployHistoryDto> queryAll(DeployHistoryQueryCriteria criteria) {
    return deployhistoryMapper.toDto(deployhistoryRepository.findAll(
        (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria,
            criteriaBuilder)));
  }

  @Override
  public DeployHistoryDto findById(String id) {
    DeployHistory deployhistory = deployhistoryRepository.findById(id)
        .orElseGet(DeployHistory::new);
    ValidationUtil.isNull(deployhistory.getId(), "DeployHistory", "id", id);
    return deployhistoryMapper.toDto(deployhistory);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void create(DeployHistory resources) {
    resources.setId(IdUtil.simpleUUID());
    deployhistoryRepository.save(resources);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void delete(Set<String> ids) {
    for (String id : ids) {
      deployhistoryRepository.deleteById(id);
    }
  }

  @Override
  public void download(List<DeployHistoryDto> queryAll, HttpServletResponse response)
      throws IOException {
    List<Map<String, Object>> list = new ArrayList<>();
    for (DeployHistoryDto deployHistoryDto : queryAll) {
      Map<String, Object> map = new LinkedHashMap<>();
      map.put("部署编号", deployHistoryDto.getDeployId());
      map.put("应用名称", deployHistoryDto.getAppName());
      map.put("部署IP", deployHistoryDto.getIp());
      map.put("部署时间", deployHistoryDto.getDeployDate());
      map.put("部署人员", deployHistoryDto.getDeployUser());
      list.add(map);
    }
    FileUtil.downloadExcel(list, response);
  }

}
