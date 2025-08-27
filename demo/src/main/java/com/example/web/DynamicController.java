package com.example.web;

import com.example.domain.Dynamic;
import com.example.service.IDynamicService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dynamic")
public class DynamicController extends BaseController<IDynamicService, Dynamic> {}