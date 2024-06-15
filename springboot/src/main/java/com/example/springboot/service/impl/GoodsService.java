package com.example.springboot.service.impl;


import cn.hutool.core.date.DateUtil;
import com.example.springboot.controller.requst.GoodsPageRequest;
import com.example.springboot.entity.Goods;
import com.example.springboot.entity.User;
import com.example.springboot.mapper.GoodsMapper;
import com.example.springboot.service.IGoodsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service //把它标注为Spring的组件，这样外部才能去调用，会将当前类自动注入到spring容器中，标注在具体的实现类上面
public class GoodsService implements IGoodsService {
    @Autowired
    GoodsMapper goodsMapper;

    //查询所有
    @Override
    public Object page(GoodsPageRequest goodsPageRequest) {
        PageHelper.startPage(goodsPageRequest.getPageNum(), goodsPageRequest.getPageSize());
        List<Goods> goodss = goodsMapper.listByCondition(goodsPageRequest);
        return new PageInfo<>(goodss);
    }

    @Override
    public List<String> findall() {
        List<String> names = goodsMapper.listGoodsName();
        return names;
    }

    @Override
    public void save(Goods goods) {
        Date date = new Date();
        goods.setLasttime(java.sql.Date.valueOf(DateUtil.format(date, "yyyy-MM-dd")));
        goodsMapper.save(goods);
    }

    @Override
    public void deleteById(Integer id) { goodsMapper.deleteById(id); }

//
    @Override
    public void update(Goods goods) {
        Date date = new Date();
        goods.setLasttime(java.sql.Date.valueOf(DateUtil.format(date, "yyyy-MM-dd")));
        goodsMapper.updateById(goods);
    }
}
