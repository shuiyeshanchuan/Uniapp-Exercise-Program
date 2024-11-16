package com.project.template.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.template.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 太上老君 * 
 */
public interface SysRoleService extends IService<SysRole> {

    Page<SysRole> page(Map<String, Object> query, Integer pageNum, Integer pageSize);
}
