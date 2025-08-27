package com.example.pro.web;

import com.example.pro.domain.Blog;
import com.example.pro.service.IBlogService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/blog")
public class BlogController extends BaseController<IBlogService, Blog> {}