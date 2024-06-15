package com.example.springboot.mapper;

import com.example.springboot.controller.requst.AdminPageRequest;
import com.example.springboot.controller.requst.LoginRequest;
import com.example.springboot.entity.Admin;
import com.example.springboot.entity.HomeView;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HomeViewMapper {

    //查询所有轮播图的信息
    List<HomeView> list();

//    //新添管理员
//    void save(HomeView homeView);

    //通过id来访问具体的轮播图
    HomeView getById(Integer id);

    //更新轮播图
    void updateById(HomeView homeView);

}
