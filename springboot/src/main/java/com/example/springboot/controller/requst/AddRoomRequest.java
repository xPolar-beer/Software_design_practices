package com.example.springboot.controller.requst;

import lombok.Data;

@Data
public class AddRoomRequest {
    private Integer id;
    private String name;
    private String phone;
    private String intime;
    private Integer day;
    private String sex;
}
