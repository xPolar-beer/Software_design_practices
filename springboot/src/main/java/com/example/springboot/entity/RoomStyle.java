package com.example.springboot.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RoomStyle {
    private Integer id; //房型编号
    private String style; //房型
    private Integer number; //房型数量
    private String avatar; //图片描述
    private String describes; //文字描述
    private BigDecimal price;
    private Integer greatnum;
}
