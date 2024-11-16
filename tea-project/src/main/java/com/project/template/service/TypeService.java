package com.project.template.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.template.entity.Type;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;
/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 太上老君 *
 */
    public interface TypeService extends IService<Type> {
        Page<Type> page(Map<String, Object> query, Integer pageNum, Integer pageSize);
    }
