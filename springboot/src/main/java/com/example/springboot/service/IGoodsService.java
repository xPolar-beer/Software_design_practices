package com.example.springboot.service;

import com.example.springboot.controller.requst.GoodsPageRequest;
import com.example.springboot.controller.requst.OrderPageRequest;
import com.example.springboot.controller.requst.UserOrderPageRequest;
import com.example.springboot.entity.Goods;
import com.example.springboot.entity.Order;

import java.util.List;
import java.util.Map;

public interface IGoodsService {
//interface声明一个接口

//    Object page(OrderPageRequest orderPageRequest);
//
//    Object save(Order order);
//
//    Order getById(Integer id);
//
//    void update(Integer id);
//
//    Object userpage(UserOrderPageRequest userorderPageRequest);
//
//    Object updatestate(String number);
//
//    Map<String, Object> getCountByTimeRange(String timeRange);
//
//    Map<String, Object> getCountByTimeRange2(String timeRange);

    Object page(GoodsPageRequest goodsPageRequest);

    void deleteById(Integer id);

    void save(Goods goods);

    void update(Goods goods);

    List<String> findall();
}
