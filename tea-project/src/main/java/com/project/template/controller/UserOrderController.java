package com.project.template.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.template.common.PageVO;
import com.project.template.common.Result;
import com.project.template.entity.UserOrder;
import com.project.template.service.UserOrderService;
import com.project.template.utils.Utils;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * @author 太上老君 *
 */
@RestController
@RequestMapping("/userOrder")
public class UserOrderController {
    private static final Logger log = LoggerFactory.getLogger(UserOrderController.class);

    @Resource
    private UserOrderService userOrderService;

    /**
     * 获取系统中所有订单的数量
     */
    @ApiOperation(value = "count", notes = "获取系统中所有订单的数量")
    @GetMapping("/count")
    public Result<Long> count() {
        // 使用 userOrderService.count() 方法获取总数
        long total = userOrderService.count();
        return new Result<Long>().success(total);
    }

    /**
     * 列表
     */
    @ApiOperation(value = "列表", notes = "列表")
    @GetMapping("/list")
    public Result<List<UserOrder>> list() {
        return new Result<>().success(userOrderService.list());
    }

    /**
     * 分页查询
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page")
    public Result<PageVO<UserOrder>> findPage(@RequestParam Map<String, Object> query,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<UserOrder> page = userOrderService.page(query, pageNum, pageSize);
        return new Result<>().success(new PageVO<>(page));
    }

    /**
     * 数据新增
     */
    @ApiOperation(value = "数据新增", notes = "数据新增")
    @PostMapping()
    public Result add(@Validated @RequestBody UserOrder entity) {
        userOrderService.save(entity);
        return new Result<>().success();
    }

    /**
     * 数据更新
     */
    @ApiOperation(value = "数据更新", notes = "数据更新")
    @PutMapping()
    public Result update(@Validated @RequestBody UserOrder entity) {
        userOrderService.updateById(entity);
        return new Result<>().success();
    }

    // @PostMapping
    // public Result save(@RequestBody UserOrder userOrder) {
    // userOrderService.saveOrUpdate(userOrder);
    // return new Result<>().success();
    // }

    /**
     * 根据id删除
     */
    @ApiOperation(value = "数据根据id批量删除", notes = "数据根据id批量删除")
    @DeleteMapping()
    public Result delBatch(@RequestBody List<Integer> ids) {
        userOrderService.removeByIds(ids);
        return new Result<>().success();
    }

    /**
     * 根据id查询
     */
    @ApiOperation(value = "根据id查询", notes = "根据id查询")
    @GetMapping("/{id}")
    public Result<UserOrder> getById(@PathVariable("id") Integer id) {
        UserOrder entity = userOrderService.getById(id);
        return new Result<>().success(entity);
    }

    @ApiOperation(value = "addOrder", notes = "根据用户token信息生成订单")
    @GetMapping("addOrder")
    public Result addOrder() {
        return new Result().success(userOrderService.addOrder());
    }

    // @ApiOperation(value = "queryOrder", notes = "根据订单状态获取该用户对应的订单信息")
    // @GetMapping("queryOrder")
    // public Result queryOrder(String ostate) {
    // System.out.println("ostate = " + ostate);
    // return new Result().success(userOrderService.queryOrder(ostate));
    // }

    @ApiOperation(value = "queryOrder", notes = "获取当前用户的所有订单信息")
    @GetMapping("queryOrder")
    public Result queryOrder() {
        return new Result().success(userOrderService.queryOrder());
    }

    // @ApiOperation(value = "updateOstate", notes = "更新收货状态")
    // @PutMapping("/updateOstate")
    // public Result updateOstate(@Validated @RequestBody UserOrder entity) {
    // log.info("接收到的订单ID: {}", entity.getId());
    // log.info("接收到的订单状态: {}", entity.getOstate());
    // // log.info("完整的订单对象: {}", entity);

    // try {
    // userOrderService.updateById(entity);
    // return new Result<>().success();
    // } catch (Exception e) {
    // log.error("更新订单状态失败", e);
    // return new Result<>().error("更新失败：" + e.getMessage());
    // }
    // }

    @ApiOperation(value = "updateState", notes = "更新订单收货状态")
    @PostMapping("/updateState")
    public Result updateState(@RequestBody UserOrder userOrder) {
        log.info("更新订单状态 - 订单ID: {}, 新状态: {}", userOrder.getId(), userOrder.getOstate());

        try {
            // 检查订单是否存在
            UserOrder existOrder = userOrderService.getById(userOrder.getId());
            if (existOrder == null) {
                return new Result().error("订单不存在");
            }

            // 检查订单是否属于当前用户
            Long currentUserId = Long.valueOf(Utils.getUser().getId());
            if (!existOrder.getUid().equals(currentUserId)) {
                return new Result().error("无权操作此订单");
            }

            // 只更新订单状态
            UserOrder updateOrder = new UserOrder();
            updateOrder.setId(userOrder.getId());
            updateOrder.setOstate(userOrder.getOstate());

            boolean success = userOrderService.updateById(updateOrder);
            if (success) {
                return new Result().success("更新成功");
            } else {
                return new Result().error("更新失败");
            }

        } catch (Exception e) {
            log.error("更新订单状态失败", e);
            return new Result().error("更新失败：" + e.getMessage());
        }
    }

}
