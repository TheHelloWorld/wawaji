package com.toiletCat.controller;

import com.alibaba.fastjson.JSONObject;
import com.toiletCat.utils.DateUtil;
import com.toiletCat.utils.PropertiesUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;

@RequestMapping("/toiletCat/fileUpload")
@Controller
public class FileUploadController {

    @RequestMapping(value = "uploadFile",method= RequestMethod.POST)
    @ResponseBody
    public String uploadFile(HttpServletRequest request) {

        PropertiesUtil systemProperties = PropertiesUtil.getInstance("system");

        // 获取图片储存目录
        String savePath = systemProperties.getProperty("toy_img_save_path");

        // 获取图片展示目录
        String showPath = systemProperties.getProperty("toy_img_show_path");

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

        String fileName = DateUtil.getSecondDate();
        fileName += prefix;
        try {
            // 写文件
            FileOutputStream fos = new FileOutputStream(new File(savePath, fileName));
            fos.write(multipartFile.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        fileName = showPath + fileName;
        JSONObject json = new JSONObject();
        json.put("fileName", fileName);
        return json.toJSONString();
    }
}
