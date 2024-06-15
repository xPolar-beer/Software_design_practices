package com.example.springboot.mapper;

import com.example.springboot.controller.requst.AdminPageRequest;
import com.example.springboot.controller.requst.LoginRequest;
import com.example.springboot.entity.Admin;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminMapper {

    //查询所有管理员的信息
    List<Admin> list();

    //通过名字或者电话来定位管理员
    List<Admin> listByCondition(AdminPageRequest adminPageRequest);

    //新添管理员
    void save(Admin admin);

    //通过id来访问具体的管理员信息
    Admin getById(Integer id);

    //更新修改完后的信息
    void updateById(Admin admin);

    //删除管理员
    void deleteById(Integer id);

    //管理员登录
    Admin getByPhoneAndPassword(LoginRequest loginRequest);

}
