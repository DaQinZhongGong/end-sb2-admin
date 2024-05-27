package com.daqinzhonggong.sb2.admin.modules.system.rest;

import cn.hutool.core.collection.CollectionUtil;
import com.daqinzhonggong.sb2.admin.annotation.Log;
import com.daqinzhonggong.sb2.admin.base.BaseEntity.Update;
import com.daqinzhonggong.sb2.admin.exception.BadRequestException;
import com.daqinzhonggong.sb2.admin.modules.system.domain.Menu;
import com.daqinzhonggong.sb2.admin.modules.system.domain.vo.MenuVo;
import com.daqinzhonggong.sb2.admin.modules.system.service.MenuService;
import com.daqinzhonggong.sb2.admin.modules.system.service.dto.MenuDto;
import com.daqinzhonggong.sb2.admin.modules.system.service.dto.MenuQueryCriteria;
import com.daqinzhonggong.sb2.admin.modules.system.service.mapstruct.MenuMapper;
import com.daqinzhonggong.sb2.admin.utils.PageResult;
import com.daqinzhonggong.sb2.admin.utils.PageUtil;
import com.daqinzhonggong.sb2.admin.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Api(tags = "系统：菜单管理")
@RequestMapping("/api/menus")
public class MenuController {

  private final MenuService menuService;
  private final MenuMapper menuMapper;
  private static final String ENTITY_NAME = "menu";

  @ApiOperation("导出菜单数据")
  @GetMapping(value = "/download")
  @PreAuthorize("@el.check('menu:list')")
  public void exportMenu(HttpServletResponse response, MenuQueryCriteria criteria)
      throws Exception {
    menuService.download(menuService.queryAll(criteria, false), response);
  }

  @GetMapping(value = "/build")
  @ApiOperation("获取前端所需菜单")
  public ResponseEntity<List<MenuVo>> buildMenus() {
    List<MenuDto> menuDtoList = menuService.findByUser(SecurityUtils.getCurrentUserId());
    List<MenuDto> menus = menuService.buildTree(menuDtoList);
    return new ResponseEntity<>(menuService.buildMenus(menus), HttpStatus.OK);
  }

  @ApiOperation("返回全部的菜单")
  @GetMapping(value = "/lazy")
  @PreAuthorize("@el.check('menu:list','roles:list')")
  public ResponseEntity<List<MenuDto>> queryAllMenu(@RequestParam Long pid) {
    return new ResponseEntity<>(menuService.getMenus(pid), HttpStatus.OK);
  }

  @ApiOperation("根据菜单ID返回所有子节点ID，包含自身ID")
  @GetMapping(value = "/child")
  @PreAuthorize("@el.check('menu:list','roles:list')")
  public ResponseEntity<Object> childMenu(@RequestParam Long id) {
    Set<Menu> menuSet = new HashSet<>();
    List<MenuDto> menuList = menuService.getMenus(id);
    menuSet.add(menuService.findOne(id));
    menuSet = menuService.getChildMenus(menuMapper.toEntity(menuList), menuSet);
    Set<Long> ids = menuSet.stream().map(Menu::getId).collect(Collectors.toSet());
    return new ResponseEntity<>(ids, HttpStatus.OK);
  }

  @GetMapping
  @ApiOperation("查询菜单")
  @PreAuthorize("@el.check('menu:list')")
  public ResponseEntity<PageResult<MenuDto>> queryMenu(MenuQueryCriteria criteria)
      throws Exception {
    List<MenuDto> menuDtoList = menuService.queryAll(criteria, true);
    return new ResponseEntity<>(PageUtil.toPage(menuDtoList, menuDtoList.size()), HttpStatus.OK);
  }

  @ApiOperation("查询菜单:根据ID获取同级与上级数据")
  @PostMapping("/superior")
  @PreAuthorize("@el.check('menu:list')")
  public ResponseEntity<List<MenuDto>> getMenuSuperior(@RequestBody List<Long> ids) {
    Set<MenuDto> menuDtos = new LinkedHashSet<>();
    if (CollectionUtil.isNotEmpty(ids)) {
      for (Long id : ids) {
        MenuDto menuDto = menuService.findById(id);
        List<MenuDto> menuDtoList = menuService.getSuperior(menuDto, new ArrayList<>());
        for (MenuDto menu : menuDtoList) {
          if (menu.getId().equals(menuDto.getPid())) {
            menu.setSubCount(menu.getSubCount() - 1);
          }
        }
        menuDtos.addAll(menuDtoList);
      }
      // 编辑菜单时不显示自己以及自己下级的数据，避免出现PID数据环形问题
      menuDtos = menuDtos.stream().filter(i -> !ids.contains(i.getId()))
          .collect(Collectors.toSet());
      return new ResponseEntity<>(menuService.buildTree(new ArrayList<>(menuDtos)), HttpStatus.OK);
    }
    return new ResponseEntity<>(menuService.getMenus(null), HttpStatus.OK);
  }

  @Log("新增菜单")
  @ApiOperation("新增菜单")
  @PostMapping
  @PreAuthorize("@el.check('menu:add')")
  public ResponseEntity<Object> createMenu(@Validated @RequestBody Menu resources) {
    if (resources.getId() != null) {
      throw new BadRequestException("A new " + ENTITY_NAME + " cannot already have an ID");
    }
    menuService.create(resources);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @Log("修改菜单")
  @ApiOperation("修改菜单")
  @PutMapping
  @PreAuthorize("@el.check('menu:edit')")
  public ResponseEntity<Object> updateMenu(@Validated(Update.class) @RequestBody Menu resources) {
    menuService.update(resources);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @Log("删除菜单")
  @ApiOperation("删除菜单")
  @DeleteMapping
  @PreAuthorize("@el.check('menu:del')")
  public ResponseEntity<Object> deleteMenu(@RequestBody Set<Long> ids) {
    Set<Menu> menuSet = new HashSet<>();
    for (Long id : ids) {
      List<MenuDto> menuList = menuService.getMenus(id);
      menuSet.add(menuService.findOne(id));
      menuSet = menuService.getChildMenus(menuMapper.toEntity(menuList), menuSet);
    }
    menuService.delete(menuSet);
    return new ResponseEntity<>(HttpStatus.OK);
  }

}
