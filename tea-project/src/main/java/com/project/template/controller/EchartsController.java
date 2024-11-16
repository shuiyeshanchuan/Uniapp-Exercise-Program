package com.project.template.controller;

import cn.hutool.core.collection.CollUtil;
import com.project.template.common.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/echarts")
public class EchartsController {

    @GetMapping("/example")
    public Result get() {
        Map<String, Object> map = new HashMap<>();
        map.put("x", CollUtil.newArrayList("最牛逼", "一般牛逼", "牛逼", "一般", "非常一般"));
        map.put("y", CollUtil.newArrayList('0'));
        Result result = new Result();
        return result.success(map);
    }
}
