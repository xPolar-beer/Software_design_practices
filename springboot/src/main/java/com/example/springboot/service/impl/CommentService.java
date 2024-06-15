package com.example.springboot.service.impl;

import cn.hutool.core.date.DateUtil;
import com.example.springboot.controller.requst.CommentPageRequest;
import com.example.springboot.controller.requst.LoginRequest;
import com.example.springboot.controller.requst.OrderPageRequest;
import com.example.springboot.controller.requst.PasswordRequest;
import com.example.springboot.entity.Comment;
import com.example.springboot.entity.Order;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.mapper.CommentMapper;
import com.example.springboot.service.ICommentService;
import com.example.springboot.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Slf4j
@Service //把它标注为Spring的组件，这样外部才能去调用，会将当前类自动注入到spring容器中，标注在具体的实现类上面
public class CommentService implements ICommentService {
//implements声明自己使用一个或者多个接口，类CommentService来实现接口ICommentService中的方法
    @Autowired
    CommentMapper commentMapper;

    //此注释表示子类重写了父类的方法
    @Override
    public List<Comment> listbyroomid(Integer roomid) {
        return commentMapper.listbyroomid(roomid);
    }

    @Override
    public List<Comment> listbycommentid(Integer commentid) {
        return commentMapper.listbycommentid(commentid);
    }

    @Override
    public List<Comment> listbyuserid(Integer userid) {
        return commentMapper.listbyuserid(userid);
    }

    //
    @Override
    public void savecomment(Comment comment) {
        Date date = new Date();
        comment.setCreatetime(java.sql.Date.valueOf(DateUtil.format(date, "yyyy-MM-dd")));
        commentMapper.savecomment(comment);
    }

    @Override
    public void savereply(Comment comment) {
        Date date = new Date();
        comment.setCreatetime(java.sql.Date.valueOf(DateUtil.format(date, "yyyy-MM-dd")));
        commentMapper.savereply(comment);
    }

    @Override
    public void deleteById(Integer id) {
        commentMapper.deleteById(id);
    }

    @Override
    public void updategreatnum(Integer id) {
        commentMapper.updategreatnum(id);
    }

    @Override
    public Object page(CommentPageRequest commentPageRequest) {
        PageHelper.startPage(commentPageRequest.getPageNum(), commentPageRequest.getPageSize());
        List<Comment> comments = commentMapper.listByCondition(commentPageRequest);
        return new PageInfo<>(comments);
    }

    @Override
    public Object userpage(CommentPageRequest commentPageRequest) {
        PageHelper.startPage(commentPageRequest.getPageNum(), commentPageRequest.getPageSize());
        List<Comment> comments = commentMapper.userlistByCondition(commentPageRequest);
        return new PageInfo<>(comments);
    }

}
