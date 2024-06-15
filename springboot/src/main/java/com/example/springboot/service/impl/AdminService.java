package com.example.springboot.service.impl;

import com.example.springboot.controller.dto.AdminLoginDTO;
import com.example.springboot.controller.dto.UserLoginDTO;
import com.example.springboot.controller.requst.AdminPageRequest;
import com.example.springboot.controller.requst.LoginRequest;
import com.example.springboot.entity.Admin;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.mapper.AdminMapper;
import com.example.springboot.service.IAdminService;
//import com.example.springboot.utils.TokenUtils;
import com.example.springboot.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class AdminService implements IAdminService {

    @Autowired
    AdminMapper adminMapper;

    @Override
    public List<Admin> list() {
        return adminMapper.list();
    }

    @Override
    public Object page(AdminPageRequest adminPageRequest) {
        PageHelper.startPage(adminPageRequest.getPageNum(), adminPageRequest.getPageSize());
        List<Admin> admins = adminMapper.listByCondition(adminPageRequest);
        return new PageInfo<>(admins);
    }

    @Override
    public void save(Admin admin) {
        Date date = new Date();
        adminMapper.save(admin);
    }

    @Override
    public Admin getById(Integer id) {
        return adminMapper.getById(id);
    }

    @Override
    public void update(Admin admin) {
        adminMapper.updateById(admin);
    }

    @Override
    public void deleteById(Integer id) {
        adminMapper.deleteById(id);
    }

    @Override
    public AdminLoginDTO login(LoginRequest request) {
        Admin admin = adminMapper.getByPhoneAndPassword(request);
        if(admin == null) {
            throw new ServiceException("账号或者密码错误");
        }
        AdminLoginDTO adminLoginDTO = new AdminLoginDTO();
        BeanUtils.copyProperties(admin, adminLoginDTO);

        //获取token，传给前台
        String token = TokenUtils.admingenToken(String.valueOf(admin.getId()), admin.getPassword());

        adminLoginDTO.setToken(token);
        return adminLoginDTO;
    }
}
