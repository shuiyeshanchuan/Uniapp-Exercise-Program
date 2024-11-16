package com.project.template.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.template.common.PageVO;
import com.project.template.common.Result;
import com.project.template.entity.Cart;
import com.project.template.entity.Product;
import com.project.template.service.ProductService;
import com.project.template.service.TypeService;
import com.project.template.utils.Utils;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 太上老君 */
@RestController
@RequestMapping("/product")
public class ProductController {
    @Resource
    private ProductService productService;

    @Resource
    private TypeService typeService;

    /**
     * 获取系统中所有商品的数量
     */
    @ApiOperation(value = "count", notes = "获取系统中所有商品的数量")
    @GetMapping("/count")
    public Result<Long> count() {
        // 使用 productService.count() 方法获取总数
        long total = productService.count();
        return new Result<Long>().success(total);
    }


    /**
     * 列表
     */
    @ApiOperation(value = "列表", notes = "列表")
    @GetMapping("/list")
    public Result<List<Product>> list() {
        return new Result<>().success(productService.list());
    }

    /**
     * 分页查询
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page")
    public Result<PageVO<Product>> findPage(@RequestParam Map<String, Object> query,
            @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<Product> page = productService.page(query, pageNum, pageSize);
        page.getRecords().forEach(record -> {
            record.selectTypeName(typeService);
        });
        return new Result<>().success(new PageVO<>(page));
    }

    /**
     * 数据新增
     */
    @ApiOperation(value = "数据新增", notes = "数据新增")
    @PostMapping()
    public Result add(@Validated @RequestBody Product entity) {
        productService.save(entity);
        return new Result<>().success();
    }

    /**
     * 数据更新
     */
    @ApiOperation(value = "数据更新", notes = "数据更新")
    @PutMapping()
    public Result update(@Validated @RequestBody Product entity) {
        productService.updateById(entity);
        return new Result<>().success();
    }

    /**
     * 根据id删除
     */
    @ApiOperation(value = "数据根据id批量删除", notes = "数据根据id批量删除")
    @DeleteMapping()
    public Result delBatch(@RequestBody List<Integer> ids) {
        productService.removeByIds(ids);
        return new Result<>().success();
    }

    /**
     * 根据id查询
     */
    @ApiOperation(value = "根据id查询", notes = "根据id查询")
    @GetMapping("/{id}")
    public Result<Product> getById(@PathVariable("id") Integer id) {
        Product entity = productService.getById(id);
        return new Result<>().success(entity);
    }

    @ApiOperation(value = "搜索商品", notes = "根据商品名称或介绍搜索")
    @GetMapping("/search")
    public Result<List<Product>> search(@RequestParam String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return new Result<>().success(Collections.emptyList());
        }
        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(Product::getPname, keyword.trim())
                .or()
                .like(Product::getTintroduce, keyword.trim());
        List<Product> products = productService.list(wrapper);
        // 获取类型名称
        products.forEach(product -> product.selectTypeName(typeService));
        return new Result<>().success(products);
    }

    @GetMapping("queryProductByType")
    @ApiOperation(value = "queryProductByType", notes = "获取用户的购物车信息")
    public Result<Product> queryProductByType(Integer id) {
        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Product::getTypeid, id);
        return new Result<>().success(productService.list(wrapper));
    }

}
