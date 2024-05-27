package com.daqinzhonggong.sb2.admin.modules.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.daqinzhonggong.sb2.admin.modules.system.domain.Dict;
import com.daqinzhonggong.sb2.admin.modules.system.domain.DictDetail;
import com.daqinzhonggong.sb2.admin.modules.system.domain.vo.DictQueryCriteria;
import com.daqinzhonggong.sb2.admin.modules.system.mapper.DictDetailMapper;
import com.daqinzhonggong.sb2.admin.modules.system.mapper.DictMapper;
import com.daqinzhonggong.sb2.admin.modules.system.service.DictService;
import com.daqinzhonggong.sb2.admin.utils.CacheKey;
import com.daqinzhonggong.sb2.admin.utils.FileUtil;
import com.daqinzhonggong.sb2.admin.utils.PageResult;
import com.daqinzhonggong.sb2.admin.utils.PageUtil;
import com.daqinzhonggong.sb2.admin.utils.RedisUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "dict")
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

  private final DictMapper dictMapper;
  private final RedisUtils redisUtils;
  private final DictDetailMapper deleteDetail;

  @Override
  public PageResult<Dict> queryAll(DictQueryCriteria criteria, Page<Object> page) {
    criteria.setOffset(page.offset());
    List<Dict> dicts = dictMapper.findAll(criteria);
    Long total = dictMapper.countAll(criteria);
    return PageUtil.toPage(dicts, total);
  }

  @Override
  public List<Dict> queryAll(DictQueryCriteria criteria) {
    return dictMapper.findAll(criteria);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void create(Dict resources) {
    save(resources);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void update(Dict resources) {
    // 清理缓存
    delCaches(resources);
    Dict dict = getById(resources.getId());
    dict.setName(resources.getName());
    dict.setDescription(resources.getDescription());
    saveOrUpdate(dict);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void delete(Set<Long> ids) {
    // 清理缓存
    List<Dict> dicts = dictMapper.selectBatchIds(ids);
    for (Dict dict : dicts) {
      delCaches(dict);
    }
    // 删除字典
    dictMapper.deleteBatchIds(ids);
    // 删除字典详情
    deleteDetail.deleteByDictBatchIds(ids);
  }

  @Override
  public void download(List<Dict> dicts, HttpServletResponse response) throws IOException {
    List<Map<String, Object>> list = new ArrayList<>();
    for (Dict dict : dicts) {
      if (CollectionUtil.isNotEmpty(dict.getDictDetails())) {
        for (DictDetail dictDetail : dict.getDictDetails()) {
          Map<String, Object> map = new LinkedHashMap<>();
          map.put("字典名称", dict.getName());
          map.put("字典描述", dict.getDescription());
          map.put("字典标签", dictDetail.getLabel());
          map.put("字典值", dictDetail.getValue());
          map.put("创建日期", dictDetail.getCreateTime());
          list.add(map);
        }
      } else {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("字典名称", dict.getName());
        map.put("字典描述", dict.getDescription());
        map.put("字典标签", null);
        map.put("字典值", null);
        map.put("创建日期", dict.getCreateTime());
        list.add(map);
      }
    }
    FileUtil.downloadExcel(list, response);
  }

  public void delCaches(Dict dict) {
    redisUtils.del(CacheKey.DICT_NAME + dict.getName());
  }

}