package com.daqinzhonggong.sb2.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.daqinzhonggong.sb2.admin.domain.QiniuConfig;
import com.daqinzhonggong.sb2.admin.domain.QiniuContent;
import com.daqinzhonggong.sb2.admin.domain.vo.QiniuQueryCriteria;
import com.daqinzhonggong.sb2.admin.utils.PageResult;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

public interface QiniuContentService extends IService<QiniuContent> {

  /**
   * 分页查询
   *
   * @param criteria 条件
   * @param page     分页参数
   * @return /
   */
  PageResult<QiniuContent> queryAll(QiniuQueryCriteria criteria, Page<Object> page);

  /**
   * 查询全部
   *
   * @param criteria 条件
   * @return /
   */
  List<QiniuContent> queryAll(QiniuQueryCriteria criteria);

  /**
   * 上传文件
   *
   * @param file        文件
   * @param qiniuConfig 配置
   * @return QiniuContent
   */
  QiniuContent upload(MultipartFile file, QiniuConfig qiniuConfig);

  /**
   * 下载文件
   *
   * @param content 文件信息
   * @param config  配置
   * @return String
   */
  String download(QiniuContent content, QiniuConfig config);

  /**
   * 删除文件
   *
   * @param content 文件
   * @param config  配置
   */
  void delete(QiniuContent content, QiniuConfig config);

  /**
   * 同步数据
   *
   * @param config 配置
   */
  void synchronize(QiniuConfig config);

  /**
   * 删除文件
   *
   * @param ids    文件ID数组
   * @param config 配置
   */
  void deleteAll(Long[] ids, QiniuConfig config);

  /**
   * 导出数据
   *
   * @param queryAll /
   * @param response /
   * @throws IOException /
   */
  void downloadList(List<QiniuContent> queryAll, HttpServletResponse response) throws IOException;

}
