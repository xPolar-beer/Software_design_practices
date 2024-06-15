package com.example.springboot.mapper;

import com.example.springboot.controller.requst.RoomPageRequest;
import com.example.springboot.controller.requst.RoomStyleRequest;
import com.example.springboot.entity.Room;
import com.example.springboot.entity.RoomStyle;
import com.example.springboot.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoomStyleMapper {
    //查询房间
    List<RoomStyle> listByCondition(RoomStyleRequest roomStyleRequest);

    RoomStyle getById(Integer id);

    void great(Integer id);

    void updateById(RoomStyle roomStyle);

    void updatenumber(String style);
}
