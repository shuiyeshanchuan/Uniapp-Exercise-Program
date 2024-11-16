package com.project.template.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.template.entity.Notice;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 用户ID 服务类
 * </p>
 *
 * @author 太上老君 *
 */
public interface NoticeService extends IService<Notice> {
    Page<Notice> page(Map<String, Object> query, Integer pageNum, Integer pageSize);

    long count();
}
