package com.example.web;

import com.example.domain.Blog;
import com.example.service.IBlogService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/blog")
public class BlogController extends BaseController<IBlogService, Blog> {}