package com.project.template.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.template.common.PageVO;
import com.project.template.common.Result;
import com.project.template.entity.Notice;
import com.project.template.service.NoticeService;
import com.project.template.service.SysUserService;
import com.project.template.utils.Utils;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户ID 前端控制器
 * </p>
 *
 * @author 太上老君
 */
@RestController
@RequestMapping("/notice")
public class NoticeController {
    @Resource
    private NoticeService noticeService;

    @Resource
    private SysUserService sysUserService;

    /**
     * 统计通知总数
     */
    @ApiOperation(value = "count", notes = "获取系统中所有通知的数量")
    @GetMapping("/count")
    public Result<Long> count() {
        // 使用 noticeService.count() 方法获取总数
        long total = noticeService.count();
        return new Result<Long>().success(total);
    }

    /**
     * 列表
     */
    @ApiOperation(value = "列表", notes = "列表")
    @GetMapping("/list")
    public Result<List<Notice>> list() {
        return new Result<>().success(noticeService.list());
    }

    /**
     * 分页查询
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page")
    public Result<PageVO<Notice>> findPage(@RequestParam Map<String, Object> query,
            @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<Notice> page = noticeService.page(query, pageNum, pageSize);
        page.getRecords().forEach(record -> {
            record.selectUserName(sysUserService);
        });
        return new Result<>().success(new PageVO<>(page));
    }

    /**
     * 数据新增
     */
    @ApiOperation(value = "数据新增", notes = "数据新增")
    @PostMapping()
    public Result add(@Validated @RequestBody Notice entity) {
        noticeService.save(entity);
        return new Result<>().success();
    }

    /**
     * 数据更新
     */
    @ApiOperation(value = "数据更新", notes = "数据更新")
    @PutMapping()
    public Result update(@Validated @RequestBody Notice entity) {
        noticeService.updateById(entity);
        return new Result<>().success();
    }

    /**
     * 根据id删除
     */
    @ApiOperation(value = "数据根据id批量删除", notes = "数据根据id批量删除")
    @DeleteMapping()
    public Result delBatch(@RequestBody List<Integer> ids) {
        noticeService.removeByIds(ids);
        return new Result<>().success();
    }

    /**
     * 根据id查询
     */
    @ApiOperation(value = "根据id查询", notes = "根据id查询")
    @GetMapping("/{id}")
    public Result<Notice> getById(@PathVariable("id") Integer id) {
        Notice entity = noticeService.getById(id);
        return new Result<>().success(entity);
    }
}
