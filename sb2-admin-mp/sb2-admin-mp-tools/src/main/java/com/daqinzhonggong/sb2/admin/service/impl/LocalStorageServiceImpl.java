package com.daqinzhonggong.sb2.admin.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.daqinzhonggong.sb2.admin.config.FileProperties;
import com.daqinzhonggong.sb2.admin.domain.LocalStorage;
import com.daqinzhonggong.sb2.admin.domain.vo.LocalStorageQueryCriteria;
import com.daqinzhonggong.sb2.admin.exception.BadRequestException;
import com.daqinzhonggong.sb2.admin.mapper.LocalStorageMapper;
import com.daqinzhonggong.sb2.admin.service.LocalStorageService;
import com.daqinzhonggong.sb2.admin.utils.FileUtil;
import com.daqinzhonggong.sb2.admin.utils.PageResult;
import com.daqinzhonggong.sb2.admin.utils.PageUtil;
import com.daqinzhonggong.sb2.admin.utils.StringUtils;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class LocalStorageServiceImpl extends
    ServiceImpl<LocalStorageMapper, LocalStorage> implements LocalStorageService {

  private final LocalStorageMapper localStorageMapper;
  private final FileProperties properties;

  @Override
  public PageResult<LocalStorage> queryAll(LocalStorageQueryCriteria criteria, Page<Object> page) {
    return PageUtil.toPage(localStorageMapper.findAll(criteria, page));
  }

  @Override
  public List<LocalStorage> queryAll(LocalStorageQueryCriteria criteria) {
    return localStorageMapper.findAll(criteria);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public LocalStorage create(String name, MultipartFile multipartFile) {
    FileUtil.checkSize(properties.getMaxSize(), multipartFile.getSize());
    String suffix = FileUtil.getExtensionName(multipartFile.getOriginalFilename());
    String type = FileUtil.getFileType(suffix);
    File file = FileUtil.upload(multipartFile,
        properties.getPath().getPath() + type + File.separator);
    if (ObjectUtil.isNull(file)) {
      throw new BadRequestException("上传失败");
    }
    try {
      name =
          StringUtils.isBlank(name) ? FileUtil.getFileNameNoEx(multipartFile.getOriginalFilename())
              : name;
      LocalStorage localStorage = new LocalStorage(
          file.getName(),
          name,
          suffix,
          file.getPath(),
          type,
          FileUtil.getSize(multipartFile.getSize())
      );
      save(localStorage);
      return localStorage;
    } catch (Exception e) {
      FileUtil.del(file);
      throw e;
    }
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void update(LocalStorage resources) {
    LocalStorage localStorage = getById(resources.getId());
    localStorage.copy(resources);
    saveOrUpdate(localStorage);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void deleteAll(Long[] ids) {
    for (Long id : ids) {
      LocalStorage storage = getById(id);
      FileUtil.del(storage.getPath());
      removeById(storage);
    }
  }

  @Override
  public void download(List<LocalStorage> queryAll, HttpServletResponse response)
      throws IOException {
    List<Map<String, Object>> list = new ArrayList<>();
    for (LocalStorage localStorage : queryAll) {
      Map<String, Object> map = new LinkedHashMap<>();
      map.put("文件名", localStorage.getRealName());
      map.put("备注名", localStorage.getName());
      map.put("文件类型", localStorage.getType());
      map.put("文件大小", localStorage.getSize());
      map.put("创建者", localStorage.getCreateBy());
      map.put("创建日期", localStorage.getCreateTime());
      list.add(map);
    }
    FileUtil.downloadExcel(list, response);
  }

}
