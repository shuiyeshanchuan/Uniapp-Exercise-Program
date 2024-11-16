package com.project.template.common;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.util.List;

@Data
public class PageVO<T> {
    List<T> list; // 存储分页数据的列表
    long total; // 总记录数

    public PageVO(Page<T> page) {
        this.setList(page.getRecords()); // 设置当前页的数据列表
        this.setTotal(page.getTotal()); // 设置总记录数
    }
}
