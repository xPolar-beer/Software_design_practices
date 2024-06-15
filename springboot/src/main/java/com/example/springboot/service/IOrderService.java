package com.example.springboot.service;

import com.example.springboot.controller.requst.OrderPageRequest;
import com.example.springboot.controller.requst.UserOrderPageRequest;
import com.example.springboot.entity.Order;

import java.util.Map;

public interface IOrderService {
//interface声明一个接口

    Object page(OrderPageRequest orderPageRequest);

    Object save(Order order);

    Order getById(Integer id);

    void update(Integer id);

    Object userpage(UserOrderPageRequest userorderPageRequest);

    Object updatestate(String number);

    Map<String, Object> getCountByTimeRange(String timeRange);

    Map<String, Object> getCountByTimeRange2(String timeRange);
}
