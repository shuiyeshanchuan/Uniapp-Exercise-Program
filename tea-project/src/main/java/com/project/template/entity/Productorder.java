package com.project.template.entity;

import com.project.template.entity.BaseEntity;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author 太上老君 *
 */
@Getter
@Setter
@ApiModel(value = "Productorder对象", description = "")
public class Productorder extends BaseEntity {



    @ApiModelProperty("关联订单流水号")
    private String oid;

    @ApiModelProperty("关联产品id")
    private Long pid;

    @ApiModelProperty("购买产品数量")
    private Integer number;

    @ApiModelProperty("产品单价")
    private Double price;

    // 新增产品信息
    private String pname;        // 产品名称
    private String pimageurl;    // 产品图片



        }
