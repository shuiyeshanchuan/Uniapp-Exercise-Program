package com.project.template.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.template.entity.SysRole;
import com.project.template.exception.CustomException;
import com.project.template.mapper.SysRoleMapper;
import com.project.template.service.SysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 太上老君 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Override
    public boolean save(SysRole entity) {
        check(entity);
        return super.save(entity);
    }

    @Override
    public boolean updateById(SysRole entity) {
        check(entity);
        return super.updateById(entity);
    }


    private void check(SysRole entity) {
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRole::getName, entity.getName());
        wrapper.ne(entity.getId() != null, SysRole::getId, entity.getId());
        if (count(wrapper) > 0) {
            throw new CustomException("名称重复");
        }
    }


    @Override
    public boolean removeByIds(Collection<?> list) {
        List<Integer> integerList = (List<Integer>) list;
        return super.removeByIds(integerList);
    }

    @Override
    public Page<SysRole> page(Map<String, Object> query, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(SysRole::getId);
        if (ObjectUtils.isNotEmpty(query.get("name"))) {
            wrapper.like(SysRole::getName, query.get("name"));
        }
        return page(new Page<>(pageNum, pageSize), wrapper);
    }
}
