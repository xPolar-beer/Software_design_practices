package com.example.springboot.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.example.springboot.common.Result;
import com.example.springboot.entity.Admin;
import com.example.springboot.entity.HomeView;
import com.example.springboot.service.IAdminService;
import com.example.springboot.service.IHomeViewService;
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
@RequestMapping("/homeview")
public class HomeViewController {

    @Autowired
    IHomeViewService homeViewService;

    //更新
    @PutMapping("/update")
    public Result update(@RequestBody HomeView homeView){
        homeViewService.update(homeView);
        return Result.success();
    }

    //查询单个
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        HomeView homeView = homeViewService.getById(id);
        return Result.success(homeView);
    }

    //查询所有
    @GetMapping("/list")
    public Result list(){
        List<HomeView> homeViews = homeViewService.list();
        return Result.success(homeViews);
    }

    private static final String BASE_FILE_PATH = System.getProperty("user.dir") + "/files/"; //根路径

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
            String url = "http://localhost:9090/api/homeview/the/file/download/" + flag + "?&token=" + token;
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
