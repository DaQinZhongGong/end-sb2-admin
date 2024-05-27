package com.daqinzhonggong.sb2.admin.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.daqinzhonggong.sb2.admin.config.FileProperties;
import com.daqinzhonggong.sb2.admin.domain.LocalStorage;
import com.daqinzhonggong.sb2.admin.exception.BadRequestException;
import com.daqinzhonggong.sb2.admin.repository.LocalStorageRepository;
import com.daqinzhonggong.sb2.admin.service.LocalStorageService;
import com.daqinzhonggong.sb2.admin.service.dto.LocalStorageDto;
import com.daqinzhonggong.sb2.admin.service.dto.LocalStorageQueryCriteria;
import com.daqinzhonggong.sb2.admin.service.mapstruct.LocalStorageMapper;
import com.daqinzhonggong.sb2.admin.utils.FileUtil;
import com.daqinzhonggong.sb2.admin.utils.PageResult;
import com.daqinzhonggong.sb2.admin.utils.PageUtil;
import com.daqinzhonggong.sb2.admin.utils.QueryHelp;
import com.daqinzhonggong.sb2.admin.utils.StringUtils;
import com.daqinzhonggong.sb2.admin.utils.ValidationUtil;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class LocalStorageServiceImpl implements LocalStorageService {

  private final LocalStorageRepository localStorageRepository;
  private final LocalStorageMapper localStorageMapper;
  private final FileProperties properties;

  @Override
  public PageResult<LocalStorageDto> queryAll(LocalStorageQueryCriteria criteria,
      Pageable pageable) {
    Page<LocalStorage> page = localStorageRepository.findAll(
        (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria,
            criteriaBuilder), pageable);
    return PageUtil.toPage(page.map(localStorageMapper::toDto));
  }

  @Override
  public List<LocalStorageDto> queryAll(LocalStorageQueryCriteria criteria) {
    return localStorageMapper.toDto(localStorageRepository.findAll(
        (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria,
            criteriaBuilder)));
  }

  @Override
  public LocalStorageDto findById(Long id) {
    LocalStorage localStorage = localStorageRepository.findById(id).orElseGet(LocalStorage::new);
    ValidationUtil.isNull(localStorage.getId(), "LocalStorage", "id", id);
    return localStorageMapper.toDto(localStorage);
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
      return localStorageRepository.save(localStorage);
    } catch (Exception e) {
      FileUtil.del(file);
      throw e;
    }
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void update(LocalStorage resources) {
    LocalStorage localStorage = localStorageRepository.findById(resources.getId())
        .orElseGet(LocalStorage::new);
    ValidationUtil.isNull(localStorage.getId(), "LocalStorage", "id", resources.getId());
    localStorage.copy(resources);
    localStorageRepository.save(localStorage);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void deleteAll(Long[] ids) {
    for (Long id : ids) {
      LocalStorage storage = localStorageRepository.findById(id).orElseGet(LocalStorage::new);
      FileUtil.del(storage.getPath());
      localStorageRepository.delete(storage);
    }
  }

  @Override
  public void download(List<LocalStorageDto> queryAll, HttpServletResponse response)
      throws IOException {
    List<Map<String, Object>> list = new ArrayList<>();
    for (LocalStorageDto localStorageDTO : queryAll) {
      Map<String, Object> map = new LinkedHashMap<>();
      map.put("文件名", localStorageDTO.getRealName());
      map.put("备注名", localStorageDTO.getName());
      map.put("文件类型", localStorageDTO.getType());
      map.put("文件大小", localStorageDTO.getSize());
      map.put("创建者", localStorageDTO.getCreateBy());
      map.put("创建日期", localStorageDTO.getCreateTime());
      list.add(map);
    }
    FileUtil.downloadExcel(list, response);
  }

}
