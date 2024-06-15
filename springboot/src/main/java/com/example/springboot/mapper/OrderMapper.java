package com.example.springboot.mapper;

import com.example.springboot.controller.requst.LoginRequest;
import com.example.springboot.controller.requst.OrderPageRequest;
import com.example.springboot.controller.requst.UserOrderPageRequest;
import com.example.springboot.controller.requst.UserPageRequest;
import com.example.springboot.entity.Order;
import com.example.springboot.entity.User;
import com.example.springboot.mapper.po.MoneyCount;
import com.example.springboot.mapper.po.OrderCount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderMapper {
//定义了一个OrderMapper的接口，其中有一个方法listUsers()
    //@Select("select * from users")
    //获取所有订单
    List<Order> listByCondition(OrderPageRequest orderPageRequest);

    List<Order> userlistByCondition(UserOrderPageRequest userorderPageRequest);
    //保存订单
    void save(Order order);

    //查询单个订单
    Order getById(Integer id);

    //更新订单状态
    void updateById(Integer id);


    void updateByOrdernumber(String number);

    Object findDayByOrderNumber(String number);

    List<OrderCount> getCountByTimeRange1(@Param("timeRange") String timeRange);  // 1 single  2 double

    List<OrderCount> getCountByTimeRange2(@Param("timeRange") String timeRange);  // 1 single  2 double

    List<MoneyCount> getCountByTimeRange3(@Param("timeRange") String timeRange);


}
