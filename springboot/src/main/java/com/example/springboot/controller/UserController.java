package com.example.springboot.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.example.springboot.common.Result;
import com.example.springboot.controller.requst.LoginRequest;
import com.example.springboot.controller.requst.PasswordRequest;
import com.example.springboot.controller.requst.UserPageRequest;
import com.example.springboot.entity.Admin;
import com.example.springboot.entity.User;
import com.example.springboot.service.IUserService;
import com.example.springboot.utils.TokenUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.List;

@Slf4j
@CrossOrigin //解决跨域错误
@RestController //以json格式返回
@RequestMapping("/user")
public class UserController {

    @Autowired
    IUserService userService;

    private static final String BASE_FILE_PATH = System.getProperty("user.dir") + "/files/"; //根路径

    //上传接口
    @PostMapping("/the/file/upload")
    public Result uploadFile(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (StrUtil.isBlank(originalFilename)) {
            return Result.error("文件上传失败");
        }
        long flag = System.currentTimeMillis();
        String filePath = BASE_FILE_PATH + flag + "_" + originalFilename; //生成文件路径
        try {
            FileUtil.mkParentDirs(filePath); //创建父级目录
            file.transferTo(FileUtil.file(filePath));
            User currentUser = TokenUtils.getCurrentUser();
            String token = TokenUtils.usergenToken(currentUser.getId().toString(), currentUser.getPassword(), 15);
            String url = "http://localhost:9090/api/user/the/file/download/" + flag + "?&token=" + token;
            if (originalFilename.endsWith("png") || originalFilename.endsWith("jpg") || originalFilename.endsWith("pdf")) {
                url += "&play=1";
            }
            return Result.success(url);
//            return Result.success("http://localhost:9090/api/user/file/download/" + flag + "?token=");
        } catch (Exception e) {
            log.info("文件上传失败", e);
        }
        return Result.error("文件上传失败");
        //获取到当前系统的文件夹

    }

    //下载接口(提供一个接口来下载文件路径)
    @GetMapping("/the/file/download/{flag}")
    public void downloadFile(@PathVariable String flag, @RequestParam(required = false) String play, HttpServletResponse response) {
        OutputStream os;
        List<String> fileNames = FileUtil.listFileNames(BASE_FILE_PATH);
        String fileName = fileNames.stream().filter(name -> name.contains(flag)).findAny().orElse("");
        try {
            if (StrUtil.isNotEmpty(fileName)) {
                String realName = fileName.substring(fileName.indexOf("_") + 1);
                if ("1".equals(play)) {
                    response.addHeader("Content-Disposition", "inline;filename=" + URLEncoder.encode(realName, "UTF-8"));
                //预览
                } else {
                    response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(realName, "UTF-8"));
                }
                byte[] bytes = FileUtil.readBytes(BASE_FILE_PATH + fileName);
                os = response.getOutputStream();
                os.write(bytes);
                os.flush();
                os.close();
            }
        } catch (Exception e) {
            log.error("文件下载失败", e);
        }
    }

    //登录请求
    @PostMapping("/the/login")
    public Result login(@RequestBody LoginRequest request){
        return Result.success(userService.login(request));
    }

    //修密码请求
    @PostMapping("/passwordrequest")
    public Result passwordrequest(@RequestBody PasswordRequest request){
        return Result.success(userService.passwordrequest(request));
    }

    @PutMapping("/updatepassword/{id}/{pass}")
    public Result updatepassword(@PathVariable Integer id, @PathVariable String pass){
        userService.updatepassword(id, pass);
        return Result.success();
    }

    //保存（插入）
    @PostMapping("/save")
    public Result save(@RequestBody User user){
        userService.save(user);
        return Result.success();
    }

    //更新
    @PutMapping("/update")
    public Result update(@RequestBody User user){
        userService.update(user);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        userService.deleteById(id);
        return Result.success();
    }

    //查询单个
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        User user = userService.getById(id);
        return Result.success(user);
    }

    //查询所有
    @GetMapping("/list")
    public Result list(){
        List<User> users = userService.list();
        return Result.success(users);
    }

    @GetMapping("/page")
    public Result page(UserPageRequest userPageRequest){
        return Result.success(userService.page(userPageRequest));
    }

    @PutMapping("/updatemoney/{money}/{id}")
    public Result updatemoney(@PathVariable BigDecimal money, @PathVariable Integer id){
        userService.updatemoney(money, id);
        return Result.success();
    }

    @PutMapping("/updatemoneymoney/{id}/{money}")
    public Result updatemoney(@PathVariable Integer id, @PathVariable BigDecimal money){
        userService.updatemoneymoney(id, money);
        return Result.success();
    }

    @PutMapping("/updatestate/{id}/{state}")
    public Result updatestate(@PathVariable Integer id, @PathVariable Integer state){
        userService.updatestate(id, state);
        return Result.success();
    }
}
