package com.daqinzhonggong.sb2.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.daqinzhonggong.sb2.admin.domain.GenConfig;
import com.daqinzhonggong.sb2.admin.mapper.GenConfigMapper;
import com.daqinzhonggong.sb2.admin.service.GenConfigService;
import java.io.File;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@SuppressWarnings({"unchecked", "all"})
public class GenConfigServiceImpl extends ServiceImpl<GenConfigMapper, GenConfig> implements
    GenConfigService {

  private final GenConfigMapper genConfigMapper;

  @Override
  public GenConfig find(String tableName) {
    GenConfig genConfig = genConfigMapper.findByTableName(tableName);
    if (genConfig == null) {
      return new GenConfig(tableName);
    }
    return genConfig;
  }

  @Override
  public GenConfig update(String tableName, GenConfig genConfig) {
    String separator = File.separator;
    String[] paths;
    String symbol = "\\";
    if (symbol.equals(separator)) {
      paths = genConfig.getPath().split("\\\\");
    } else {
      paths = genConfig.getPath().split(File.separator);
    }
    StringBuilder api = new StringBuilder();
    for (String path : paths) {
      api.append(path);
      api.append(separator);
      if ("src".equals(path)) {
        api.append("api");
        break;
      }
    }
    genConfig.setApiPath(api.toString());
    saveOrUpdate(genConfig);
    return genConfig;
  }

}
