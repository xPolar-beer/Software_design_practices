package com.example.springboot.mapper;

import com.example.springboot.controller.requst.BaseRequest;
import com.example.springboot.controller.requst.GoodsPageRequest;
import com.example.springboot.controller.requst.InfoPageRequest;
import com.example.springboot.entity.Goods;
import com.example.springboot.entity.Info;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface InfoMapper {

    void save(Info info);

    List<Info> listByCondition(InfoPageRequest infoPageRequest);

    List<Info> listByUserId(BaseRequest baseRequest, Integer userid);

    void deleteById(Integer id);

    void updateById(Integer id);
}
