package com.project.template.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.template.entity.BaseEntity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.project.template.service.SysUserService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 用户ID
 * </p>
 *
 * @author 太上老君 */
@Getter
@Setter
@ApiModel(value = "Notice对象", description = "用户ID")
public class Notice extends BaseEntity {

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("用户ID")
    private Integer userId;


    @TableField(exist = false)
    private String userName;


    public void selectUserName(SysUserService sysUserService) {
        SysUser sysUser = sysUserService.getById(userId);
        if (sysUser == null) {
            return;
        }
        userName = sysUser.getUsername();
    }
}
