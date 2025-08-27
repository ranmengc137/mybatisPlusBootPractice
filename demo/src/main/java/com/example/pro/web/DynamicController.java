package com.example.pro.web;

import com.example.pro.domain.Dynamic;
import com.example.pro.service.IDynamicService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dynamic")
public class DynamicController extends BaseController<IDynamicService, Dynamic> {}