package com.lyc.mapper;

import com.lyc.model.dto.ConditionDTO;
import com.lyc.model.po.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyc.model.vo.MenuVO;
import com.lyc.model.vo.UserMenuVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 蜡笔
* @description 针对表【t_menu】的数据库操作Mapper
* @createDate 2023-04-24 21:06:36
* @Entity com.lyc.po.Menu
*/
public interface MenuMapper extends BaseMapper<Menu> {

    List<String> selectPermissionByRoleId(String roleId);

    List<UserMenuVO> selectMenuByUserId(int loginIdAsInt);

    List<MenuVO> selectMenuVOList(@Param("condition") ConditionDTO condition);

}




