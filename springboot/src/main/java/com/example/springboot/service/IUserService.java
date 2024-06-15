package com.example.springboot.service;

import com.example.springboot.controller.dto.UserLoginDTO;
import com.example.springboot.controller.requst.LoginRequest;
import com.example.springboot.controller.requst.PasswordRequest;
import com.example.springboot.controller.requst.UserPageRequest;
import com.example.springboot.entity.User;

import java.math.BigDecimal;
import java.util.List;

public interface IUserService {
//interface声明一个接口
    List<User> list();

    Object page(UserPageRequest userPageRequest);

    void save(User user);

    User getById(Integer id);

    void update(User user);

    void deleteById(Integer id);

    UserLoginDTO login(LoginRequest request);

    void updatemoney(BigDecimal money, Integer id);

    void updatemoneymoney(Integer id, BigDecimal money);

    String passwordrequest(PasswordRequest request);

    void updatepassword(Integer id, String pass);

    void updatestate(Integer id, Integer state);
}
