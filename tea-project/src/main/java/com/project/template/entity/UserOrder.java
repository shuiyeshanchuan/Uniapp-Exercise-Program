package com.project.template.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.project.template.entity.BaseEntity;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 *
 * </p>
 *
 * @author 太上老君 */
@Getter
@Setter
@TableName("user_order")
@ApiModel(value = "UserOrder对象", description = "")
public class UserOrder extends BaseEntity {


    @ApiModelProperty("1：外卖    0：自取")
    private Integer otype;

    @ApiModelProperty("订单流水号")
    private String onumber;

    @ApiModelProperty("关联用户表id")
    private Long uid;

    @ApiModelProperty("订单价格")
    private Double oprice;

    @ApiModelProperty("支付状态 0:未支付   1：已经支付")
    private Integer paystate;

    @ApiModelProperty("支付时间")
    private String otime;

    @ApiModelProperty("订单状态：0：预定中（未收获） 1：已收货 2：退订 3：禁用")
    private Integer ostate;

    @TableField(exist = false)
    private List<Product> product;

    @ApiModelProperty("关联商品id")
    private Integer pid;

    @TableField(exist = false)
    private String pn;

}
