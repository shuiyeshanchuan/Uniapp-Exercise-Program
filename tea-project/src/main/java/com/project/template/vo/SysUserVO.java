package com.project.template.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class SysUserVO {

    @ApiModelProperty("id")

    private Integer id;
    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty("个人简介")
    private String content;

    @ApiModelProperty("头像")
    private String img;

}
