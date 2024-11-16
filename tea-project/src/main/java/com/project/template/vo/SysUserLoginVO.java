package com.project.template.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class SysUserLoginVO {

    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("个人简介")
    private String content;

    @ApiModelProperty("头像")
    private String img;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("电话")
    private String mobile;

    @ApiModelProperty("token认证")
    private String token;

    @ApiModelProperty("角色类型")
    private String roleType;

    private SysMenuVO[] roleMenu;


}
