package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.domain.Dynamic;
import com.example.mapper.DynamicMapper;
import com.example.service.IDynamicService;
import org.springframework.stereotype.Service;

@Service
public class DynamicServiceImpl extends ServiceImpl<DynamicMapper, Dynamic> implements IDynamicService {}