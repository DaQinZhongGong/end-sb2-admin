package com.daqinzhonggong.sb2.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.daqinzhonggong.sb2.admin.domain.ColumnInfo;
import com.daqinzhonggong.sb2.admin.domain.vo.TableInfo;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ColumnInfoMapper extends BaseMapper<ColumnInfo> {

  IPage<TableInfo> getTables(@Param("tableName") String tableName, Page<Object> page);

  List<ColumnInfo> findByTableNameOrderByIdAsc(@Param("tableName") String tableName);

  List<ColumnInfo> getColumns(@Param("tableName") String tableName);

}
