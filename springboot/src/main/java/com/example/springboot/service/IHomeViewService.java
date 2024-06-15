package com.example.springboot.service;

import com.example.springboot.controller.dto.AdminLoginDTO;
import com.example.springboot.controller.requst.AdminPageRequest;
import com.example.springboot.controller.requst.LoginRequest;
import com.example.springboot.entity.Admin;
import com.example.springboot.entity.HomeView;

import java.util.List;

public interface IHomeViewService {

    List<HomeView> list();

    HomeView getById(Integer id);

    void update(HomeView homeView);
}
