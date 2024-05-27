package com.daqinzhonggong.sb2.admin.modules.mnt.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.daqinzhonggong.sb2.admin.exception.BadRequestException;
import com.daqinzhonggong.sb2.admin.modules.mnt.domain.App;
import com.daqinzhonggong.sb2.admin.modules.mnt.domain.vo.AppQueryCriteria;
import com.daqinzhonggong.sb2.admin.modules.mnt.mapper.AppMapper;
import com.daqinzhonggong.sb2.admin.modules.mnt.mapper.DeployMapper;
import com.daqinzhonggong.sb2.admin.modules.mnt.mapper.DeployServerMapper;
import com.daqinzhonggong.sb2.admin.modules.mnt.service.AppService;
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
public class AppServiceImpl extends ServiceImpl<AppMapper, App> implements AppService {

  private final AppMapper appMapper;
  private final DeployMapper deployMapper;
  private final DeployServerMapper deployServerMapper;

  @Override
  public PageResult<App> queryAll(AppQueryCriteria criteria, Page<Object> page) {
    return PageUtil.toPage(appMapper.queryAll(criteria, page));
  }

  @Override
  public List<App> queryAll(AppQueryCriteria criteria) {
    return appMapper.queryAll(criteria);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void create(App resources) {
    verification(resources);
    save(resources);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void update(App resources) {
    verification(resources);
    App app = getById(resources.getId());
    app.copy(resources);
    saveOrUpdate(app);
  }

  private void verification(App resources) {
    String opt = "/opt";
    String home = "/home";
    if (!(resources.getUploadPath().startsWith(opt) || resources.getUploadPath()
        .startsWith(home))) {
      throw new BadRequestException("文件只能上传在opt目录或者home目录 ");
    }
    if (!(resources.getDeployPath().startsWith(opt) || resources.getDeployPath()
        .startsWith(home))) {
      throw new BadRequestException("文件只能部署在opt目录或者home目录 ");
    }
    if (!(resources.getBackupPath().startsWith(opt) || resources.getBackupPath()
        .startsWith(home))) {
      throw new BadRequestException("文件只能备份在opt目录或者home目录 ");
    }
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void delete(Set<Long> ids) {
    // 删除应用
    removeBatchByIds(ids);
    // 删除部署
    Set<Long> deployIds = deployMapper.getIdByAppIds(ids);
    if (deployIds != null && deployIds.size() > 0) {
      deployServerMapper.deleteByDeployIds(deployIds);
      deployMapper.deleteBatchIds(deployIds);
    }
  }

  @Override
  public void download(List<App> apps, HttpServletResponse response) throws IOException {
    List<Map<String, Object>> list = new ArrayList<>();
    for (App app : apps) {
      Map<String, Object> map = new LinkedHashMap<>();
      map.put("应用名称", app.getName());
      map.put("端口", app.getPort());
      map.put("上传目录", app.getUploadPath());
      map.put("部署目录", app.getDeployPath());
      map.put("备份目录", app.getBackupPath());
      map.put("启动脚本", app.getStartScript());
      map.put("部署脚本", app.getDeployScript());
      map.put("创建日期", app.getCreateTime());
      list.add(map);
    }
    FileUtil.downloadExcel(list, response);
  }

}
