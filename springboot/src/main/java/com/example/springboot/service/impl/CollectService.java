package com.example.springboot.service.impl;

import com.example.springboot.entity.Collect;
import com.example.springboot.mapper.CollectMapper;
import com.example.springboot.service.ICollectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class CollectService implements ICollectService {

    @Autowired
    CollectMapper collectMapper;

    @Override
    public List<Collect> list(Integer userid) {
        return collectMapper.list(userid);
    }

    @Override
    public void save(Collect collect) {
        collectMapper.save(collect);
    }

    @Override
    public Collect getById(Integer userid, Integer roomstyleid) {
        return collectMapper.getById(userid, roomstyleid);
    }
    
    @Override
    public void deleteById(Integer id) {
        collectMapper.deleteById(id);
    }
}
