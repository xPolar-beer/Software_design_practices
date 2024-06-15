package com.example.springboot.mapper;

import com.example.springboot.controller.requst.CommentPageRequest;
import com.example.springboot.controller.requst.LoginRequest;
import com.example.springboot.controller.requst.PasswordRequest;
import com.example.springboot.controller.requst.UserPageRequest;
import com.example.springboot.entity.Comment;
import com.example.springboot.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface CommentMapper {

    List<Comment> listbyroomid(Integer roomid);

    List<Comment> listbycommentid(Integer commentid);

    List<Comment> listbyuserid(Integer userid);

    void savecomment(Comment comment);

    void savereply(Comment comment);

    void deleteById(Integer id);

    void updategreatnum(Integer id);

    List<Comment> listByCondition(CommentPageRequest commentPageRequest);

    List<Comment> userlistByCondition(CommentPageRequest commentPageRequest);
}
