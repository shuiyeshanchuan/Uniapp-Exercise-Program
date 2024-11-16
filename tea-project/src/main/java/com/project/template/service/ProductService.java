package com.project.template.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.template.entity.Product;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;
/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 太上老君 *
 */
    public interface ProductService extends IService<Product> {
        Page<Product> page(Map<String, Object> query, Integer pageNum, Integer pageSize);
    long count();

}
