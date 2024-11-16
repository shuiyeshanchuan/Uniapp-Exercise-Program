package com.project.template.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysMenuVO {

    private String title;

    private String path;

    private String componentPath;

    private String iconClass;

    private Boolean isPage;

    private List<SysMenuVO> list;



}
