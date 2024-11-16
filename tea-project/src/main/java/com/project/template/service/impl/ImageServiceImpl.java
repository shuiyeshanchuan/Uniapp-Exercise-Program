package com.project.template.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.template.entity.Image;
import com.project.template.mapper.ImageMapper;
    import com.project.template.service.ImageService;
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
        public class ImageServiceImpl extends ServiceImpl<ImageMapper, Image> implements ImageService{

        @Override
        public boolean save(Image entity) {
        check(entity);
        return super.save(entity);
        }

        @Override
        public boolean updateById(Image entity) {
        check(entity);
        //entity.setCreateTime(LocalDateTime.now());
        //entity.setUserId(Utils.getUser().getId());
        return super.updateById(entity);
        }

        private void check(Image entity) {
//        LambdaQueryWrapper<Image> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(Image::getName, entity.getName());
//        wrapper.ne(entity.getId() != null, Image::getId, entity.getId());
//        if (exists(wrapper)) {
//            throw new CustomException("名称重复");
//        }
        }


        @Override
        public boolean removeByIds(Collection<?> list) {
        List<Integer> integerList = (List<Integer>) list;
//        list(new LambdaQueryWrapper<Image>().in(Image::getId, integerList)).forEach(test -> {
//            if (true) {
//                throw new CustomException("删除失败");
//            }
//        });
        return super.removeByIds(integerList);
        }

        @Override
        public Page<Image> page(Map<String, Object> query, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Image> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Image::getId);
            if (ObjectUtils.isNotEmpty(query.get("name"))) {
//                wrapper.like(Image::getName, query.get("name"));
            }
        return page(new Page<>(pageNum, pageSize), wrapper);
        }
}
