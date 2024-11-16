package com.project.template.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.template.common.ProductSalesVO;
import com.project.template.entity.Productorder;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;
/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 太上老君 *
 */
    public interface ProductorderService extends IService<Productorder> {
        Page<Productorder> page(Map<String, Object> query, Integer pageNum, Integer pageSize);

        List<ProductSalesVO> getTopFourProducts();

    }
