package com.example.springboot.mapper.po;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MoneyCount {
    private String date;
    private BigDecimal count;
}
