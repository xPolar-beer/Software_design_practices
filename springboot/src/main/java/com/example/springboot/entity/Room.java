package com.example.springboot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.criteria.CriteriaBuilder;
import java.sql.Date;

@Data
public class Room {
    private Integer id; //房间id
    private String roomnumber; //房间号
    private Integer userid; //住户id
    private String style; //房间类型
    private Integer state; //房间状态
    private Integer up;//订单状态
    private String ordernumber;//订单号
    private String intime;
    private Integer day;
    private String user; //当前住户
    private String lastuser; //上一次用户
}
