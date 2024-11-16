package com.project.template.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.template.common.ProductSalesVO;
import com.project.template.entity.Product;
import com.project.template.entity.Productorder;
import com.project.template.mapper.ProductorderMapper;
import com.project.template.service.ProductService;
import com.project.template.service.ProductorderService;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 太上老君
 */
@Service
public class ProductorderServiceImpl extends ServiceImpl<ProductorderMapper, Productorder>
        implements ProductorderService {

    @Autowired
    private ProductService productService;

    @Override
    public boolean save(Productorder entity) {
        check(entity);
        return super.save(entity);
    }

    @Override
    public boolean updateById(Productorder entity) {
        check(entity);
        // entity.setCreateTime(LocalDateTime.now());
        // entity.setUserId(Utils.getUser().getId());
        return super.updateById(entity);
    }

    private void check(Productorder entity) {
        // LambdaQueryWrapper<Productorder> wrapper = new LambdaQueryWrapper<>();
        // wrapper.eq(Productorder::getName, entity.getName());
        // wrapper.ne(entity.getId() != null, Productorder::getId, entity.getId());
        // if (exists(wrapper)) {
        // throw new CustomException("名称重复");
        // }
    }

    @Override
    public boolean removeByIds(Collection<?> list) {
        List<Integer> integerList = (List<Integer>) list;
        // list(new LambdaQueryWrapper<Productorder>().in(Productorder::getId,
        // integerList)).forEach(test -> {
        // if (true) {
        // throw new CustomException("删除失败");
        // }
        // });
        return super.removeByIds(integerList);
    }

    // @Override
    // public Page<Productorder> page(Map<String, Object> query, Integer pageNum,
    // Integer pageSize) {
    // LambdaQueryWrapper<Productorder> wrapper = new LambdaQueryWrapper<>();
    // wrapper.orderByDesc(Productorder::getId);
    //// if (ObjectUtils.isNotEmpty(query.get("name"))) {
    //// wrapper.like(Productorder::getName, query.get("name"));
    //// }
    // return page(new Page<>(pageNum, pageSize), wrapper);
    // }

    @Override
    public Page<Productorder> page(Map<String, Object> query, Integer pageNum, Integer pageSize) {
        Page<Productorder> page = new Page<>(pageNum, pageSize);
        return baseMapper.selectPage(page, query);
    }

    @Override
    public List<ProductSalesVO> getTopFourProducts() {
        // 创建查询构造器
        LambdaQueryWrapper<Productorder> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Productorder::getPid)
                .groupBy(Productorder::getPid)
                .orderByDesc(Productorder::getPid);

        // 执行查询并处理结果
        List<Map<String, Object>> result = baseMapper.selectMaps(wrapper);

        // 转换为VO对象
        List<ProductSalesVO> salesVOList = new ArrayList<>();
        for (Map<String, Object> map : result) {
            ProductSalesVO vo = new ProductSalesVO();
            // 修改这里的类型转换
            Long pidLong = (Long) map.get("pid");
            vo.setPid(pidLong.intValue()); // 使用 intValue() 安全转换

            // 获取商品名称
            Product product = productService.getById(pidLong); // 使用 Long 类型的 pid
            if (product != null) {
                vo.setPname(product.getPname());
            }

            // 获取销售数量
            LambdaQueryWrapper<Productorder> countWrapper = new LambdaQueryWrapper<>();
            countWrapper.eq(Productorder::getPid, pidLong);
            Long count = baseMapper.selectCount(countWrapper);
            vo.setSalesCount(count.intValue()); // 使用 intValue() 安全转换

            salesVOList.add(vo);
        }

        // 按销售数量排序并只返回前4个
        return salesVOList.stream()
                .sorted((a, b) -> b.getSalesCount().compareTo(a.getSalesCount()))
                .limit(4)
                .collect(Collectors.toList());
    }

}
