package com.lyc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyc.model.dto.ConditionDTO;
import com.lyc.model.po.Role;
import com.lyc.common.PageResult;
import com.lyc.model.vo.RoleVO;
import com.lyc.service.RoleService;
import com.lyc.mapper.RoleMapper;
import com.lyc.utils.PageUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author 蜡笔
* @description 针对表【t_role】的数据库操作Service实现
* @createDate 2023-04-24 21:06:36
*/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService{

    @Resource
    private RoleMapper roleMapper;

    @Override
    public PageResult<RoleVO> listRoleVO(ConditionDTO condition) {
        // 查询角色数量
        Long count = roleMapper.selectCountRoleVO(condition);
        if (count == 0) {
            return new PageResult<>();
        }
        // 分页查询角色列表
        List<RoleVO> roleVOList = roleMapper.selectRoleVOList(PageUtils.getLimit(), PageUtils.getSize(), condition);
        return new PageResult<>(roleVOList, count);
    }
}




