package com.example.springboot.service.impl;

import cn.hutool.core.date.DateUtil;
import com.example.springboot.controller.requst.AddRoomDayRequest;
import com.example.springboot.controller.requst.AddRoomRequest;
import com.example.springboot.controller.requst.RoomPageRequest;
import com.example.springboot.controller.requst.RoomRequest;
import com.example.springboot.entity.Room;
import com.example.springboot.entity.User;
import com.example.springboot.mapper.RoomMapper;
import com.example.springboot.service.IRoomService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class RoomService implements IRoomService {

    @Autowired
    RoomMapper roomMapper;

    @Override
    public Object page1(RoomPageRequest roomPageRequest) {
        PageHelper.startPage(roomPageRequest.getPageNum(), roomPageRequest.getPageSize());
        List<Room> rooms = roomMapper.listByCondition1(roomPageRequest);
        return new PageInfo<>(rooms);
    }

    @Override
    public Object page2(RoomPageRequest roomPageRequest) {
        PageHelper.startPage(roomPageRequest.getPageNum(), roomPageRequest.getPageSize());
        List<Room> rooms = roomMapper.listByCondition2(roomPageRequest);
        return new PageInfo<>(rooms);
    }

    @Override
    public Object page3(RoomPageRequest roomPageRequest) {
        PageHelper.startPage(roomPageRequest.getPageNum(), roomPageRequest.getPageSize());
        List<Room> rooms = roomMapper.listByCondition3(roomPageRequest);
        return new PageInfo<>(rooms);
    }

    @Override
    public Object page4(RoomPageRequest roomPageRequest) {
        PageHelper.startPage(roomPageRequest.getPageNum(), roomPageRequest.getPageSize());
        List<Room> rooms = roomMapper.listByCondition4(roomPageRequest);
        return new PageInfo<>(rooms);
    }

    @Override
    public List<String> getRoomsByStyle(Integer id) {
        List<String> roomsnumber = roomMapper.getRoomsByStyle(id);
        return roomsnumber;
    }

    @Override
    public String findroomnumber(Integer id) {
        String roomnumber = roomMapper.getRoomNumberByUserId(id);
        return roomnumber;
    }

    @Override
    public void update(AddRoomRequest addRoomRequest) {
        addRoomRequest.setIntime((DateUtil.format(new Date(), "yyyy-MM-dd")));
        roomMapper.updateByUserNameAndPhone(addRoomRequest);
    }

    @Override
    public void updateout(Integer id, String user) {
        roomMapper.updateByUserIdToNot(id, user);
    }

    @Override
    public void updatein(Integer id) {
        roomMapper.updateByUserIdToYes(id);
    }

    @Override
    public void updatenotuse(Integer userid) {
        roomMapper.updateByIdToNotUse(userid);
    }

    @Override
    public void updateup(Integer userid, String roomnumber) {
        roomMapper.updateByUserIdToUp(userid, roomnumber);
    }

    @Override
    public void updateordernumber(String ordernumber, String roomnumber) {
        roomMapper.updateByUserIdToOrdernumber(ordernumber, roomnumber);
    }

    @Override
    public void updatenotup(AddRoomDayRequest addRoomDayRequest) {
        addRoomDayRequest.setIntime((DateUtil.format(new Date(), "yyyy-MM-dd")));
        roomMapper.updateByRoomIdNotUp(addRoomDayRequest);
    }
}
