package com.lyc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyc.model.dto.ConditionDTO;
import com.lyc.model.po.Menu;
import com.lyc.model.vo.MenuVO;
import com.lyc.service.MenuService;
import com.lyc.mapper.MenuMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
* @author 蜡笔
* @description 针对表【t_menu】的数据库操作Service实现
* @createDate 2023-04-24 21:06:36
*/
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService{
    @Resource
    private MenuMapper menuMapper;

    @Override
    public List<MenuVO> getListMenu(ConditionDTO condition) {

        // 查询当前菜单列表
        List<MenuVO> menuVOList = menuMapper.selectMenuVOList(condition);
        // 当前菜单id列表
        Set<Integer> menuIdList = menuVOList.stream()
                .map(MenuVO::getId)
                .collect(Collectors.toSet());
        return menuVOList.stream().map(menuVO -> {
            Integer parentId = menuVO.getParentId();
            // parentId不在当前菜单id列表，说明为父级菜单id，根据此id作为递归的开始条件节点
            if (!menuIdList.contains(parentId)) {
                menuIdList.add(parentId);
                return recurMenuList(parentId, menuVOList);
            }
            return new ArrayList<MenuVO>();
        }).collect(ArrayList::new, ArrayList::addAll, ArrayList::addAll);
    }

    /**
     * 递归生成菜单列表
     *
     * @param parentId 父菜单id
     * @param menuList 菜单列表
     * @return 菜单列表
     */
    private List<MenuVO> recurMenuList(Integer parentId, List<MenuVO> menuList) {
        return menuList.stream()
                .filter(menuVO -> menuVO.getParentId().equals(parentId))
                .peek(menuVO -> menuVO.setChildren(recurMenuList(menuVO.getId(), menuList)))
                .collect(Collectors.toList());
    }

}




