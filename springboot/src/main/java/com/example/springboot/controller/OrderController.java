package com.example.springboot.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.example.springboot.common.Result;
import com.example.springboot.controller.requst.LoginRequest;
import com.example.springboot.controller.requst.OrderPageRequest;
import com.example.springboot.controller.requst.UserOrderPageRequest;
import com.example.springboot.controller.requst.UserPageRequest;
import com.example.springboot.entity.Order;
import com.example.springboot.entity.User;
import com.example.springboot.service.IOrderService;
import com.example.springboot.service.IUserService;
import com.example.springboot.utils.TokenUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

@Slf4j
@CrossOrigin //解决跨域错误
@RestController //以json格式返回
@RequestMapping("/order")
public class OrderController {

    @Autowired
    IOrderService orderService;

    //保存（插入）
    @PostMapping("/save")
    public Result save(@RequestBody Order order){
        return Result.success(orderService.save(order));
    }

    //更新
    @PutMapping("/update/{id}")
    public Result update(@PathVariable Integer id){
        orderService.update(id);
        return Result.success();
    }

    @PutMapping("/updatestate/{number}")
    public Result updatestate(@PathVariable String number){
        return Result.success(orderService.updatestate(number));
    }

    //查询单个
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        Order order = orderService.getById(id);
        return Result.success(order);
    }

    @GetMapping("/page")
    public Result page(OrderPageRequest orderPageRequest){
        return Result.success(orderService.page(orderPageRequest));
    }

    @GetMapping("/userpage")
    public Result userpage(UserOrderPageRequest userorderPageRequest){
        return Result.success(orderService.userpage(userorderPageRequest));
    }

    @GetMapping("/lineCharts/{timeRange}")
    public Result lineCharts(@PathVariable String timeRange) {
        return Result.success(orderService.getCountByTimeRange(timeRange));
    }

    @GetMapping("/lineCharts2/{timeRange}")
    public Result lineCharts2(@PathVariable String timeRange) {
        return Result.success(orderService.getCountByTimeRange2(timeRange));
    }
}
