package com.project.template.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.template.dto.EditPasswordDTO;
import com.project.template.dto.SysUserDTO;
import com.project.template.dto.UserLoginDTO;
import com.project.template.dto.UserRegisterDTO;
import com.project.template.entity.Notice;
import com.project.template.entity.SysUser;
import com.project.template.entity.SysUser;
import com.project.template.enums.StateType;
import com.project.template.exception.CustomException;
import com.project.template.mapper.SysUserMapper;
import com.project.template.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.template.utils.JwtUtils;
import com.project.template.utils.RoleMenuUtils;
import com.project.template.utils.UserThreadLocal;
import com.project.template.utils.Utils;
import com.project.template.vo.SysUserLoginVO;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author 太上老君 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Value("${resetPassword}")
    private String resetPassword;


    @Override
    public boolean save(SysUser entity) {
        check(entity);
        entity.setCreateTime(LocalDateTime.now());
        entity.encryptedPassword(entity.getPassword());
        return super.save(entity);
    }

    @Override
    public boolean updateById(SysUser entity) {
        check(entity);
        SysUser entityOld = getById(entity.getId());
        entity.setSalt(entityOld.getSalt());
        entity.setPassword(entityOld.getPassword());
        return super.updateById(entity);
    }

    private void check(SysUser entity) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUsername, entity.getUsername());
        wrapper.ne(entity.getId() != null, SysUser::getId, entity.getId());
        if (exists(wrapper)) {
            throw new CustomException("用户名已存在");
        }
    }

    @Override
    public boolean removeByIds(Collection<?> list) {
        List<Integer> integerList = (List<Integer>) list;
        return super.removeByIds(integerList);
    }

    @Override
    public Page<SysUser> page(Map<String, Object> query, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(SysUser::getId);
        if (ObjectUtils.isNotEmpty(query.get("username"))) {
            wrapper.like(SysUser::getUsername, query.get("username"));
        }
        if (ObjectUtils.isNotEmpty(query.get("email"))) {
            wrapper.like(SysUser::getEmail, query.get("email"));
        }
        if (ObjectUtils.isNotEmpty(query.get("mobile"))) {
            wrapper.like(SysUser::getMobile, query.get("mobile"));
        }
        if (ObjectUtils.isNotEmpty(query.get("address"))) {
            wrapper.like(SysUser::getAddress, query.get("address"));
        }
        if (ObjectUtils.isNotEmpty(query.get("roleType"))) {
            wrapper.eq(SysUser::getRoleType, query.get("roleType"));
        }
        if (ObjectUtils.isNotEmpty(query.get("gender"))) {
            wrapper.eq(SysUser::getGender, query.get("gender"));
        }
        if (ObjectUtils.isNotEmpty(query.get("state"))) {
            wrapper.eq(SysUser::getState, query.get("state"));
        }
        return page(new Page<>(pageNum, pageSize), wrapper);
    }


    @Override
    public void register(UserRegisterDTO userRegisterDTO) {
        SysUser entity = new SysUser();
        entity.setUsername(userRegisterDTO.getUsername());
        entity.setPassword(userRegisterDTO.getPassword());
        entity.setRoleType("USER");
        //账号默认为启用
        entity.setState(StateType.ENABLE);
        save(entity);
    }

    @Override
    public void editPassword(EditPasswordDTO dto) {
        SysUserDTO user = Utils.getUser();
        SysUser sysUser = getById(user.getId());
        //对比旧密码
        String password = SecureUtil.md5(dto.getPassword() + sysUser.getSalt());
        if (!sysUser.getPassword().equals(password)) {
            throw new CustomException("旧密码错误");
        }
        sysUser.encryptedPassword(dto.getNewPassword());
        updateById(sysUser);
    }

    @Override
    public SysUserLoginVO login(UserLoginDTO userLoginDTO) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUsername, userLoginDTO.getUsername());
        SysUser sysUser = getOne(wrapper);

        if (sysUser == null) {
            throw new CustomException("请检查用户名密码是否正确");
        }

        String password = SecureUtil.md5(userLoginDTO.getPassword() + sysUser.getSalt());
        if (!sysUser.getPassword().equals(password)) {
            throw new CustomException("请检查用户名密码是否正确");
        }

        //检查用户状态是否被禁用
        if (StateType.DISABLED.equals(sysUser.getState())) {
            throw new CustomException("当前用户已经被禁用");
        }

        //生成jwt
        String token = JwtUtils.generateToken(sysUser);
        SysUserLoginVO sysUserLoginVO = new SysUserLoginVO();
        BeanUtil.copyProperties(sysUser, sysUserLoginVO);
        sysUserLoginVO.setToken(token);
        //生成菜单
        sysUserLoginVO.setRoleMenu(RoleMenuUtils.getRoleMenu(sysUserLoginVO.getRoleType()));
        return sysUserLoginVO;
    }

    @Override
    public String resetPassword(Integer id) {
        SysUser sysUser = getById(id);
        sysUser.encryptedPassword(resetPassword);
        super.updateById(sysUser);
        return "重置成功，重置密码为：" + resetPassword;
    }

    @Override
    public long count() {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        return baseMapper.selectCount(wrapper);
    }
}
