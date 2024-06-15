package com.example.springboot.service.impl;

import cn.hutool.core.date.DateUtil;
import com.example.springboot.controller.dto.UserLoginDTO;
import com.example.springboot.controller.requst.LoginRequest;
import com.example.springboot.controller.requst.PasswordRequest;
import com.example.springboot.controller.requst.UserPageRequest;
import com.example.springboot.entity.User;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.mapper.UserMapper;
import com.example.springboot.service.IUserService;
import com.example.springboot.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Slf4j
@Service //把它标注为Spring的组件，这样外部才能去调用，会将当前类自动注入到spring容器中，标注在具体的实现类上面
public class UserService implements IUserService {
//implements声明自己使用一个或者多个接口，类UserService来实现接口IUserService中的方法
    @Autowired
    UserMapper userMapper;

    @Override
    //此注释表示子类重写了父类的方法
    public List<User> list() {
        return userMapper.list();
    }

    //查询所有
    @Override
    public Object page(UserPageRequest userPageRequest) {
        PageHelper.startPage(userPageRequest.getPageNum(), userPageRequest.getPageSize());
        List<User> users = userMapper.listByCondition(userPageRequest);
        return new PageInfo<>(users);
    }

    //
    @Override
    public void save(User user) {
        Date date = new Date();
        user.setCreatetime(java.sql.Date.valueOf(DateUtil.format(date, "yyyy-MM-dd")));
        userMapper.save(user);
    }

    //获取某个
    @Override
    public User getById(Integer id) {
        return userMapper.getById(id);
    }

    @Override
    public void update(User user) {
//        设置更新时间
        userMapper.updateById(user);
    }

    @Override
    public void deleteById(Integer id) {
        userMapper.deleteById(id);
    };

    @Override
    public UserLoginDTO login(LoginRequest request) {
        User user = userMapper.getByPhoneAndPassword(request);
        if(user == null) {
            throw new ServiceException("账号或者密码错误");
        }
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        BeanUtils.copyProperties(user, userLoginDTO);

        //获取token，传给前台
        String token = TokenUtils.usergenToken(String.valueOf(user.getId()), user.getPassword());
        userLoginDTO.setToken(token);

        return userLoginDTO;
    }

    @Override
    public String passwordrequest(PasswordRequest request) {
        User user = userMapper.getByIdAndOldpass(request);
        if(user == null) {
            throw new ServiceException("密码错误");
        }
        return "成功";
    }

    @Override
    public void updatepassword(Integer id, String pass) {
//        设置更新时间
        userMapper.updatePassword(id, pass);
    }

    @Override
    public void updatemoney(BigDecimal money, Integer id) {
        userMapper.updatemoney(money, id);
    }
    //实现这个方法
    @Override
    public void updatemoneymoney(Integer id, BigDecimal money) {
        userMapper.updatemoneyByMoney(id, money);
    }

    @Override
    public void updatestate(Integer id, Integer state) {
        userMapper.updatestateById(id, state);
    }
}
