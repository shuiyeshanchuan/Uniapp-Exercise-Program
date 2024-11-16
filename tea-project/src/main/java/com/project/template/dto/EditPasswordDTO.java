package com.project.template.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 功能描述：
 * 作者：B站：程序员瑞哥
 * 日期：2024/1/24 17:18
 */
@Data
public class EditPasswordDTO {

    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("新密码")
    private String newPassword;

}
