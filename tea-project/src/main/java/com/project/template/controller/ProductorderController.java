package com.project.template.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.template.common.PageVO;
import com.project.template.common.ProductSalesVO;
import com.project.template.common.Result;
import com.project.template.entity.Productorder;
import com.project.template.service.ProductorderService;
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
@RequestMapping("/productorder")
public class ProductorderController {
    @Resource
    private ProductorderService productorderService;


    /**
     * 列表
     */
    @ApiOperation(value = "列表", notes = "列表")
    @GetMapping("/list")
    public Result<List<Productorder>> list() {
        return new Result<>().success(productorderService.list());
    }

    /**
     * 分页查询
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page")
    public Result<PageVO<Productorder>> findPage(@RequestParam Map<String, Object> query,
                                                 @RequestParam(defaultValue = "1") Integer pageNum,
                                                 @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<Productorder> page = productorderService.page(query, pageNum, pageSize);
        return new Result<>().success(new PageVO<>(page));
    }

    /**
     * 数据新增
     */
    @ApiOperation(value = "数据新增", notes = "数据新增")
    @PostMapping()
    public Result add(@Validated @RequestBody Productorder entity) {
        productorderService.save(entity);
        return new Result<>().success();
    }

    /**
     * 数据更新
     */
    @ApiOperation(value = "数据更新", notes = "数据更新")
    @PutMapping()
    public Result update(@Validated @RequestBody Productorder entity) {
        productorderService.updateById(entity);
        return new Result<>().success();
    }

    /**
     * 根据id删除
     */
    @ApiOperation(value = "数据根据id批量删除", notes = "数据根据id批量删除")
    @DeleteMapping()
    public Result delBatch(@RequestBody List<Integer> ids) {
        productorderService.removeByIds(ids);
        return new Result<>().success();
    }

    /**
     * 根据id查询
     */
    @ApiOperation(value = "根据id查询", notes = "根据id查询")
    @GetMapping("/{id}")
    public Result<Productorder> getById(@PathVariable("id") Integer id) {
        Productorder entity = productorderService.getById(id);
        return new Result<>().success(entity);
    }

    /**
     * 获取销量前四的商品
     */
        @ApiOperation(value = "top-four", notes = "获取销量前四的商品")
        @GetMapping("/top-four")
        public Result<List<ProductSalesVO>> getTopFourProducts() {
            List<ProductSalesVO> result = productorderService.getTopFourProducts();
            return new Result<>().success(result);
        }




}
