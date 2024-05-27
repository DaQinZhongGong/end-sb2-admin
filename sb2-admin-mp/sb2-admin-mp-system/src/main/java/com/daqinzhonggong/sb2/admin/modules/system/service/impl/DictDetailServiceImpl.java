package com.daqinzhonggong.sb2.admin.modules.system.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.daqinzhonggong.sb2.admin.modules.system.domain.Dict;
import com.daqinzhonggong.sb2.admin.modules.system.domain.DictDetail;
import com.daqinzhonggong.sb2.admin.modules.system.domain.vo.DictDetailQueryCriteria;
import com.daqinzhonggong.sb2.admin.modules.system.mapper.DictDetailMapper;
import com.daqinzhonggong.sb2.admin.modules.system.mapper.DictMapper;
import com.daqinzhonggong.sb2.admin.modules.system.service.DictDetailService;
import com.daqinzhonggong.sb2.admin.utils.CacheKey;
import com.daqinzhonggong.sb2.admin.utils.PageResult;
import com.daqinzhonggong.sb2.admin.utils.PageUtil;
import com.daqinzhonggong.sb2.admin.utils.RedisUtils;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "dict")
public class DictDetailServiceImpl extends ServiceImpl<DictDetailMapper, DictDetail> implements
    DictDetailService {

  private final DictMapper dictMapper;
  private final DictDetailMapper dictDetailMapper;
  private final RedisUtils redisUtils;

  @Override
  public PageResult<DictDetail> queryAll(DictDetailQueryCriteria criteria, Page<Object> page) {
    return PageUtil.toPage(dictDetailMapper.findAll(criteria, page));
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void create(DictDetail resources) {
    resources.setDictId(resources.getDict().getId());
    save(resources);
    // 清理缓存
    delCaches(resources);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void update(DictDetail resources) {
    DictDetail dictDetail = getById(resources.getId());
    resources.setId(dictDetail.getId());
    // 更新数据
    saveOrUpdate(resources);
    // 清理缓存
    delCaches(dictDetail);
  }

  @Override
  @Cacheable(key = "'name:' + #p0")
  public List<DictDetail> getDictByName(String name) {
    return dictDetailMapper.findByDictName(name);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void delete(Long id) {
    DictDetail dictDetail = getById(id);
    removeById(id);
    // 清理缓存
    delCaches(dictDetail);
  }

  public void delCaches(DictDetail dictDetail) {
    Dict dict = dictMapper.selectById(dictDetail.getDictId());
    redisUtils.del(CacheKey.DICT_NAME + dict.getName());
  }

}