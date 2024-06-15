package com.example.springboot.mapper;

import com.example.springboot.controller.requst.LoginRequest;
import com.example.springboot.controller.requst.PasswordRequest;
import com.example.springboot.controller.requst.UserPageRequest;
import com.example.springboot.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface UserMapper {
//定义了一个UserMapper的接口，其中有一个方法listUsers()
    //@Select("select * from users")
    List<User> list(); //List<User>类型的一个listUsers()的方法

    List<User> listByCondition(UserPageRequest userPageRequest);

    void save(User user);

    User getById(Integer id);

    void updateById(User user);

    void deleteById(Integer id);

    User getByPhoneAndPassword(LoginRequest request);

    void updatemoney(BigDecimal money, Integer id);

    void updatemoneyByMoney(Integer id, BigDecimal money);

    User getByIdAndOldpass(PasswordRequest request);

    void updatePassword(Integer id, String pass);

    void updatestateById(Integer id, Integer state);
}
