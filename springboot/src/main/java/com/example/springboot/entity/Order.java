package com.example.springboot.entity;

import com.example.springboot.controller.requst.BaseRequest;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.sql.Date;

@Data
public class Order {
    private Integer id;
    private Integer userid;
    private String roomnumber;
    private String number;
    private String style;
    private BigDecimal money;
    private Integer day;
    private Integer state;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date reservetime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date establishtime;
}
