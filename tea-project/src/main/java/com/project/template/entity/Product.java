package com.project.template.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.project.template.entity.BaseEntity;

import java.io.Serializable;

import com.project.template.service.SysUserService;
import com.project.template.service.TypeService;
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
@ApiModel(value = "Product对象", description = "")
public class Product extends BaseEntity {



    @ApiModelProperty("产品名称")
    private String pname;

    @ApiModelProperty("关联类型表id")
    private Long typeid;

    @ApiModelProperty("产品价格")
    private Double tprice;

    @ApiModelProperty("产品介绍")
    private String tintroduce;

    @ApiModelProperty("图片路径")
    private String pimageurl;


    @TableField(exist = false)
    private String typeName;

    public void selectTypeName(TypeService typeService) {
        Type service = typeService.getById(typeid);
        if (service == null) {
            return;
        }
        typeName = service.getTypename();
    }


}
