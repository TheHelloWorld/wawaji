package com.lzg.wawaji.controller;

import com.lzg.wawaji.utils.DateUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;

@RequestMapping("/wawaji/fileUpload")
@Controller
public class FileUploadController {


    @RequestMapping(value = "uploadFile",method= RequestMethod.POST)
    @ResponseBody
    public String uploadFile(Model model, HttpServletRequest request) {

        System.out.println("123123123213");
        // 获取项目目录
        String rootPath = request.getSession().getServletContext().getRealPath("/");
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
        String key = null;
        // 目前只有一个文件所以可以这样，多文件需要将读写逻辑写入for循环
        for(Map.Entry<String,MultipartFile> entry : multipartRequest.getFileMap().entrySet()) {
            key = entry.getKey();
        }
        // 根据key获得文件内容
        MultipartFile multipartFile = multipartRequest.getFile(key);
        // 获得文件名
        String originFileName = multipartFile.getOriginalFilename();
        // 获取文件类型
        String prefix = originFileName.substring(originFileName.lastIndexOf("."));
        // 放到files文件夹下
        rootPath += "/files";
        String fileName = DateUtil.getSecondDate();
        fileName += prefix;
        try {
            // 写文件
            FileOutputStream fos = new FileOutputStream(new File(rootPath,fileName));
            fos.write(multipartFile.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        fileName = "files/"+fileName;
        return fileName;
    }
}
