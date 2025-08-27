package com.example.pro.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pro.domain.Dynamic;
import com.example.pro.mapper.DynamicMapper;
import com.example.pro.service.IDynamicService;
import org.springframework.stereotype.Service;

@Service
public class DynamicServiceImpl extends ServiceImpl<DynamicMapper, Dynamic> implements IDynamicService {}