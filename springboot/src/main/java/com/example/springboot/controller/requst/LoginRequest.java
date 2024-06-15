package com.example.springboot.controller.requst;

import lombok.Data;

@Data
public class LoginRequest {
    private String phone;
    private String password;
}
