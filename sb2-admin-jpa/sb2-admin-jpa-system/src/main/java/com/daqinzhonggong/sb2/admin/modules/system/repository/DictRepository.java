package com.daqinzhonggong.sb2.admin.modules.system.repository;

import com.daqinzhonggong.sb2.admin.modules.system.domain.Dict;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * <p> JpaRepository<Dict, Long>
 * JpaRepository是Spring Data JPA的一个核心接口，它提供了大量的方法来执行基本的CRUD操作。 <Dict,
 * Long>中的Dict表示这个仓库是用于操作Dict这个实体类的。Long表示Dict实体的主键类型是Long。
 * 通过继承JpaRepository，DictRepository自动获得了如save(), findAll(), findById(), deleteById(),
 * existsById()等方法，无需手动实现。 JpaSpecificationExecutor<Dict> JpaSpecificationExecutor是Spring Data
 * JPA的另一个接口，它允许你使用Specification来构建复杂的查询条件。 通过Specification，你可以动态地创建查询条件，例如组合多个条件、排序、分页等。
 * JpaSpecificationExecutor提供了如findAll(Specification<T> spec)这样的方法，允许你传递一个Specification对象作为查询条件。</>
 */
public interface DictRepository extends JpaRepository<Dict, Long>, JpaSpecificationExecutor<Dict> {

  /**
   * 删除
   *
   * @param ids /
   */
  void deleteByIdIn(Set<Long> ids);

  /**
   * 查询
   *
   * @param ids /
   * @return /
   */
  List<Dict> findByIdIn(Set<Long> ids);

}