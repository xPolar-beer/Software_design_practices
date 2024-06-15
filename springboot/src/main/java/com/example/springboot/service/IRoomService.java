package com.example.springboot.service;

import com.example.springboot.controller.requst.AddRoomDayRequest;
import com.example.springboot.controller.requst.AddRoomRequest;
import com.example.springboot.controller.requst.RoomPageRequest;
import com.example.springboot.controller.requst.RoomRequest;
import com.example.springboot.entity.Room;
import com.example.springboot.entity.User;

import java.util.List;

public interface IRoomService {
    Object page1(RoomPageRequest roompageRequest);

    Object page2(RoomPageRequest roomRequest);

    Object page3(RoomPageRequest roompageRequest);

    Object page4(RoomPageRequest roompageRequest);

    void update(AddRoomRequest addRoomRequest);

    void updatein(Integer id);

    void updatenotuse(Integer userid);

    List<String> getRoomsByStyle(Integer id);

    void updateup(Integer userid, String roomnumber);

    void updateordernumber(String ordernumber, String roomnumber);

    void updatenotup(AddRoomDayRequest addRoomDayRequest);

    void updateout(Integer id, String user);

    String findroomnumber(Integer id);

//    Object getRoomsByUserId(Integer id);
}
