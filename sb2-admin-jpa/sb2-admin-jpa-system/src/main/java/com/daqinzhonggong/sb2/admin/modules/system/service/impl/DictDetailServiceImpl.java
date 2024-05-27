package com.daqinzhonggong.sb2.admin.modules.system.service.impl;

import com.daqinzhonggong.sb2.admin.modules.system.domain.Dict;
import com.daqinzhonggong.sb2.admin.modules.system.domain.DictDetail;
import com.daqinzhonggong.sb2.admin.modules.system.repository.DictDetailRepository;
import com.daqinzhonggong.sb2.admin.modules.system.repository.DictRepository;
import com.daqinzhonggong.sb2.admin.modules.system.service.DictDetailService;
import com.daqinzhonggong.sb2.admin.modules.system.service.dto.DictDetailDto;
import com.daqinzhonggong.sb2.admin.modules.system.service.dto.DictDetailQueryCriteria;
import com.daqinzhonggong.sb2.admin.modules.system.service.mapstruct.DictDetailMapper;
import com.daqinzhonggong.sb2.admin.utils.CacheKey;
import com.daqinzhonggong.sb2.admin.utils.PageResult;
import com.daqinzhonggong.sb2.admin.utils.PageUtil;
import com.daqinzhonggong.sb2.admin.utils.QueryHelp;
import com.daqinzhonggong.sb2.admin.utils.RedisUtils;
import com.daqinzhonggong.sb2.admin.utils.ValidationUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "dict")
public class DictDetailServiceImpl implements DictDetailService {

  private final DictRepository dictRepository;
  private final DictDetailRepository dictDetailRepository;
  private final DictDetailMapper dictDetailMapper;
  private final RedisUtils redisUtils;

  @Override
  public PageResult<DictDetailDto> queryAll(DictDetailQueryCriteria criteria, Pageable pageable) {
    Page<DictDetail> page = dictDetailRepository.findAll(
        (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria,
            criteriaBuilder), pageable);
    return PageUtil.toPage(page.map(dictDetailMapper::toDto));
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void create(DictDetail resources) {
    dictDetailRepository.save(resources);
    // 清理缓存
    delCaches(resources);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void update(DictDetail resources) {
    DictDetail dictDetail = dictDetailRepository.findById(resources.getId())
        .orElseGet(DictDetail::new);
    ValidationUtil.isNull(dictDetail.getId(), "DictDetail", "id", resources.getId());
    resources.setId(dictDetail.getId());
    dictDetailRepository.save(resources);
    // 清理缓存
    delCaches(resources);
  }

  @Override
  @Cacheable(key = "'name:' + #p0")
  public List<DictDetailDto> getDictByName(String name) {
    return dictDetailMapper.toDto(dictDetailRepository.findByDictName(name));
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void delete(Long id) {
    DictDetail dictDetail = dictDetailRepository.findById(id).orElseGet(DictDetail::new);
    // 清理缓存
    delCaches(dictDetail);
    dictDetailRepository.deleteById(id);
  }

  public void delCaches(DictDetail dictDetail) {
    Dict dict = dictRepository.findById(dictDetail.getDict().getId()).orElseGet(Dict::new);
    redisUtils.del(CacheKey.DICT_NAME + dict.getName());
  }

}
