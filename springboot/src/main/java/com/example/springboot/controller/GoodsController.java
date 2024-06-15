package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.controller.requst.GoodsPageRequest;
import com.example.springboot.controller.requst.OrderPageRequest;
import com.example.springboot.controller.requst.UserOrderPageRequest;
import com.example.springboot.entity.Goods;
import com.example.springboot.entity.Order;
import com.example.springboot.service.IGoodsService;
import com.example.springboot.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin //解决跨域错误
@RestController //以json格式返回
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    IGoodsService goodsService;

//    查询所有
    @GetMapping("/page")
    public Result page(GoodsPageRequest goodsPageRequest){
        return Result.success(goodsService.page(goodsPageRequest));
    }

    @GetMapping("/findall")
    public Result findall(){
        return Result.success(goodsService.findall());
    }
    //添加
    @PostMapping("/save")
    public Result save(@RequestBody Goods goods){
        goodsService.save(goods);
        return Result.success();
    }

//    移除
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        goodsService.deleteById(id);
        return Result.success();
    }

    @PutMapping("/update")
    public Result update(@RequestBody Goods goods) {
        goodsService.update(goods);
        return Result.success();
    }
}
