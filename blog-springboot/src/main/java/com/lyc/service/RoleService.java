package com.lyc.service;

import com.lyc.model.dto.ConditionDTO;
import com.lyc.model.po.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyc.model.common.PageResult;
import com.lyc.model.vo.RoleVO;

/**
* @author 蜡笔
* @description 针对表【t_role】的数据库操作Service
* @createDate 2023-04-24 21:06:36
*/
public interface RoleService extends IService<Role> {

    PageResult<RoleVO> listRoleVO(ConditionDTO condition);
}
