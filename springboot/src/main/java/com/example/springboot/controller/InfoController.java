package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.controller.requst.BaseRequest;
import com.example.springboot.controller.requst.GoodsPageRequest;
import com.example.springboot.controller.requst.InfoPageRequest;
import com.example.springboot.entity.Goods;
import com.example.springboot.entity.Info;
import com.example.springboot.service.IGoodsService;
import com.example.springboot.service.impl.InfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin //解决跨域错误
@RestController //以json格式返回
@RequestMapping("/info")
public class InfoController {

    @Autowired
    InfoService infoService;

    //    查询所有
    @GetMapping("/page")
    public Result page(InfoPageRequest infoPageRequest){
        return Result.success(infoService.page(infoPageRequest));
    }

    @GetMapping("/list/{userid}")
    public Result list(BaseRequest baseRequest, @PathVariable Integer userid) {
        return  Result.success(infoService.list(baseRequest, userid));
    }
    //添加
    @PostMapping("/save")
    public Result save(@RequestBody Info info){
        infoService.save(info);
        return Result.success();
    }

    //    移除
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        infoService.deleteById(id);
        return Result.success();
    }

    @PutMapping("/update/{id}")
    public Result update(@PathVariable Integer id){
        infoService.updateById(id);
        return Result.success();
    }
}
