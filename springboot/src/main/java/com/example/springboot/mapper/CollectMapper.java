package com.example.springboot.mapper;

import com.example.springboot.controller.requst.LoginRequest;
import com.example.springboot.entity.Collect;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CollectMapper {

    //查询所有管理员的信息
    List<Collect> list(Integer userid);

    //新添收藏
    void save(Collect collect);

    //通过id来访问收藏
    Collect getById(Integer userid, Integer roomstyleid);

    //删除收藏
    void deleteById(Integer id);
}
