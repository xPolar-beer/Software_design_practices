package com.example.springboot.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.example.springboot.controller.requst.OrderPageRequest;
import com.example.springboot.controller.requst.UserOrderPageRequest;
import com.example.springboot.entity.Order;
import com.example.springboot.mapper.OrderMapper;
import com.example.springboot.mapper.po.MoneyCount;
import com.example.springboot.mapper.po.OrderCount;
import com.example.springboot.service.IOrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service //把它标注为Spring的组件，这样外部才能去调用，会将当前类自动注入到spring容器中，标注在具体的实现类上面
public class OrderService implements IOrderService {
//implements声明自己使用一个或者多个接口，类OrderService来实现接口IOrderService中的方法
    @Autowired
    OrderMapper orderMapper;

    //查询所有
    @Override
    public Object page(OrderPageRequest orderPageRequest) {
        PageHelper.startPage(orderPageRequest.getPageNum(), orderPageRequest.getPageSize());
        List<Order> orders = orderMapper.listByCondition(orderPageRequest);
        return new PageInfo<>(orders);
    }

    @Override
    public Object userpage(UserOrderPageRequest userorderPageRequest) {
        PageHelper.startPage(userorderPageRequest.getPageNum(), userorderPageRequest.getPageSize());
        List<Order> orders = orderMapper.userlistByCondition(userorderPageRequest);
        return new PageInfo<>(orders);
    }

    //
    @Override
    public Object save(Order order) {
        Date date = new Date();
        Date date1 = new Date();
        String number = new SimpleDateFormat("yyyyMMddHHmmss").format(date1);
        Integer num = (int)Math.floor(Math.random() * 10000);
        String str = num.toString();
        String temp = number + str;
        order.setNumber(number + str);
        order.setEstablishtime(java.sql.Date.valueOf(DateUtil.format(date, "yyyy-MM-dd")));
        orderMapper.save(order);
        return temp;
    }

    //获取某个
    @Override
    public Order getById(Integer id) {
        return orderMapper.getById(id);
    }

    @Override
    public void update(Integer id) {
        orderMapper.updateById(id);
    }

    @Override
    public Object updatestate(String number) {
        orderMapper.updateByOrdernumber(number);
        return orderMapper.findDayByOrderNumber(number);
    }

    @Override
    public Map<String, Object> getCountByTimeRange(String timeRange) {
        Map<String, Object> map = new HashMap<>();
        Date today = new Date();
        List<DateTime> dateRange;
        switch (timeRange) {
            case "week":
                dateRange = DateUtil.rangeToList(today, DateUtil.offsetDay(today, 6), DateField.DAY_OF_WEEK);
                break;
            case "month1":
                dateRange = DateUtil.rangeToList(today, DateUtil.offsetDay(today, 29), DateField.DAY_OF_MONTH);
                break;
            case "month2":
                dateRange = DateUtil.rangeToList(today, DateUtil.offsetDay(today, 59), DateField.DAY_OF_MONTH);
                break;
            case "month3":
                dateRange = DateUtil.rangeToList(today, DateUtil.offsetDay(today, 89), DateField.DAY_OF_MONTH);
                break;
            default:
                dateRange = new ArrayList<>();
        }
        List<String> dateStrRange = datetimeToDateStr(dateRange);
        map.put("date", dateStrRange);
        List<OrderCount> singleCount = orderMapper.getCountByTimeRange1(timeRange);
        List<OrderCount> doubleCount = orderMapper.getCountByTimeRange2(timeRange);
        map.put("single", countList(singleCount, dateStrRange));
        map.put("double", countList(doubleCount, dateStrRange));
        return map;
    }

    @Override
    public Map<String, Object> getCountByTimeRange2(String timeRange) {
        Map<String, Object> map = new HashMap<>();
        Date today = new Date();
        List<DateTime> dateRange;
        switch (timeRange) {
            case "week":
                dateRange = DateUtil.rangeToList(DateUtil.offsetDay(today, -6), today, DateField.DAY_OF_WEEK);
                break;
            case "month1":
                dateRange = DateUtil.rangeToList(DateUtil.offsetDay(today, -29), today, DateField.DAY_OF_MONTH);
                break;
            case "month2":
                dateRange = DateUtil.rangeToList(DateUtil.offsetDay(today, -59), today, DateField.DAY_OF_MONTH);
                break;
            case "month3":
                dateRange = DateUtil.rangeToList(DateUtil.offsetDay(today, -89), today, DateField.DAY_OF_MONTH);
                break;
            default:
                dateRange = new ArrayList<>();
        }
        List<String> dateStrRange = datetimeToDateStr(dateRange);
        map.put("date", dateStrRange);
        List<MoneyCount> sumCount = orderMapper.getCountByTimeRange3(timeRange);
        map.put("money", countList2(sumCount, dateStrRange));
        return map;
    }


    private List<String> datetimeToDateStr(List<DateTime> dateTimeList) {
        List<String> list = CollUtil.newArrayList();
        if (CollUtil.isEmpty(dateTimeList)) {
            return list;
        }
        for (DateTime dateTime : dateTimeList) {
            String date = DateUtil.formatDate(dateTime);
            list.add(date);
        }
        return list;
    }

    private List<Integer> countList(List<OrderCount> countPOList, List<String> dateRange) {
        List<Integer> list = CollUtil.newArrayList();
        if (CollUtil.isEmpty(countPOList)) {
            return list;
        }
        for (String date : dateRange) {
            Integer count = countPOList.stream().filter(countPO -> date.equals(countPO.getDate()))
                    .map(OrderCount::getCount).findFirst().orElse(0);
            list.add(count);
        }
        return list;
    }

    private List<BigDecimal> countList2(List<MoneyCount> countPOList, List<String> dateRange) {
        List<BigDecimal> list = CollUtil.newArrayList();
        if (CollUtil.isEmpty(countPOList)) {
            return list;
        }
        for (String date : dateRange) {
            //map()取出对应值
            BigDecimal count = countPOList.stream().filter(countPO -> date.equals(countPO.getDate()))
                    .map(MoneyCount::getCount).findFirst().orElse(BigDecimal.valueOf(0));
            list.add(count);
        }
        return list;
    }


}
