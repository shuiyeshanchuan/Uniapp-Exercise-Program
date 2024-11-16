package com.project.template.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum StateType {

    DISABLED(0, "禁用"),
    ENABLE(1, "启用");

    /**
     * 存到数据库的值
     */
    @EnumValue
    private Integer key;

    /**
     * 前端显示的值
     */
    @JsonValue
    private String name;


    StateType(Integer key, String name) {
        this.key = key;
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }


}
