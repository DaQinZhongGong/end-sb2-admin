package com.daqinzhonggong.sb2.admin.modules.mnt.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.daqinzhonggong.sb2.admin.modules.mnt.domain.Database;
import com.daqinzhonggong.sb2.admin.modules.mnt.domain.vo.DatabaseQueryCriteria;
import com.daqinzhonggong.sb2.admin.modules.mnt.mapper.DatabaseMapper;
import com.daqinzhonggong.sb2.admin.modules.mnt.service.DatabaseService;
import com.daqinzhonggong.sb2.admin.modules.mnt.util.SqlUtils;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DatabaseServiceImpl extends ServiceImpl<DatabaseMapper, Database> implements
    DatabaseService {

  private final DatabaseMapper databaseMapper;

  @Override
  public PageResult<Database> queryAll(DatabaseQueryCriteria criteria, Page<Object> page) {
    return PageUtil.toPage(databaseMapper.findAll(criteria, page));
  }

  @Override
  public List<Database> queryAll(DatabaseQueryCriteria criteria) {
    return databaseMapper.findAll(criteria);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void create(Database resources) {
    resources.setId(IdUtil.simpleUUID());
    save(resources);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void update(Database resources) {
    Database database = getById(resources.getId());
    database.copy(resources);
    saveOrUpdate(database);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void delete(Set<String> ids) {
    removeBatchByIds(ids);
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
  public void download(List<Database> databases, HttpServletResponse response) throws IOException {
    List<Map<String, Object>> list = new ArrayList<>();
    for (Database database : databases) {
      Map<String, Object> map = new LinkedHashMap<>();
      map.put("数据库名称", database.getName());
      map.put("数据库连接地址", database.getJdbcUrl());
      map.put("用户名", database.getUserName());
      map.put("创建日期", database.getCreateTime());
      list.add(map);
    }
    FileUtil.downloadExcel(list, response);
  }

}
