package com.lyc.service;

import com.lyc.model.dto.ConditionDTO;
import com.lyc.model.dto.MenuDTO;
import com.lyc.model.po.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyc.model.vo.MenuOption;
import com.lyc.model.vo.MenuTree;
import com.lyc.model.vo.MenuVO;

import java.util.List;

/**
* @author 蜡笔
* @description 针对表【t_menu】的数据库操作Service
* @createDate 2023-04-24 21:06:36
*/
public interface MenuService extends IService<Menu> {

    /**
     * 查看菜单列表
     *
     * @param condition 查询条件
     * @return 菜单列表
     */
    List<MenuVO> listMenuVO(ConditionDTO condition);

    /**
     * 添加菜单
     *
     * @param menu 菜单
     */
    void addMenu(MenuDTO menu);

    /**
     * 删除菜单
     *
     * @param menuId 菜单id
     */
    void deleteMenu(Integer menuId);

    /**
     * 修改菜单
     *
     * @param menu 菜单
     */
    void updateMenu(MenuDTO menu);

    /**
     * 获取菜单下拉树
     *
     * @return 菜单下拉树
     */
    List<MenuTree> listMenuTree();

    /**
     * 获取菜单选项树
     *
     * @return 菜单选项树
     */
    List<MenuOption> listMenuOption();

    /**
     * 编辑菜单
     *
     * @param menuId 菜单id
     * @return 菜单
     */
    MenuDTO editMenu(Integer menuId);
}
