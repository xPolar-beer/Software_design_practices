package com.example.springboot.service.impl;

import com.example.springboot.controller.requst.RoomPageRequest;
import com.example.springboot.controller.requst.RoomStyleRequest;
import com.example.springboot.entity.Room;
import com.example.springboot.entity.RoomStyle;
import com.example.springboot.mapper.RoomMapper;
import com.example.springboot.mapper.RoomStyleMapper;
import com.example.springboot.service.IRoomStyleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class RoomStyleService implements IRoomStyleService {

    @Autowired
    RoomStyleMapper roomStyleMapper;

    @Override
    public Object page(RoomStyleRequest roomStyleRequest) {
        PageHelper.startPage(roomStyleRequest.getPageNum(), roomStyleRequest.getPageSize());
        List<RoomStyle> roomstyles = roomStyleMapper.listByCondition(roomStyleRequest);
        return new PageInfo<>(roomstyles);
    }

    @Override
    public RoomStyle getById(Integer id) {
        return roomStyleMapper.getById(id);
    }

    @Override
    public void great(Integer id) {
        roomStyleMapper.great(id);
    }

    @Override
    public void update(RoomStyle roomStyle) {
        roomStyleMapper.updateById(roomStyle);
    }

    @Override
    public void updatenumber(String style) {
        roomStyleMapper.updatenumber(style);
    }

}
