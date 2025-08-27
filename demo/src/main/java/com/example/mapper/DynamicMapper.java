package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.domain.Dynamic;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DynamicMapper extends BaseMapper<Dynamic> {}