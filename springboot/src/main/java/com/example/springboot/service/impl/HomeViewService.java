package com.example.springboot.service.impl;

import com.example.springboot.controller.requst.RoomStyleRequest;
import com.example.springboot.entity.Admin;
import com.example.springboot.entity.HomeView;
import com.example.springboot.entity.RoomStyle;
import com.example.springboot.mapper.HomeViewMapper;
import com.example.springboot.service.IHomeViewService;
import com.example.springboot.service.IRoomStyleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class HomeViewService implements IHomeViewService {

    @Autowired
    HomeViewMapper homeViewMapper;

    @Override
    public List<HomeView> list() {
        return homeViewMapper.list();
    }

    @Override
    public HomeView getById(Integer id) {
        return homeViewMapper.getById(id);
    }

    @Override
    public void update(HomeView homeView) {
        homeViewMapper.updateById(homeView);
    }

}
