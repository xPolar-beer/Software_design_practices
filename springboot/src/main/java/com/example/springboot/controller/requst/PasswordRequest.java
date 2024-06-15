package com.example.springboot.controller.requst;

import lombok.Data;

@Data
public class PasswordRequest {
    private Integer id;
    private String oldpass;
    private String pass;
    private String checkpass;
}
