package com.example.pro.web;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pro.common.ApiResult;
import com.example.pro.dto.PageParamDto;
import com.example.pro.util.ApprenticeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/** Generic base controller: extend this to get CRUD endpoints for your entity. */
public class BaseController<S extends IService<E>, E> {

    @Autowired
    protected S baseService;

    @PostMapping("/insert")
    public ApiResult<String> insert(@RequestBody E entity) {
        baseService.save(entity);
        return ApiResult.success("Inserted");
    }

    @PostMapping("/deleteById")
    public ApiResult<String> deleteById(@RequestBody List<Long> ids) {
        baseService.removeByIds(ids);
        return ApiResult.success("Deleted");
    }

    @PostMapping("/updateById")
    public ApiResult<String> updateById(@RequestBody E entity) {
        baseService.updateById(entity);
        return ApiResult.success("Updated");
    }

    @GetMapping("/getById")
    public ApiResult<E> getById(@RequestParam("id") Integer id) {
        return ApiResult.success(baseService.getById(id));
    }

    @PostMapping("/save")
    public ApiResult<String> save(@RequestBody E entity) {
        baseService.saveOrUpdate(entity);
        return ApiResult.success("Saved");
    }

    @PostMapping("/list")
    public ApiResult<List<E>> list(@RequestBody E entity) {
        QueryWrapper<E> wrapper = ApprenticeUtil.getQueryWrapper(entity);
        return ApiResult.success(baseService.list(wrapper));
    }

    @PostMapping("/page")
    public ApiResult<Page<E>> page(@RequestBody PageParamDto<E> param) {
        int page = Math.max(1, param.getPage());
        int size = Math.min(Math.max(1, param.getSize()), 100);

        Page<E> mpPage = new Page<>(page, size);
        QueryWrapper<E> wrapper = new QueryWrapper<>();

        // optional: filter by condition's non-null fields
        if (param.getCondition() != null) {
            wrapper = ApprenticeUtil.getQueryWrapper(param.getCondition());
        }

        // sort
        if (StrUtil.isNotBlank(param.getAsc()) && !"null".equalsIgnoreCase(param.getAsc())) {
            wrapper.orderByAsc(Arrays.asList(param.getAsc().split(",")));
        }
        if (StrUtil.isNotBlank(param.getDesc()) && !"null".equalsIgnoreCase(param.getDesc())) {
            wrapper.orderByDesc(Arrays.asList(param.getDesc().split(",")));
        }

        return ApiResult.success(baseService.page(mpPage, wrapper));
    }

    @PostMapping("/count")
    public ApiResult<Long> count(@RequestBody E entity) {
        QueryWrapper<E> wrapper = ApprenticeUtil.getQueryWrapper(entity);
        return ApiResult.success(baseService.count(wrapper));
    }
}