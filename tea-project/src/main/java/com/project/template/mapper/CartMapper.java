package com.project.template.mapper;

import com.project.template.entity.Cart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 太上老君 */
@Mapper
public interface CartMapper extends BaseMapper<Cart> {
    /**
     * 根据用户id获取购物车商品信息
     *
     * @param uid
     * @return
     */
    List<Cart> queryCartByUid(Long uid);

}
