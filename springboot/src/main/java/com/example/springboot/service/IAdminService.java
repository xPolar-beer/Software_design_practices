package com.example.springboot.service;

import com.example.springboot.controller.dto.AdminLoginDTO;
import com.example.springboot.controller.dto.UserLoginDTO;
import com.example.springboot.controller.requst.AdminPageRequest;
import com.example.springboot.controller.requst.LoginRequest;
import com.example.springboot.entity.Admin;

import java.util.List;

public interface IAdminService {

    List<Admin> list();

    Object page(AdminPageRequest adminPageRequest);

    void save(Admin admin);

    Admin getById(Integer id);

    void update(Admin admin);

    void deleteById(Integer id);

    AdminLoginDTO login(LoginRequest request);
}
