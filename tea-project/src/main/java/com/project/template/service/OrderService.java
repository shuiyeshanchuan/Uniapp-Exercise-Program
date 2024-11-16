package com.project.template.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.template.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;
/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 太上老君 *
 */
    public interface OrderService extends IService<Order> {
        Page<Order> page(Map<String, Object> query, Integer pageNum, Integer pageSize);



}
