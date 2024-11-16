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
 * @author 太上老君 */
@Getter
@Setter
@ApiModel(value = "Image对象", description = "")
public class Image extends BaseEntity {


    @ApiModelProperty("图片标题")
    private String ititle;

    @ApiModelProperty("图片路径")
    private String iurl;

    @ApiModelProperty("图片类型 0：注册页面轮播图  1：产品轮播图图片等等 ")
    private String itype;

    @ApiModelProperty("描述  如：注册页面轮播图")
    private String imsg;

    @ApiModelProperty("1:可用    0：禁用")
    private Integer istate;
}
