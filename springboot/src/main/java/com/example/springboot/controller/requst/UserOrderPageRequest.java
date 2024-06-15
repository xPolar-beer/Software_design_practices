package com.example.springboot.controller.requst;

import lombok.Data;

@Data
public class UserOrderPageRequest extends BaseRequest {
    private Integer userid;
    private String number;
    private String reservetime;
}
