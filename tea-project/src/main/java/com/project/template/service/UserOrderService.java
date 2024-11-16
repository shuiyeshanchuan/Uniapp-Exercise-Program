package com.project.template.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.template.common.Result;
import com.project.template.entity.UserOrder;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 太上老君 */
public interface UserOrderService extends IService<UserOrder> {
    Page<UserOrder> page(Map<String, Object> query, Integer pageNum, Integer pageSize);

    Page<UserOrder> pageWithProduct(Map<String, Object> query, Integer pageNum, Integer pageSize);

    String addOrder();

//    List<UserOrder> queryOrder(String ostate);
    List<UserOrder> queryOrder();

    long count();

}
