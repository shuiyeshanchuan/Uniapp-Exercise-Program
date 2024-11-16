package com.project.template.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.template.entity.UserOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 太上老君 */
@Mapper
public interface UserOrderMapper extends BaseMapper<UserOrder> {


    Page<UserOrder> selectOrdersWithProduct(Page<UserOrder> page, Map<String, Object> query);
}
