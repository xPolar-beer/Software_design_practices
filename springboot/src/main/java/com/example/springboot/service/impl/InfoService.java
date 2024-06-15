package com.example.springboot.service.impl;


import cn.hutool.core.date.DateUtil;
import com.example.springboot.controller.requst.BaseRequest;
import com.example.springboot.controller.requst.GoodsPageRequest;
import com.example.springboot.controller.requst.InfoPageRequest;
import com.example.springboot.entity.Goods;
import com.example.springboot.entity.Info;
import com.example.springboot.mapper.GoodsMapper;
import com.example.springboot.mapper.InfoMapper;
import com.example.springboot.service.IGoodsService;
import com.example.springboot.service.IInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service //把它标注为Spring的组件，这样外部才能去调用，会将当前类自动注入到spring容器中，标注在具体的实现类上面
public class InfoService implements IInfoService {
    @Autowired
    InfoMapper infoMapper;

    @Override
    public void save(Info info) {
        Date date = new Date();
        info.setTime(java.sql.Date.valueOf(DateUtil.format(date, "yyyy-MM-dd")));
        infoMapper.save(info);
    }

    //查询所有
    @Override
    public Object page(InfoPageRequest infoPageRequest) {
        PageHelper.startPage(infoPageRequest.getPageNum(), infoPageRequest.getPageSize());
        List<Info> infos = infoMapper.listByCondition(infoPageRequest);
        return new PageInfo<>(infos);
    }

    @Override
    public Object list(BaseRequest baseRequest, Integer userid) {
        PageHelper.startPage(baseRequest.getPageNum(), baseRequest.getPageSize());
        List<Info> infos = infoMapper.listByUserId(baseRequest, userid);
        return new PageInfo<>(infos);
    }

    @Override
    public void deleteById(Integer id) { infoMapper.deleteById(id); }

    @Override
    public void updateById(Integer id) { infoMapper.updateById(id); }

    private class BasePageRequest {
    }
}
