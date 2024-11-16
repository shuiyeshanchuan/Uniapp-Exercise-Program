package com.project.template.common;


import lombok.Data;

@Data
public class ProductSalesVO {
    private Integer pid;        // 商品ID
    private String pname; // 商品名称
    private Integer salesCount; // 销售数量
}
