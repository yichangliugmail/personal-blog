package com.lyc.service;

import com.lyc.common.PageResult;
import com.lyc.model.dto.ConditionDTO;
import com.lyc.model.po.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyc.model.vo.MenuVO;

import java.util.List;

/**
* @author 蜡笔
* @description 针对表【t_menu】的数据库操作Service
* @createDate 2023-04-24 21:06:36
*/
public interface MenuService extends IService<Menu> {

    /**
     * 获取菜单集合
     */
    List<MenuVO> getListMenu(ConditionDTO conditionDTO);
}
