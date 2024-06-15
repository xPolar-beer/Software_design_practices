package com.example.springboot.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.example.springboot.common.Result;
import com.example.springboot.controller.requst.CommentPageRequest;
import com.example.springboot.controller.requst.LoginRequest;
import com.example.springboot.controller.requst.OrderPageRequest;
import com.example.springboot.controller.requst.PasswordRequest;
import com.example.springboot.entity.Comment;
import com.example.springboot.service.ICommentService;
import com.example.springboot.utils.TokenUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.List;

@Slf4j
@CrossOrigin //解决跨域错误
@RestController //以json格式返回
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    ICommentService commentService;

    //发表评论
    @PostMapping("/savecomment")
    public Result savecomment(@RequestBody Comment comment){
        commentService.savecomment(comment);
        return Result.success();
    }

    //发表回复
    @PostMapping("/savereply")
    public Result savereply(@RequestBody Comment comment){
        commentService.savereply(comment);
        return Result.success();
    }

    //通过评论id删除评价及回复
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        commentService.deleteById(id);
        return Result.success();
    }

    //通过房型查询所有评论
    @GetMapping("/listbyroomid/{roomid}")
    public Result listbyroomid(@PathVariable Integer roomid){
        List<Comment> comments = commentService.listbyroomid(roomid);
        return Result.success(comments);
    }

    //通过评论id查询所有回复
    @GetMapping("/listbycommentid/{commentid}")
    public Result listbycommentid(@PathVariable Integer commentid){
        List<Comment> comments = commentService.listbycommentid(commentid);
        return Result.success(comments);
    }

    //通过用户id查询所有评论和回复
    @GetMapping("/listbyuserid/{userid}")
    public Result listbyuserid(@PathVariable Integer userid){
        List<Comment> comments = commentService.listbyuserid(userid);
        return Result.success(comments);
    }

    @PutMapping("/updategreatnum/{id}")
    public Result updategreatnum(@PathVariable Integer id){
        commentService.updategreatnum(id);
        return Result.success();
    }

    @GetMapping("/userpage")
    public Result userpage(CommentPageRequest commentPageRequest){
        return Result.success(commentService.userpage(commentPageRequest));
    }

    @GetMapping("/page")
    public Result page(CommentPageRequest commentPageRequest){
        return Result.success(commentService.page(commentPageRequest));
    }

}
