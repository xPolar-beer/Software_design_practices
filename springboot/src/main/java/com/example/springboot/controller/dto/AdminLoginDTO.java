package com.example.springboot.controller.dto;

import lombok.Data;

@Data
public class AdminLoginDTO {
    private Integer id;
    private String phone;
    private String name;
    private String username;
    private String token;
}
