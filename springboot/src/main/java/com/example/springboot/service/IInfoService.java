package com.example.springboot.service;

import com.example.springboot.controller.requst.BaseRequest;
import com.example.springboot.controller.requst.GoodsPageRequest;
import com.example.springboot.controller.requst.InfoPageRequest;
import com.example.springboot.entity.Goods;
import com.example.springboot.entity.Info;

import java.util.List;

public interface IInfoService {

    Object page(InfoPageRequest infoPageRequest);

    Object list(BaseRequest baseRequest, Integer userid);

    void save(Info info);

    void deleteById(Integer id);

    void updateById(Integer id);
}
