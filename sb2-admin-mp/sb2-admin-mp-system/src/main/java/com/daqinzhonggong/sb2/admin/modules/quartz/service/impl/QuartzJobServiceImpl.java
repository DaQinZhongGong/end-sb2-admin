package com.daqinzhonggong.sb2.admin.modules.quartz.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.daqinzhonggong.sb2.admin.exception.BadRequestException;
import com.daqinzhonggong.sb2.admin.modules.quartz.domain.QuartzJob;
import com.daqinzhonggong.sb2.admin.modules.quartz.domain.QuartzLog;
import com.daqinzhonggong.sb2.admin.modules.quartz.domain.vo.QuartzJobQueryCriteria;
import com.daqinzhonggong.sb2.admin.modules.quartz.mapper.QuartzJobMapper;
import com.daqinzhonggong.sb2.admin.modules.quartz.mapper.QuartzLogMapper;
import com.daqinzhonggong.sb2.admin.modules.quartz.service.QuartzJobService;
import com.daqinzhonggong.sb2.admin.modules.quartz.utils.QuartzManage;
import com.daqinzhonggong.sb2.admin.utils.FileUtil;
import com.daqinzhonggong.sb2.admin.utils.PageResult;
import com.daqinzhonggong.sb2.admin.utils.PageUtil;
import com.daqinzhonggong.sb2.admin.utils.RedisUtils;
import com.daqinzhonggong.sb2.admin.utils.StringUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.quartz.CronExpression;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service(value = "quartzJobService")
public class QuartzJobServiceImpl extends ServiceImpl<QuartzJobMapper, QuartzJob> implements
    QuartzJobService {

  private final QuartzJobMapper quartzJobMapper;
  private final QuartzLogMapper quartzLogMapper;
  private final QuartzManage quartzManage;
  private final RedisUtils redisUtils;

  @Override
  public PageResult<QuartzJob> queryAll(QuartzJobQueryCriteria criteria, Page<Object> page) {
    return PageUtil.toPage(quartzJobMapper.findAll(criteria, page));
  }

  @Override
  public PageResult<QuartzLog> queryAllLog(QuartzJobQueryCriteria criteria, Page<Object> page) {
    return PageUtil.toPage(quartzLogMapper.findAll(criteria, page));
  }

  @Override
  public List<QuartzJob> queryAll(QuartzJobQueryCriteria criteria) {
    return quartzJobMapper.findAll(criteria);
  }

  @Override
  public List<QuartzLog> queryAllLog(QuartzJobQueryCriteria criteria) {
    return quartzLogMapper.findAll(criteria);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void create(QuartzJob resources) {
    if (!CronExpression.isValidExpression(resources.getCronExpression())) {
      throw new BadRequestException("cron表达式格式错误");
    }
    save(resources);
    quartzManage.addJob(resources);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void update(QuartzJob resources) {
    if (!CronExpression.isValidExpression(resources.getCronExpression())) {
      throw new BadRequestException("cron表达式格式错误");
    }
    if (StringUtils.isNotBlank(resources.getSubTask())) {
      List<String> tasks = Arrays.asList(resources.getSubTask().split("[,，]"));
      if (tasks.contains(resources.getId().toString())) {
        throw new BadRequestException("子任务中不能添加当前任务ID");
      }
    }
    saveOrUpdate(resources);
    quartzManage.updateJobCron(resources);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void updateIsPause(QuartzJob quartzJob) {
    if (quartzJob.getIsPause()) {
      quartzManage.resumeJob(quartzJob);
      quartzJob.setIsPause(false);
    } else {
      quartzManage.pauseJob(quartzJob);
      quartzJob.setIsPause(true);
    }
    saveOrUpdate(quartzJob);
  }

  @Override
  public void execution(QuartzJob quartzJob) {
    quartzManage.runJobNow(quartzJob);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void delete(Set<Long> ids) {
    for (Long id : ids) {
      QuartzJob quartzJob = getById(id);
      quartzManage.deleteJob(quartzJob);
      removeById(quartzJob);
    }
  }

  @Async
  @Override
  @Transactional(rollbackFor = Exception.class)
  public void executionSubJob(String[] tasks) throws InterruptedException {
    for (String id : tasks) {
      if (StrUtil.isBlank(id)) {
        // 如果是手动清除子任务id，会出现id为空字符串的问题
        continue;
      }
      QuartzJob quartzJob = getById(Long.parseLong(id));
      // 执行任务
      String uuid = IdUtil.simpleUUID();
      quartzJob.setUuid(uuid);
      // 执行任务
      execution(quartzJob);
      // 获取执行状态，如果执行失败则停止后面的子任务执行
      Boolean result = (Boolean) redisUtils.get(uuid);
      while (result == null) {
        // 休眠5秒，再次获取子任务执行情况
        Thread.sleep(5000);
        result = (Boolean) redisUtils.get(uuid);
      }
      if (!result) {
        redisUtils.del(uuid);
        break;
      }
    }
  }

  @Override
  public void download(List<QuartzJob> quartzJobs, HttpServletResponse response)
      throws IOException {
    List<Map<String, Object>> list = new ArrayList<>();
    for (QuartzJob quartzJob : quartzJobs) {
      Map<String, Object> map = new LinkedHashMap<>();
      map.put("任务名称", quartzJob.getJobName());
      map.put("Bean名称", quartzJob.getBeanName());
      map.put("执行方法", quartzJob.getMethodName());
      map.put("参数", quartzJob.getParams());
      map.put("表达式", quartzJob.getCronExpression());
      map.put("状态", quartzJob.getIsPause() ? "暂停中" : "运行中");
      map.put("描述", quartzJob.getDescription());
      map.put("创建日期", quartzJob.getCreateTime());
      list.add(map);
    }
    FileUtil.downloadExcel(list, response);
  }

  @Override
  public void downloadLog(List<QuartzLog> queryAllLog, HttpServletResponse response)
      throws IOException {
    List<Map<String, Object>> list = new ArrayList<>();
    for (QuartzLog quartzLog : queryAllLog) {
      Map<String, Object> map = new LinkedHashMap<>();
      map.put("任务名称", quartzLog.getJobName());
      map.put("Bean名称", quartzLog.getBeanName());
      map.put("执行方法", quartzLog.getMethodName());
      map.put("参数", quartzLog.getParams());
      map.put("表达式", quartzLog.getCronExpression());
      map.put("异常详情", quartzLog.getExceptionDetail());
      map.put("耗时/毫秒", quartzLog.getTime());
      map.put("状态", quartzLog.getIsSuccess() ? "成功" : "失败");
      map.put("创建日期", quartzLog.getCreateTime());
      list.add(map);
    }
    FileUtil.downloadExcel(list, response);
  }

}
