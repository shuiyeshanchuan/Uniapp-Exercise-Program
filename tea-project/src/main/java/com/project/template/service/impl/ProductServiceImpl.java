package com.project.template.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.template.entity.Notice;
import com.project.template.entity.Product;
import com.project.template.mapper.ProductMapper;
    import com.project.template.service.ProductService;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.ObjectUtils;
import java.util.Collection;
import java.util.List;
import java.util.Map;
/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 太上老君 *
 */
@Service
        public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService{

        @Override
        public boolean save(Product entity) {
        check(entity);
        return super.save(entity);
        }

        @Override
        public boolean updateById(Product entity) {
        check(entity);
        //entity.setCreateTime(LocalDateTime.now());
        //entity.setUserId(Utils.getUser().getId());
        return super.updateById(entity);
        }

        private void check(Product entity) {
//        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(Product::getName, entity.getName());
//        wrapper.ne(entity.getId() != null, Product::getId, entity.getId());
//        if (exists(wrapper)) {
//            throw new CustomException("名称重复");
//        }
        }


        @Override
        public boolean removeByIds(Collection<?> list) {
        List<Integer> integerList = (List<Integer>) list;
//        list(new LambdaQueryWrapper<Product>().in(Product::getId, integerList)).forEach(test -> {
//            if (true) {
//                throw new CustomException("删除失败");
//            }
//        });
        return super.removeByIds(integerList);
        }

        @Override
        public Page<Product> page(Map<String, Object> query, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Product::getId);
//            if (ObjectUtils.isNotEmpty(query.get("name"))) {
//                wrapper.like(Product::getName, query.get("name"));
//            }
        return page(new Page<>(pageNum, pageSize), wrapper);
        }

        @Override
        public long count() {
                LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();

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
}
