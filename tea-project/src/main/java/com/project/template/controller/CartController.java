package com.project.template.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.template.common.PageVO;
import com.project.template.common.Result;
import com.project.template.entity.Cart;
import com.project.template.service.CartService;
import com.project.template.utils.Utils;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 太上老君 */
@RestController
@RequestMapping("/cart")
public class CartController {
    @Resource
    private CartService cartService;

    /**
     * 列表
     */
    @ApiOperation(value = "列表", notes = "列表")
    @GetMapping("/list")
    public Result<List<Cart>> list() {
        return new Result<>().success(cartService.list());
    }

    /**
     * 分页查询
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page")
    public Result<PageVO<Cart>> findPage(@RequestParam Map<String, Object> query, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<Cart> page = cartService.page(query, pageNum, pageSize);
        return new Result<>().success(new PageVO<>(page));
    }

    /**
     * 数据新增
     */
    @ApiOperation(value = "数据新增", notes = "数据新增")
    @PostMapping()
    public Result add(@Validated @RequestBody Cart entity) {
        cartService.save(entity);
        return new Result<>().success();
    }

    /**
     * 数据更新
     */
    @ApiOperation(value = "数据更新", notes = "数据更新")
    @PutMapping()
    public Result update(@Validated @RequestBody Cart entity) {
        cartService.updateById(entity);
        return new Result<>().success();
    }

    /**
     * 根据id删除
     */
    @ApiOperation(value = "数据根据id批量删除", notes = "数据根据id批量删除")
    @DeleteMapping()
    public Result delBatch(@RequestBody List<Integer> ids) {
        cartService.removeByIds(ids);
        return new Result<>().success();
    }

    /**
     * 根据id查询
     */
    @ApiOperation(value = "根据id查询", notes = "根据id查询")
    @GetMapping("/{id}")
    public Result<Cart> getById(@PathVariable("id") Integer id) {
        Cart entity = cartService.getById(id);
        return new Result<>().success(entity);
    }

    @GetMapping("queryCartInfo")
    @ApiOperation(value = "queryCartInfo", notes = "获取用户的购物车信息")
    public Result<Cart> queryCartInfo() {

        return new Result<>().success(cartService.queryCartInfo(Long.valueOf(Utils.getUser().getId())));
    }


    @GetMapping("changeNumber")
    @ApiOperation(value = "changeNumber", notes = "改变数量")
    public Result changeNumber(String cmd, Integer id) {

        Cart cart = cartService.getById(id);
        if ("add".equals(cmd)) {
            cart.setNumber(cart.getNumber() + 1);
        } else {
            int number = cart.getNumber() - 1;
            if (number <= 0) {
                return new Result<>().success();
            }
            cart.setNumber(number);
        }
        cartService.updateById(cart);
        return new Result<>().success();
    }


    @GetMapping("addCart")
    public Result addCart(Cart cart) {
        System.out.println("cart = " + cart);
        return new Result<>().success(cartService.addCart(cart));
    }

//    清空购物车
@ApiOperation(value = "清空购物车", notes = "清空当前用户的购物车")
@PostMapping("/clear")
public Result clearCart() {
    // 获取当前用户ID
    Long userId = Long.valueOf(Utils.getUser().getId());
    // 调用service层清空购物车
    cartService.clearUserCart(userId);
    return new Result<>().success();
}


}
