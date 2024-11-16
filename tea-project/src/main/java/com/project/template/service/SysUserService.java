package com.project.template.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.template.dto.EditPasswordDTO;
import com.project.template.dto.UserLoginDTO;
import com.project.template.dto.UserRegisterDTO;
import com.project.template.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.project.template.vo.SysUserLoginVO;

import java.util.Map;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author 太上老君 * 
 */
public interface SysUserService extends IService<SysUser> {

    Page<SysUser> page(Map<String, Object> query, Integer pageNum, Integer pageSize);

    void register(UserRegisterDTO userRegisterDTO);

    void editPassword(EditPasswordDTO dto);

    SysUserLoginVO login(UserLoginDTO userLoginDTO);

    String resetPassword(Integer id);

    long count();

}
