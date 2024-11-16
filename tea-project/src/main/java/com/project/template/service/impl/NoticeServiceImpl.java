package com.project.template.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.template.entity.Notice;
import com.project.template.exception.CustomException;
import com.project.template.mapper.NoticeMapper;
import com.project.template.service.NoticeService;
import com.project.template.utils.Utils;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.ObjectUtils;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户ID 服务实现类
 * </p>
 *
 * @author 太上老君
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {

    @Override
    public boolean save(Notice entity) {
        check(entity);
        entity.setCreateTime(LocalDateTime.now());
        entity.setUserId(Utils.getUser().getId());
        return super.save(entity);
    }

    @Override
    public boolean updateById(Notice entity) {
        check(entity);
        return super.updateById(entity);
    }

    private void check(Notice entity) {
        LambdaQueryWrapper<Notice> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Notice::getName, entity.getName());
        wrapper.ne(entity.getId() != null, Notice::getId, entity.getId());
        if (exists(wrapper)) {
            throw new CustomException("名称重复");
        }
    }

    @Override
    public boolean removeByIds(Collection<?> list) {
        List<Integer> integerList = (List<Integer>) list;
        return super.removeByIds(integerList);
    }

    @Override
    public Page<Notice> page(Map<String, Object> query, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Notice> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Notice::getId);
        if (ObjectUtils.isNotEmpty(query.get("name"))) {
            wrapper.like(Notice::getName, query.get("name"));
        }
        if (ObjectUtils.isNotEmpty(query.get("content"))) {
            wrapper.like(Notice::getContent, query.get("content"));
        }
        return page(new Page<>(pageNum, pageSize), wrapper);
    }

    @Override
    public long count() {
        LambdaQueryWrapper<Notice> wrapper = new LambdaQueryWrapper<>();

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
