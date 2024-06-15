package com.example.springboot.service;

import com.example.springboot.controller.requst.RoomStyleRequest;
import com.example.springboot.entity.RoomStyle;

public interface IRoomStyleService {

    Object page(RoomStyleRequest roomstyleRequest);

    RoomStyle getById(Integer id);

    void great(Integer id);

    void update(RoomStyle roomStyle);

    void updatenumber(String style);
}
