package com.project.template.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.template.common.Result;
import com.project.template.entity.Cart;
import com.project.template.exception.CustomException;
import com.project.template.mapper.CartMapper;
import com.project.template.service.CartService;
import com.project.template.utils.Utils;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.ObjectUtils;

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
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements CartService {

    @Resource
    private CartMapper cartMapper;

    @Override
    public boolean save(Cart entity) {
        check(entity);
        return super.save(entity);
    }

    @Override
    public boolean updateById(Cart entity) {
        check(entity);
        //entity.setCreateTime(LocalDateTime.now());
        //entity.setUserId(Utils.getUser().getId());
        return super.updateById(entity);
    }

    private void check(Cart entity) {
//        LambdaQueryWrapper<Cart> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(Cart::getName, entity.getName());
//        wrapper.ne(entity.getId() != null, Cart::getId, entity.getId());
//        if (exists(wrapper)) {
//            throw new CustomException("名称重复");
//        }
    }


    @Override
    public boolean removeByIds(Collection<?> list) {
        List<Integer> integerList = (List<Integer>) list;
//        list(new LambdaQueryWrapper<Cart>().in(Cart::getId, integerList)).forEach(test -> {
//            if (true) {
//                throw new CustomException("删除失败");
//            }
//        });
        return super.removeByIds(integerList);
    }

    @Override
    public Page<Cart> page(Map<String, Object> query, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Cart> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Cart::getId);
//            if (ObjectUtils.isNotEmpty(query.get("name"))) {
//                wrapper.like(Cart::getName, query.get("name"));
//            }
        return page(new Page<>(pageNum, pageSize), wrapper);
    }

    @Override
    public Result addCart(Cart cart) {
        Long id = Long.valueOf(Utils.getUser().getId());
        //2.跟新cart的uid
        cart.setUid(id);

        //3.校验是否已经存在与购物车  根据传入的商品id 和用户id 进行查询(购物车表)

        LambdaQueryWrapper<Cart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Cart::getUid,id);
        wrapper.eq(Cart::getPid,cart.getPid());

        Long count =count(wrapper);

        if(count>=1){
            throw new CustomException("购物车已存在");
        }
        save(cart);
        return null;
    }

    /**
     * 根据用户token获取购物车信息
     *
     * @param uid
     * @rCartn
     */
    @Override
    public List<Cart> queryCartInfo(Long uid) {
        return cartMapper.queryCartByUid(uid);
    }

    @Override
    public void clearUserCart(Long userId) {
        // 创建删除条件
        LambdaQueryWrapper<Cart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Cart::getUid,userId);
        // 执行删除操作
        this.remove(wrapper);

    }
}
