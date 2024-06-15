package com.example.springboot.controller.requst;

import lombok.Data;

@Data
public class RoomRequest{
    private Integer userid;
    private String roomnumber;
    private String ordernumber;
}
