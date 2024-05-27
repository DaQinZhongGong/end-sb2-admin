package com.daqinzhonggong.sb2.admin.service.impl;

import cn.hutool.core.lang.Dict;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.daqinzhonggong.sb2.admin.annotation.Log;
import com.daqinzhonggong.sb2.admin.domain.SysLog;
import com.daqinzhonggong.sb2.admin.domain.vo.SysLogQueryCriteria;
import com.daqinzhonggong.sb2.admin.mapper.SysLogMapper;
import com.daqinzhonggong.sb2.admin.service.SysLogService;
import com.daqinzhonggong.sb2.admin.utils.FileUtil;
import com.daqinzhonggong.sb2.admin.utils.PageResult;
import com.daqinzhonggong.sb2.admin.utils.PageUtil;
import com.daqinzhonggong.sb2.admin.utils.StringUtils;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {

  private final SysLogMapper sysLogMapper;

  @Override
  public PageResult<SysLog> queryAll(SysLogQueryCriteria criteria, Page<SysLog> page) {
    return PageUtil.toPage(sysLogMapper.queryAll(criteria, page));
  }

  @Override
  public List<SysLog> queryAll(SysLogQueryCriteria criteria) {
    return sysLogMapper.queryAll(criteria);
  }

  @Override
  public PageResult<SysLog> queryAllByUser(SysLogQueryCriteria criteria, Page<SysLog> page) {
    return PageUtil.toPage(sysLogMapper.queryAllByUser(criteria, page));
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void save(String username, String browser, String ip, ProceedingJoinPoint joinPoint,
      SysLog sysLog) {
    if (sysLog == null) {
      throw new IllegalArgumentException("Log 不能为 null!");
    }
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Method method = signature.getMethod();
    Log aopLog = method.getAnnotation(Log.class);

    // 方法路径
    String methodName =
        joinPoint.getTarget().getClass().getName() + "." + signature.getName() + "()";

    // 描述
    sysLog.setDescription(aopLog.value());

    sysLog.setRequestIp(ip);
    sysLog.setAddress(StringUtils.getCityInfo(sysLog.getRequestIp()));
    sysLog.setMethod(methodName);
    sysLog.setUsername(username);
    sysLog.setParams(getParameter(method, joinPoint.getArgs()));
    // 记录登录用户，隐藏密码信息
    if (signature.getName().equals("login") && StringUtils.isNotEmpty(sysLog.getParams())) {
      JSONObject obj = JSON.parseObject(sysLog.getParams());
      sysLog.setUsername(obj.getString("username"));
      sysLog.setParams(JSON.toJSONString(Dict.create().set("username", sysLog.getUsername())));
    }
    sysLog.setBrowser(browser);
    // 保存
    save(sysLog);
  }

  /**
   * 根据方法和传入的参数获取请求参数
   */
  private String getParameter(Method method, Object[] args) {
    List<Object> argList = new ArrayList<>();
    Parameter[] parameters = method.getParameters();
    for (int i = 0; i < parameters.length; i++) {
      // 过滤掉不能序列化的类型: MultiPartFile
      if (args[i] instanceof MultipartFile) {
        continue;
      }
      //将RequestBody注解修饰的参数作为请求参数
      RequestBody requestBody = parameters[i].getAnnotation(RequestBody.class);
      if (requestBody != null) {
        argList.add(args[i]);
      }
      //将RequestParam注解修饰的参数作为请求参数
      RequestParam requestParam = parameters[i].getAnnotation(RequestParam.class);
      if (requestParam != null) {
        Map<String, Object> map = new HashMap<>(2);
        String key = parameters[i].getName();
        if (!StringUtils.isEmpty(requestParam.value())) {
          key = requestParam.value();
        }
        map.put(key, args[i]);
        argList.add(map);
      }
    }
    if (argList.isEmpty()) {
      return "";
    }
    return argList.size() == 1 ? JSON.toJSONString(argList.get(0)) : JSON.toJSONString(argList);
  }

  @Override
  public Object findByErrDetail(Long id) {
    String details = sysLogMapper.getExceptionDetails(id);
    return Dict.create().set("exception", details);
  }

  @Override
  public void download(List<SysLog> sysLogs, HttpServletResponse response) throws IOException {
    List<Map<String, Object>> list = new ArrayList<>();
    for (SysLog sysLog : sysLogs) {
      Map<String, Object> map = new LinkedHashMap<>();
      map.put("用户名", sysLog.getUsername());
      map.put("IP", sysLog.getRequestIp());
      map.put("IP来源", sysLog.getAddress());
      map.put("描述", sysLog.getDescription());
      map.put("浏览器", sysLog.getBrowser());
      map.put("请求耗时/毫秒", sysLog.getTime());
      map.put("异常详情", sysLog.getExceptionDetail());
      map.put("创建日期", sysLog.getCreateTime());
      list.add(map);
    }
    FileUtil.downloadExcel(list, response);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void delAllByError() {
    // 删除 ERROR 级别的日志
    sysLogMapper.deleteByLevel("ERROR");
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void delAllByInfo() {
    // 删除 INFO 级别的日志
    sysLogMapper.deleteByLevel("INFO");
  }

}
