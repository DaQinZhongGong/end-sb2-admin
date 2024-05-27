package com.daqinzhonggong.sb2.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.daqinzhonggong.sb2.admin.domain.LocalStorage;
import com.daqinzhonggong.sb2.admin.domain.vo.LocalStorageQueryCriteria;
import com.daqinzhonggong.sb2.admin.utils.PageResult;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

public interface LocalStorageService extends IService<LocalStorage> {

  /**
   * 分页查询
   *
   * @param criteria 条件
   * @param page     分页参数
   * @return /
   */
  PageResult<LocalStorage> queryAll(LocalStorageQueryCriteria criteria, Page<Object> page);

  /**
   * 查询全部数据
   *
   * @param criteria 条件
   * @return /
   */
  List<LocalStorage> queryAll(LocalStorageQueryCriteria criteria);

  /**
   * 上传
   *
   * @param name 文件名称
   * @param file 文件
   * @return /
   */
  LocalStorage create(String name, MultipartFile file);

  /**
   * 编辑
   *
   * @param resources 文件信息
   */
  void update(LocalStorage resources);

  /**
   * 多选删除
   *
   * @param ids /
   */
  void deleteAll(Long[] ids);

  /**
   * 导出数据
   *
   * @param localStorages 待导出的数据
   * @param response      /
   * @throws IOException /
   */
  void download(List<LocalStorage> localStorages, HttpServletResponse response) throws IOException;

}