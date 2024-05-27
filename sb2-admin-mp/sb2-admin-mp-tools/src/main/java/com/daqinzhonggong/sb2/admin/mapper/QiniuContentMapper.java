package com.daqinzhonggong.sb2.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.daqinzhonggong.sb2.admin.domain.QiniuContent;
import com.daqinzhonggong.sb2.admin.domain.vo.QiniuQueryCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface QiniuContentMapper extends BaseMapper<QiniuContent> {

  QiniuContent findByKey(@Param("name") String name);

  IPage<QiniuContent> findAll(@Param("criteria") QiniuQueryCriteria criteria, Page<Object> page);

  List<QiniuContent> findAll(QiniuQueryCriteria criteria);

}
