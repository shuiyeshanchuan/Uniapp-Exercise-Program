package com.project.template.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.template.entity.Productorder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 太上老君 *
 */
@Mapper
public interface ProductorderMapper extends BaseMapper<Productorder> {
        Page<Productorder> selectPage(Page<Productorder> page, @Param("params") Map<String, Object> params);
        }
