package com.example.pro.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("dynamic")
public class Dynamic {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String type;      // e.g., "STATUS", "EVENT"
    private String payload;   // JSON or text content

    // optional common columns
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}