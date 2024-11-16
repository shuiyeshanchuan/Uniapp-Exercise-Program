package com.project.template.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.template.entity.Cart;
import com.project.template.entity.Order;
import com.project.template.entity.Productorder;
import com.project.template.mapper.CartMapper;
import com.project.template.mapper.OrderMapper;
import com.project.template.service.OrderService;
import com.project.template.service.ProductorderService;
import com.project.template.utils.Utils;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.ObjectUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 太上老君 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {


    @Override
    public boolean save(Order entity) {
        check(entity);
        return super.save(entity);
    }

    @Override
    public boolean updateById(Order entity) {
        check(entity);
        //entity.setCreateTime(LocalDateTime.now());
        //entity.setUserId(Utils.getUser().getId());
        return super.updateById(entity);
    }

    private void check(Order entity) {
//        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(Order::getName, entity.getName());
//        wrapper.ne(entity.getId() != null, Order::getId, entity.getId());
//        if (exists(wrapper)) {
//            throw new CustomException("名称重复");
//        }
    }


    @Override
    public boolean removeByIds(Collection<?> list) {
        List<Integer> integerList = (List<Integer>) list;
//        list(new LambdaQueryWrapper<Order>().in(Order::getId, integerList)).forEach(test -> {
//            if (true) {
//                throw new CustomException("删除失败");
//            }
//        });
        return super.removeByIds(integerList);
    }

    @Override
    public Page<Order> page(Map<String, Object> query, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Order::getId);
//            if (ObjectUtils.isNotEmpty(query.get("name"))) {
//                wrapper.like(Order::getName, query.get("name"));
//            }
        return page(new Page<>(pageNum, pageSize), wrapper);
    }


}
