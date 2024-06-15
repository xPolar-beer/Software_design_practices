package com.example.springboot.common;

import lombok.Data;

@Data
//统一返回结果的包装
public class Result {
    private static final String SUCCESS_CODE = "200";
    private static final String ERROR_CODE = "-1";
    //static表示其定义的变量或者方法为静态的，属于类，而不属于类的对象
    //被final修饰的只能进行一次初始化，即可理解为常量

    private String code; //
    private Object data; //
    private String msg; //存储错误的信息

    public static Result success() {
        Result result = new Result();
        result.setCode(SUCCESS_CODE);
        return result;
    }

    public static Result success(Object data) {
        Result result = new Result();
        result.setCode(SUCCESS_CODE);
        result.setData(data);
        return result;
    }

//    public static Result successbacknumber() {
//        Result result = new Result();
//        result.setCode(SUCCESS_CODE);
//        result.setData(data);
//        return result;
//    }

    public static Result error(String msg) {
        Result result = new Result();
        result.setCode(ERROR_CODE);
        result.setMsg(msg);
        return result;
    }

    public static Result error(String code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
}
