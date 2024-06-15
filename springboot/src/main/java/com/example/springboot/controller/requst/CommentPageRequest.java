package com.example.springboot.controller.requst;

import lombok.Data;

@Data
public class CommentPageRequest extends BaseRequest {
    private Integer userid;
    private String createtime;
}
