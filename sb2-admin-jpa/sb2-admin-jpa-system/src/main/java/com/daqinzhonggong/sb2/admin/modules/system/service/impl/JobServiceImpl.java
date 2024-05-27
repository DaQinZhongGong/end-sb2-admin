package com.daqinzhonggong.sb2.admin.modules.system.service.impl;

import com.daqinzhonggong.sb2.admin.exception.BadRequestException;
import com.daqinzhonggong.sb2.admin.exception.EntityExistException;
import com.daqinzhonggong.sb2.admin.modules.system.domain.Job;
import com.daqinzhonggong.sb2.admin.modules.system.repository.JobRepository;
import com.daqinzhonggong.sb2.admin.modules.system.repository.UserRepository;
import com.daqinzhonggong.sb2.admin.modules.system.service.JobService;
import com.daqinzhonggong.sb2.admin.modules.system.service.dto.JobDto;
import com.daqinzhonggong.sb2.admin.modules.system.service.dto.JobQueryCriteria;
import com.daqinzhonggong.sb2.admin.modules.system.service.mapstruct.JobMapper;
import com.daqinzhonggong.sb2.admin.utils.CacheKey;
import com.daqinzhonggong.sb2.admin.utils.FileUtil;
import com.daqinzhonggong.sb2.admin.utils.PageResult;
import com.daqinzhonggong.sb2.admin.utils.PageUtil;
import com.daqinzhonggong.sb2.admin.utils.QueryHelp;
import com.daqinzhonggong.sb2.admin.utils.RedisUtils;
import com.daqinzhonggong.sb2.admin.utils.ValidationUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "job")
public class JobServiceImpl implements JobService {

  private final JobRepository jobRepository;
  private final JobMapper jobMapper;
  private final RedisUtils redisUtils;
  private final UserRepository userRepository;

  @Override
  public PageResult<JobDto> queryAll(JobQueryCriteria criteria, Pageable pageable) {
    Page<Job> page = jobRepository.findAll(
        (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria,
            criteriaBuilder), pageable);
    return PageUtil.toPage(page.map(jobMapper::toDto).getContent(), page.getTotalElements());
  }

  @Override
  public List<JobDto> queryAll(JobQueryCriteria criteria) {
    List<Job> list = jobRepository.findAll(
        (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria,
            criteriaBuilder));
    return jobMapper.toDto(list);
  }

  @Override
  @Cacheable(key = "'id:' + #p0")
  public JobDto findById(Long id) {
    Job job = jobRepository.findById(id).orElseGet(Job::new);
    ValidationUtil.isNull(job.getId(), "Job", "id", id);
    return jobMapper.toDto(job);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void create(Job resources) {
    Job job = jobRepository.findByName(resources.getName());
    if (job != null) {
      throw new EntityExistException(Job.class, "name", resources.getName());
    }
    jobRepository.save(resources);
  }

  @Override
  @CacheEvict(key = "'id:' + #p0.id")
  @Transactional(rollbackFor = Exception.class)
  public void update(Job resources) {
    Job job = jobRepository.findById(resources.getId()).orElseGet(Job::new);
    Job old = jobRepository.findByName(resources.getName());
    if (old != null && !old.getId().equals(resources.getId())) {
      throw new EntityExistException(Job.class, "name", resources.getName());
    }
    ValidationUtil.isNull(job.getId(), "Job", "id", resources.getId());
    resources.setId(job.getId());
    jobRepository.save(resources);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void delete(Set<Long> ids) {
    jobRepository.deleteAllByIdIn(ids);
    // 删除缓存
    redisUtils.delByKeys(CacheKey.JOB_ID, ids);
  }

  @Override
  public void download(List<JobDto> jobDtos, HttpServletResponse response) throws IOException {
    List<Map<String, Object>> list = new ArrayList<>();
    for (JobDto jobDTO : jobDtos) {
      Map<String, Object> map = new LinkedHashMap<>();
      map.put("岗位名称", jobDTO.getName());
      map.put("岗位状态", jobDTO.getEnabled() ? "启用" : "停用");
      map.put("创建日期", jobDTO.getCreateTime());
      list.add(map);
    }
    FileUtil.downloadExcel(list, response);
  }

  @Override
  public void verification(Set<Long> ids) {
    if (userRepository.countByJobs(ids) > 0) {
      throw new BadRequestException("所选的岗位中存在用户关联，请解除关联再试！");
    }
  }

}
