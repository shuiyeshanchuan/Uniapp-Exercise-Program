package com.project.template.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.template.common.Result;
import com.project.template.entity.Cart;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 太上老君 */
public interface CartService extends IService<Cart> {
    Page<Cart> page(Map<String, Object> query, Integer pageNum, Integer pageSize);

    Result addCart(Cart cart);


    /**
     * 根据用户token获取购物车信息
     *
     * @param uid
     * @rCartn
     */
    List<Cart> queryCartInfo(Long uid);

    void clearUserCart(Long userId);
}
