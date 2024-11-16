package com.project.template.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SysUserDTO {

    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("角色类型")
    private String roleType;

    public boolean isAdmin()
    {
        return "ADMIN".equals(roleType);
    }

}
