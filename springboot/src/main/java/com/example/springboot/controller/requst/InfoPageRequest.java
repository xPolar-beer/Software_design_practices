package com.example.springboot.controller.requst;

import lombok.Data;

@Data
public class InfoPageRequest extends BaseRequest{
    private String user;
    private String phone;
}
