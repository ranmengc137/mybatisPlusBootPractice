package com.example.dto;

import lombok.Data;

@Data
public class PageParamDto<E> {
    private int page = 1;
    private int size = 10;
    private String asc;   // e.g. "created_at,name"
    private String desc;  // e.g. "updated_at"
    private E condition;  // optional: entity to filter with non-null fields
}