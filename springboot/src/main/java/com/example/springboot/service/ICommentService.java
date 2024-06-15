package com.example.springboot.service;

import com.example.springboot.controller.dto.UserLoginDTO;
import com.example.springboot.controller.requst.CommentPageRequest;
import com.example.springboot.controller.requst.LoginRequest;
import com.example.springboot.controller.requst.PasswordRequest;
import com.example.springboot.controller.requst.UserPageRequest;
import com.example.springboot.entity.Comment;
import com.example.springboot.entity.User;

import java.math.BigDecimal;
import java.util.List;

public interface ICommentService {
//interface声明一个接口

    void savecomment(Comment comment);

    void savereply(Comment comment);

    void deleteById(Integer id);

    List<Comment> listbyroomid(Integer roomid);

    List<Comment> listbycommentid(Integer commentid);

    List<Comment> listbyuserid(Integer userid);

    void updategreatnum(Integer id);

    Object userpage(CommentPageRequest commentPageRequest);

    Object page(CommentPageRequest commentPageRequest);
}
