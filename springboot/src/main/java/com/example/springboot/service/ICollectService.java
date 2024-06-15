package com.example.springboot.service;

import com.example.springboot.controller.requst.LoginRequest;
import com.example.springboot.entity.Collect;

import java.util.List;

public interface ICollectService {

    List<Collect> list(Integer userid);
    
    void save(Collect collect);

    Collect getById(Integer userid, Integer roomstyleid);
    
    void deleteById(Integer id);

}
