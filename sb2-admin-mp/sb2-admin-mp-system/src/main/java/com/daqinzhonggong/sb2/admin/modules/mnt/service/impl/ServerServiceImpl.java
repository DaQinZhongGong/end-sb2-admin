package com.daqinzhonggong.sb2.admin.modules.mnt.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.daqinzhonggong.sb2.admin.modules.mnt.domain.Server;
import com.daqinzhonggong.sb2.admin.modules.mnt.domain.vo.ServerQueryCriteria;
import com.daqinzhonggong.sb2.admin.modules.mnt.mapper.DeployServerMapper;
import com.daqinzhonggong.sb2.admin.modules.mnt.mapper.ServerMapper;
import com.daqinzhonggong.sb2.admin.modules.mnt.service.ServerService;
import com.daqinzhonggong.sb2.admin.modules.mnt.util.ExecuteShellUtil;
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
public class ServerServiceImpl extends ServiceImpl<ServerMapper, Server> implements ServerService {

  private final ServerMapper serverMapper;
  private final DeployServerMapper deployServerMapper;

  @Override
  public PageResult<Server> queryAll(ServerQueryCriteria criteria, Page<Object> page) {
    return PageUtil.toPage(serverMapper.findAll(criteria, page));
  }

  @Override
  public List<Server> queryAll(ServerQueryCriteria criteria) {
    return serverMapper.findAll(criteria);
  }

  @Override
  public Server findByIp(String ip) {
    return serverMapper.findByIp(ip);
  }

  @Override
  public Boolean testConnect(Server resources) {
    ExecuteShellUtil executeShellUtil = null;
    try {
      executeShellUtil = new ExecuteShellUtil(resources.getIp(), resources.getAccount(),
          resources.getPassword(), resources.getPort());
      return executeShellUtil.execute("ls") == 0;
    } catch (Exception e) {
      return false;
    } finally {
      if (executeShellUtil != null) {
        executeShellUtil.close();
      }
    }
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void create(Server resources) {
    save(resources);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void update(Server resources) {
    Server server = getById(resources.getId());
    server.copy(resources);
    saveOrUpdate(server);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void delete(Set<Long> ids) {
    removeBatchByIds(ids);
    // 删除与之关联的服务
    deployServerMapper.deleteByServerIds(ids);
  }

  @Override
  public void download(List<Server> servers, HttpServletResponse response) throws IOException {
    List<Map<String, Object>> list = new ArrayList<>();
    for (Server deploy : servers) {
      Map<String, Object> map = new LinkedHashMap<>();
      map.put("服务器名称", deploy.getName());
      map.put("服务器IP", deploy.getIp());
      map.put("端口", deploy.getPort());
      map.put("账号", deploy.getAccount());
      map.put("创建日期", deploy.getCreateTime());
      list.add(map);
    }
    FileUtil.downloadExcel(list, response);
  }
}
