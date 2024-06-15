package com.example.springboot.controller.requst;

import lombok.Data;

@Data
public class AdminPageRequest extends BaseRequest {
    private String name;
    private String phone;
}
