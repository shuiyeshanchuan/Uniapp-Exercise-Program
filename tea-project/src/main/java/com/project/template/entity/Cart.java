package com.project.template.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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
@ApiModel(value = "Cart对象", description = "")
public class Cart extends BaseEntity {

    @ApiModelProperty("关联用户id")
    private Long uid;

    @ApiModelProperty("关联产品id")
    private Long pid;

    @ApiModelProperty("产品数量")
    private Integer number;

    private Double tprice;

    @TableField(exist = false)
    private Product product;

}
