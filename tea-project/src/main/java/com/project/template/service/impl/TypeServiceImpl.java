package com.project.template.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.template.entity.Type;
import com.project.template.mapper.TypeMapper;
    import com.project.template.service.TypeService;
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
        public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type> implements TypeService{

        @Override
        public boolean save(Type entity) {
        check(entity);
        return super.save(entity);
        }

        @Override
        public boolean updateById(Type entity) {
        check(entity);
        //entity.setCreateTime(LocalDateTime.now());
        //entity.setUserId(Utils.getUser().getId());
        return super.updateById(entity);
        }

        private void check(Type entity) {
//        LambdaQueryWrapper<Type> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(Type::getName, entity.getName());
//        wrapper.ne(entity.getId() != null, Type::getId, entity.getId());
//        if (exists(wrapper)) {
//            throw new CustomException("名称重复");
//        }
        }


        @Override
        public boolean removeByIds(Collection<?> list) {
        List<Integer> integerList = (List<Integer>) list;
//        list(new LambdaQueryWrapper<Type>().in(Type::getId, integerList)).forEach(test -> {
//            if (true) {
//                throw new CustomException("删除失败");
//            }
//        });
        return super.removeByIds(integerList);
        }

        @Override
        public Page<Type> page(Map<String, Object> query, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Type> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Type::getId);
//            if (ObjectUtils.isNotEmpty(query.get("name"))) {
//                wrapper.like(Type::getName, query.get("name"));
//            }
        return page(new Page<>(pageNum, pageSize), wrapper);
        }
}
