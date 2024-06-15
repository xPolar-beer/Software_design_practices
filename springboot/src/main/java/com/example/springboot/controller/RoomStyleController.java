package com.example.springboot.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.example.springboot.common.Result;
import com.example.springboot.controller.requst.RoomPageRequest;
import com.example.springboot.controller.requst.RoomStyleRequest;
import com.example.springboot.entity.Admin;
import com.example.springboot.entity.RoomStyle;
import com.example.springboot.entity.User;
import com.example.springboot.service.IRoomService;
import com.example.springboot.service.IRoomStyleService;
import com.example.springboot.utils.TokenUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/roomstyle")
public class RoomStyleController {

    @Autowired
    IRoomStyleService roomstyleService;

    @GetMapping("/page")
    public Result page(RoomStyleRequest PageRequest){
        return Result.success(roomstyleService.page(PageRequest));
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        RoomStyle roomStyle = roomstyleService.getById(id);
        return Result.success(roomStyle);
    }

    @PutMapping("/update")
    public Result update(@RequestBody RoomStyle roomStyle) {
        roomstyleService.update(roomStyle);
        return Result.success();
    }
    //点赞
    @PutMapping("/great/{id}")
    public Result great(@PathVariable Integer id){
        roomstyleService.great(id);
        return Result.success();
    }

    @PutMapping("/updatenumber/{style}")
    public Result updatenumber(@PathVariable String style){
        roomstyleService.updatenumber(style);
        return Result.success();
    }

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
            Admin currentAdmin = TokenUtils.getCurrentAdmin();
            String token = TokenUtils.admingenToken(currentAdmin.getId().toString(), currentAdmin.getPassword(), 15);
            String url = "http://localhost:9090/api/roomstyle/the/file/download/" + flag + "?&token=" + token;
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
                //根据下划线去截取
                if ("1".equals(play)) {
                    //如果是可以预览的图片
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



}
