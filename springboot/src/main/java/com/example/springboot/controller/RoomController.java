package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.controller.requst.*;
import com.example.springboot.entity.Admin;
import com.example.springboot.entity.Room;
import com.example.springboot.entity.User;
import com.example.springboot.service.IAdminService;
import com.example.springboot.service.IRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    IRoomService roomService;

//    干净房
    @GetMapping("/page1")
    public Result page1(RoomPageRequest PageRequest){
        return Result.success(roomService.page1(PageRequest));
    }

//    入住房
    @GetMapping("/page2")
    public Result page2(RoomPageRequest PageRequest){
        return Result.success(roomService.page2(PageRequest));
    }

//    脏房
    @GetMapping("/page3")
    public Result page3(RoomPageRequest PageRequest){
        return Result.success(roomService.page3(PageRequest));
    }

//    待检修房
    @GetMapping("/page4")
    public Result page4(RoomPageRequest PageRequest){
        return Result.success(roomService.page4(PageRequest));
    }

//    通过用户id获得房间号
    @GetMapping("/findroomnumber/{id}")
    public Result findroomnumber(@PathVariable Integer id){
        return Result.success(roomService.findroomnumber(id));
    }

    @PutMapping("/update")
    public Result update(@RequestBody AddRoomRequest addRoomRequest){
        roomService.update(addRoomRequest);
        return Result.success();
    }

    @PutMapping("/updateout/{id}/{user}")
    public Result updateout(@PathVariable Integer id, @PathVariable String user){
        roomService.updateout(id, user);
        return Result.success();
    }

    @PutMapping("/updatein/{id}")
    public Result updatein(@PathVariable Integer id){
        roomService.updatein(id);
        return Result.success();
    }

   @PutMapping("/updateup/{userid}/{roomnumber}")
    public Result updateup(@PathVariable Integer userid, @PathVariable String roomnumber){
        roomService.updateup(userid, roomnumber);
        return Result.success();
    }

    @PutMapping("/updateordernumber/{ordernumber}/{roomnumber}")
    public Result updateordernumber(@PathVariable String ordernumber, @PathVariable String roomnumber){
        roomService.updateordernumber(ordernumber, roomnumber);
        return Result.success();
    }

    @PutMapping("/updatenotup")
    public Result updatenotup(@RequestBody AddRoomDayRequest addRoomDayRequest){
        roomService.updatenotup(addRoomDayRequest);
        return Result.success();
    }

    @PutMapping("/updatenotuse/{userid}")
    public Result updatenotuse(@PathVariable Integer userid){
        roomService.updatenotuse(userid);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result getRoomsByStyle(@PathVariable Integer id){
        return Result.success(roomService.getRoomsByStyle(id));
    }


}
