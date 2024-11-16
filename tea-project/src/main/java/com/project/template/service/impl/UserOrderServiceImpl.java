package com.project.template.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.template.entity.*;
import com.project.template.mapper.CartMapper;
import com.project.template.mapper.UserOrderMapper;
import com.project.template.service.ProductService;
import com.project.template.service.ProductorderService;
import com.project.template.service.UserOrderService;
import com.project.template.utils.Utils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 太上老君 */
@Service
public class UserOrderServiceImpl extends ServiceImpl<UserOrderMapper, UserOrder> implements UserOrderService {
    @Resource
    private CartMapper cartMapper;
    @Resource
    private ProductorderService productorderService;
    @Resource
    private UserOrderMapper userOrderMapper;

    @Override
    public boolean save(UserOrder entity) {
        check(entity);
        return super.save(entity);
    }

    @Override
    public boolean updateById(UserOrder entity) {
        check(entity);
        // entity.setCreateTime(LocalDateTime.now());
        // entity.setUserId(Utils.getUser().getId());
        return super.updateById(entity);
    }

    private void check(UserOrder entity) {
        // LambdaQueryWrapper<UserOrder> wrapper = new LambdaQueryWrapper<>();
        // wrapper.eq(UserOrder::getName, entity.getName());
        // wrapper.ne(entity.getId() != null, UserOrder::getId, entity.getId());
        // if (exists(wrapper)) {
        // throw new CustomException("名称重复");
        // }
    }

    @Override
    public boolean removeByIds(Collection<?> list) {
        List<Integer> integerList = (List<Integer>) list;
        // list(new LambdaQueryWrapper<UserOrder>().in(UserOrder::getId,
        // integerList)).forEach(test -> {
        // if (true) {
        // throw new CustomException("删除失败");
        // }
        // });
        return super.removeByIds(integerList);
    }

    @Override
    public Page<UserOrder> page(Map<String, Object> query, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<UserOrder> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(UserOrder::getId);
        // if (ObjectUtils.isNotEmpty(query.get("name"))) {
        // wrapper.like(UserOrder::getName, query.get("name"));
        // }
        return page(new Page<>(pageNum, pageSize), wrapper);
    }

    @Override
    public Page<UserOrder> pageWithProduct(Map<String, Object> query, Integer pageNum, Integer pageSize) {
        Page<UserOrder> page = new Page<>(pageNum, pageSize);

        // 使用MyBatis Plus的分页查询，添加商品信息的关联查询
        return baseMapper.selectOrdersWithProduct(page, query);
    }

    @Override
    public String addOrder() {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        // 2.根据用户id，获取目前购物车信息
        LambdaQueryWrapper<Cart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Cart::getUid, Utils.getUser().getId());
        List<Cart> carts = cartMapper.selectList(wrapper);
        // 3.生成订单，准备订单信息
        double totlePrice = createOrder(carts, Long.valueOf(Utils.getUser().getId()), uuid);
        // 4.生成产品、订单中间表 通过流水号进行关联
        createProductOrder(carts, uuid);
        // 5.清除购物车数据 根据用户id删除
        LambdaQueryWrapper<Cart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Cart::getUid, Utils.getUser().getId());

        cartMapper.selectList(queryWrapper).forEach(v -> {
            cartMapper.deleteById(v.getId());
        });
        return "true";
    }

    // @Override
    // public List<UserOrder> queryOrder() {
    // return List.of();
    // }

    @Resource
    private ProductService productService;

    @Override
    public List<UserOrder> queryOrder() {
        // 1. 查询当前用户的所有订单
        LambdaQueryWrapper<UserOrder> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserOrder::getUid, Utils.getUser().getId())
                .orderByDesc(UserOrder::getOtime);

        List<UserOrder> orders = list(wrapper);

        // 2. 查询每个订单关联的商品信息
        orders.forEach(order -> {
            // 只查询 productorder 表中实际存在的字段
            LambdaQueryWrapper<Productorder> productOrderWrapper = new LambdaQueryWrapper<>();
            productOrderWrapper.select(Productorder::getId,
                    Productorder::getOid,
                    Productorder::getPid,
                    Productorder::getNumber,
                    Productorder::getPrice)
                    .eq(Productorder::getOid, order.getOnumber());

            // 获取商品信息并设置到订单中
            List<Product> productList = new ArrayList<>();
            productorderService.list(productOrderWrapper).forEach(po -> {
                Product product = productService.getById(po.getPid());
                if (product != null) {
                    productList.add(product);
                }
            });

            order.setProduct(productList);
        });

        return orders;
    }

    @Override
    public long count() {
        LambdaQueryWrapper<UserOrder> wrapper = new LambdaQueryWrapper<>();

        // 可以添加特定的统计条件，例如：
        // 只统计未删除的通知
        // wrapper.eq(Notice::getIsDeleted, false);

        // 只统计特定时间段的通知
        // wrapper.ge(Notice::getCreateTime, startTime);
        // wrapper.le(Notice::getCreateTime, endTime);

        // 只统计特定类型的通知
        // wrapper.eq(Notice::getType, noticeType);

        return baseMapper.selectCount(wrapper);
    }

    /**
     * 生产订单
     * 
     * @param carts
     * @param uid
     */
    public double createOrder(List<Cart> carts, Long uid, String uuid) {
        UserOrder order = new UserOrder();
        order.setOtype(0); // 自取
        order.setUid(uid);
        order.setPaystate(1);// 已支付
        order.setOstate(0); // 预定中
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        order.setOtime(sdf.format(new Date()));
        double totlePrice = 0.0;
        for (int i = 0; i < carts.size(); i++) {
            Cart cart = carts.get(i);
            totlePrice += cart.getNumber() * cart.getTprice();
            System.out.println("总价格未：" + totlePrice);
        }
        order.setOnumber(uuid); // 订单流水号
        order.setOprice(totlePrice); // 该订单的总价格

        save(order);
        return totlePrice;
    }

    /**
     * 生成产品、订单中间表
     * 
     * @param carts
     */
    public void createProductOrder(List<Cart> carts, String uuid) {
        List<Productorder> list = new ArrayList<>();
        for (int i = 0; i < carts.size(); i++) {
            Cart cart = carts.get(i);
            Productorder po = new Productorder();
            po.setNumber(cart.getNumber());
            po.setOid(uuid); // 需要修改
            po.setPid(cart.getPid());
            po.setPrice(cart.getTprice());
            list.add(po);
        }
        // 插入数据(批量)
        productorderService.saveBatch(list);
    }

}
