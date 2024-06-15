package com.example.springboot.controller.requst;

import lombok.Data;

@Data
public class AddRoomDayRequest {
    private Integer id;
    private Integer userid;
    private String intime;
    private Integer day;
}
