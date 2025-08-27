package com.example.pro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pro.domain.Blog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BlogMapper extends BaseMapper<Blog> {}