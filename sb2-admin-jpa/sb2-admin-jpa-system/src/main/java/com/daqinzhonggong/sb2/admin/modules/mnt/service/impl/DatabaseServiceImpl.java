package com.daqinzhonggong.sb2.admin.modules.mnt.service.impl;

import cn.hutool.core.util.IdUtil;
import com.daqinzhonggong.sb2.admin.modules.mnt.domain.Database;
import com.daqinzhonggong.sb2.admin.modules.mnt.repository.DatabaseRepository;
import com.daqinzhonggong.sb2.admin.modules.mnt.service.DatabaseService;
import com.daqinzhonggong.sb2.admin.modules.mnt.service.dto.DatabaseDto;
import com.daqinzhonggong.sb2.admin.modules.mnt.service.dto.DatabaseQueryCriteria;
import com.daqinzhonggong.sb2.admin.modules.mnt.service.mapstruct.DatabaseMapper;
import com.daqinzhonggong.sb2.admin.modules.mnt.util.SqlUtils;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DatabaseServiceImpl implements DatabaseService {

  private final DatabaseRepository databaseRepository;
  private final DatabaseMapper databaseMapper;

  @Override
  public PageResult<DatabaseDto> queryAll(DatabaseQueryCriteria criteria, Pageable pageable) {
    Page<Database> page = databaseRepository.findAll(
        (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria,
            criteriaBuilder), pageable);
    return PageUtil.toPage(page.map(databaseMapper::toDto));
  }

  @Override
  public List<DatabaseDto> queryAll(DatabaseQueryCriteria criteria) {
    return databaseMapper.toDto(databaseRepository.findAll(
        (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria,
            criteriaBuilder)));
  }

  @Override
  public DatabaseDto findById(String id) {
    Database database = databaseRepository.findById(id).orElseGet(Database::new);
    ValidationUtil.isNull(database.getId(), "Database", "id", id);
    return databaseMapper.toDto(database);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void create(Database resources) {
    resources.setId(IdUtil.simpleUUID());
    databaseRepository.save(resources);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void update(Database resources) {
    Database database = databaseRepository.findById(resources.getId()).orElseGet(Database::new);
    ValidationUtil.isNull(database.getId(), "Database", "id", resources.getId());
    database.copy(resources);
    databaseRepository.save(database);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void delete(Set<String> ids) {
    for (String id : ids) {
      databaseRepository.deleteById(id);
    }
  }

  @Override
  public boolean testConnection(Database resources) {
    try {
      return SqlUtils.testConnection(resources.getJdbcUrl(), resources.getUserName(),
          resources.getPwd());
    } catch (Exception e) {
      log.error(e.getMessage());
      return false;
    }
  }

  @Override
  public void download(List<DatabaseDto> queryAll, HttpServletResponse response)
      throws IOException {
    List<Map<String, Object>> list = new ArrayList<>();
    for (DatabaseDto databaseDto : queryAll) {
      Map<String, Object> map = new LinkedHashMap<>();
      map.put("数据库名称", databaseDto.getName());
      map.put("数据库连接地址", databaseDto.getJdbcUrl());
      map.put("用户名", databaseDto.getUserName());
      map.put("创建日期", databaseDto.getCreateTime());
      list.add(map);
    }
    FileUtil.downloadExcel(list, response);
  }

}
