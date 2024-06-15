package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.entity.Collect;
import com.example.springboot.service.ICollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/collect")
public class CollectController {

    @Autowired
    ICollectService collectService;

    //保存（插入）
    @PostMapping("/save")
    public Result save(@RequestBody Collect collect){
        collectService.save(collect);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        collectService.deleteById(id);
        return Result.success();
    }

    //查询单个
    @GetMapping("/{userid}/{roomstyleid}")
    public Result getById(@PathVariable Integer userid, @PathVariable Integer roomstyleid){
        Collect collect = collectService.getById(userid, roomstyleid);
        return Result.success(collect);
    }

    //查询所有
    @GetMapping("/list/{userid}")
    public Result list(@PathVariable Integer userid){
        List<Collect> collects = collectService.list(userid);
        return Result.success(collects);
    }
}
