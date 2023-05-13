package com.lyc.mapper;

import com.lyc.model.dto.ConditionDTO;
import com.lyc.model.po.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyc.model.vo.RoleVO;
import com.lyc.model.vo.UserRoleVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 蜡笔
* @description 针对表【t_role】的数据库操作Mapper
* @createDate 2023-04-24 21:06:36
* @Entity com.lyc.po.Role
*/
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 根据id查询用户角色
     * @param userId 用户id
     * @return 角色集合
     */
    List<String> selectRoleListByUserId(@Param("userId") Object userId);

    List<UserRoleVO> selectUserRoleList();

    Long selectCountRoleVO(ConditionDTO condition);

    List<RoleVO> selectRoleVOList(Long limit, Long size, ConditionDTO condition);
}




