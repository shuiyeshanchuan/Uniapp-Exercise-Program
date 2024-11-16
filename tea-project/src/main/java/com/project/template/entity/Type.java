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
@ApiModel(value = "Type对象", description = "")
public class Type extends BaseEntity {



    @ApiModelProperty("类型名称")
    private String typename;

    @ApiModelProperty("类型图片路径")
    private String timageurl;

}
