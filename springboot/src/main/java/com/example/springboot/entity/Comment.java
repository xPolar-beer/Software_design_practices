package com.example.springboot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.sql.Date;

@Data
public class Comment {
    private Integer id;
    private String content; //正文
    private Integer roomid; //房间类型id
    private Integer userid; //用户id
    private Integer commentid; //评论id
    private String role; //角色
    private String avatar;
    private String username;
    private Integer greatnum;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createtime; //创建时间
}
