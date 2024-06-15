package com.example.springboot.controller.requst;

import lombok.Data;

@Data
public class RoomPageRequest extends BaseRequest{
    private String roomnumber;
    private String style;
}
