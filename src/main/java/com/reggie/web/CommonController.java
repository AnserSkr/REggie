package com.reggie.web;

import com.reggie.common.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

/**
 * @author 98248
 * @Date: 2022/9/25 - 09 - 25 - 22:56
 * @Description: com.reggie.web
 * @version: 1.0
 */
@RestController
@RequestMapping("/common")
public class CommonController {
    @Value("${reggie.path}")
    String basepath;

    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file){
        //获取上传文件的名称，带后缀
        String originalFilename = file.getOriginalFilename();
        //获取上传文件名称，以及文件后缀
        String perfix = originalFilename.substring(0, originalFilename.lastIndexOf("."));
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        //为防止上传文件名字相同而导致，使用UUID作为文件名
        String filename = UUID.randomUUID().toString()+suffix;

        // 判断上传的文件夹是否存在，如果不存在，创建文件夹
        File file1 = new File(basepath);
        if(!file1.exists()){
            //如果上级文件也不存在，一并创建
            file1.mkdirs();
        }
        //SpringMVC将文件封装到mutipartFile中会作为一个.tmp的临时文件进行缓存。
        //将临时文件转存到指定位置
        try {
            file.transferTo(new File(basepath + filename));
            return Result.success(filename);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error("未知错误上传失败");
        }
    }

    @GetMapping("/download")
    public void download(HttpServletResponse response,String name){
        //得到要获取的文件对象
        File file = new File(basepath + name);
        try {
            //创建输入流将文件读取到JVM中
            FileInputStream fileInputStream = new FileInputStream(file);
            //创建将文件写回到前端的输出流
            ServletOutputStream outputStream = response.getOutputStream();

            // 创建输入流的缓存数组
            byte[] bytes = new byte[1024];
            while(fileInputStream.read(bytes)!=-1){
                outputStream.write(bytes);
                outputStream.flush();
            }
            outputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
