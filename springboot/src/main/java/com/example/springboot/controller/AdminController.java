package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.controller.requst.AdminPageRequest;
import com.example.springboot.controller.requst.LoginRequest;
import com.example.springboot.entity.Admin;
import com.example.springboot.service.IAdminService;
import com.example.springboot.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    IAdminService adminService;

    //登录请求
    @PostMapping("/login")
    public Result login(@RequestBody LoginRequest request){

        return Result.success(adminService.login(request));
    }
    //保存（插入）
    @PostMapping("/save")
    public Result save(@RequestBody Admin admin){
        adminService.save(admin);
        return Result.success();
    }

    //更新
    @PutMapping("/update")
    public Result update(@RequestBody Admin admin){
        adminService.update(admin);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        adminService.deleteById(id);
        return Result.success();
    }

    //查询单个
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        Admin admin = adminService.getById(id);
        return Result.success(admin);
    }

    //查询所有
    @GetMapping("/list")
    public Result list(){
        List<Admin> admins = adminService.list();
        return Result.success(admins);
    }


    @GetMapping("/page")
    public Result page(AdminPageRequest PageRequest){
        return Result.success(adminService.page(PageRequest));
    }
}
