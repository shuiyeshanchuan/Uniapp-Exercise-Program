package com.project.template.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.template.common.PageVO;
import com.project.template.common.Result;
import com.project.template.dto.EditPasswordDTO;
import com.project.template.dto.UserLoginDTO;
import com.project.template.dto.UserRegisterDTO;
import com.project.template.entity.SysUser;
import com.project.template.service.SysUserService;
import com.project.template.utils.Utils;
import com.project.template.vo.SysUserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author 太上老君
 */
@Api(value = "用户模块", tags = "用户模块")

@RestController
@RequestMapping("/sysUser")
public class SysUserController {

    @Resource
    private SysUserService sysUserService;

    /**
     * 统计通知总数
     */
    @ApiOperation(value = "count", notes = "获取系统中所有用户的数量")
    @GetMapping("/count")
    public Result<Long> count() {
        long total = sysUserService.count();
        return new Result<Long>().success(total);
    }

    /**
     * 列表
     *
     * @return
     */
    @ApiOperation(value = "用户列表", notes = "")
    @GetMapping("/list")
    public Result<List<SysUser>> list() {
        return new Result<>().success(sysUserService.list());
    }

    /**
     * 分页查询
     *
     * @param query
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page")
    public Result<PageVO<SysUser>> findPage(@RequestParam Map<String, Object> query,
            @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<SysUser> page = sysUserService.page(query, pageNum, pageSize);
        return new Result<>().success(new PageVO<>(page));

    }

    /**
     * 数据新增
     *
     * @param user
     * @return
     */
    @ApiOperation(value = "数据新增", notes = "数据新增")
    @PostMapping()
    public Result add(@Validated @RequestBody SysUser user) {
        sysUserService.save(user);
        return new Result<>().success();
    }

    /**
     * 数据更新
     *
     * @param user
     * @return
     */
    @ApiOperation(value = "数据更新", notes = "数据更新")
    @PutMapping("/update")
    public Result updateById(@Validated @RequestBody SysUser user) {
        sysUserService.updateById(user);
        return new Result<>().success();
    }

    /**
     * 根据id删除
     *
     * @param ids
     * @return
     */
    @ApiOperation(value = "数据根据id批量删除", notes = "数据根据id批量删除")
    @DeleteMapping()
    public Result delBatch(@RequestBody List<Integer> ids) {
        sysUserService.removeByIds(ids);
        return new Result<>().success();
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping("/getById")
    public Result<SysUserVO> getById(@RequestParam("id") Integer id) {
        SysUser byId = sysUserService.getById(id);
        SysUserVO userVO = new SysUserVO();
        BeanUtils.copyProperties(byId, userVO);
        return new Result<>().success(userVO);
    }

    /**
     * 用户登录
     *
     * @param userLoginDTO
     * @return
     */
    @PostMapping("/login")
    public Result login(@Validated @RequestBody UserLoginDTO userLoginDTO) {
        return new Result<>().success(sysUserService.login(userLoginDTO));
    }

    /**
     * 用户注册
     *
     * @param userRegisterDTO
     * @return
     */
    @PostMapping("/register")
    public Result register(@Validated @RequestBody UserRegisterDTO userRegisterDTO) {
        sysUserService.register(userRegisterDTO);
        return new Result<>().success();
    }

    /**
     * 修改密码
     *
     * @param dto
     * @return
     */
    @PutMapping("/editPassword")
    public Result editPassword(@RequestBody EditPasswordDTO dto) {
        sysUserService.editPassword(dto);
        return new Result<>().success();
    }

    /**
     * 数据更新
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "密码重置", notes = "密码重置")
    @PostMapping("/resetPassword/{id}")
    public Result resetPassword(@PathVariable Integer id) {
        return new Result<>().success(sysUserService.resetPassword(id));
    }

    @GetMapping("/queryUserInfo")
    public Result queryUserInfo() {
        return new Result<>().success(sysUserService.getById(Utils.getUser().getId()));
    }

}
