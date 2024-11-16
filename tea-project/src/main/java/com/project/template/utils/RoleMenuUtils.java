package com.project.template.utils;

import com.project.template.enums.RoleType;
import com.project.template.vo.SysMenuVO;

import java.util.Arrays;

public class RoleMenuUtils {

    private final static SysMenuVO[] ADMIN = {
            new SysMenuVO("首页", "/home", "Home", "el-icon-s-home", false, null),
            new SysMenuVO("通知管理", "/noticeManage", "NoticeManage", "el-icon-chat-line-round", false, null),

            new SysMenuVO("用户订单管理", "/userOrderManage", "UserOrderManage", "el-icon-chat-line-round", false, null),
            new SysMenuVO("商品管理", "/productManage", "ProductManage", "el-icon-chat-line-round", false, null),
            new SysMenuVO("分类管理", "/typeManage", "TypeManage", "el-icon-chat-line-round", false, null),
            new SysMenuVO("图片管理", "/imageManage", "ImageManage", "el-icon-chat-line-round", false, null),
            new SysMenuVO("订单详情", "/ProductOrderManage", "ProductOrderManage", "el-icon-chat-line-round", false, null),

            new SysMenuVO("系统管理", "", "", "el-icon-setting", true,
                    Arrays.asList(
                            new SysMenuVO("角色管理", "/sysRoleManage", "SysRoleManage", "el-icon-user", false, null),
                            new SysMenuVO("用户信息", "/sysUserManage", "SysUserManage", "el-icon-user-solid", false, null)
                            )
            )
    };

    private final static SysMenuVO[] USER = {

    };

    public static SysMenuVO[] getRoleMenu(String role) {
        //管理员
        if (RoleType.ADMIN.getKey().equals(role)) {
            return ADMIN;
        }

        return null;
    }
}
