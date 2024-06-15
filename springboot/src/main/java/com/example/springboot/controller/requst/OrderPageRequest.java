package com.example.springboot.controller.requst;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;

@Data
public class OrderPageRequest extends BaseRequest {
    private String number;
    private String reservetime;
}
