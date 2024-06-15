package com.example.springboot.mapper;

import com.example.springboot.controller.requst.*;
import com.example.springboot.entity.Admin;
import com.example.springboot.entity.Room;
import com.example.springboot.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;
import java.util.List;

@Mapper
public interface RoomMapper {

    //查询房间
    List<Room> listByCondition1(RoomPageRequest roomPageRequest);

    List<Room> listByCondition2(RoomPageRequest roomPageRequest);

    List<Room> listByCondition3(RoomPageRequest roomPageRequest);

    List<Room> listByCondition4(RoomPageRequest roomPageRequest);

    void updateByUserNameAndPhone(AddRoomRequest addRoomRequest);

    void updateByUserIdToNot(Integer id, String user);

    void updateByUserIdToYes(Integer id);

    void updateByIdToNotUse(Integer userid);

    List<String> getRoomsByStyle(Integer id);

//    void updateByRoomIdNotUp(Integer id, Date date);

    void updateByUserIdToUp(Integer userid, String roomnumber);

    void updateByUserIdToOrdernumber(String ordernumber, String roomnumber);

    void updateByRoomIdNotUp(AddRoomDayRequest addRoomDayRequest);

    String getRoomNumberByUserId(Integer id);


//    void updateByUserIdToUp(RoomRequest roomRequest);
}
