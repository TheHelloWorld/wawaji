package com.toiletCat.controller;

import com.alibaba.fastjson.JSONObject;
import com.toiletCat.constants.BaseConstant;
import com.toiletCat.utils.DateUtil;
import com.toiletCat.utils.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@RequestMapping("/toiletCat/api/fileUpload")
@Controller
public class FileUploadController {

    private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    /**
     * 玩具图片上传
     * @param request 请求
     * @return
     */
    @RequestMapping(value = "toyImgUpload",method= RequestMethod.POST)
    @ResponseBody
    public String toyImgUpload(HttpServletRequest request) {

        logger.info(BaseConstant.LOG_MSG + " toyImgUpload start");

        PropertiesUtil systemProperties = PropertiesUtil.getInstance("system");

        // 获取图片储存目录
        String savePath = systemProperties.getProperty("toy_img_save_path");

        // 获取图片展示目录
        String showPath = systemProperties.getProperty("toy_img_show_path");

        return commonImgUpload(request, savePath, showPath);
    }

    /**
     * banner图片上传
     * @param request 请求
     * @return
     */
    @RequestMapping(value = "bannerImgUpload",method= RequestMethod.POST)
    @ResponseBody
    public String bannerImgUpload(HttpServletRequest request) {

        logger.info(BaseConstant.LOG_MSG + " bannerImgUpload start");

        PropertiesUtil systemProperties = PropertiesUtil.getInstance("system");

        // 获取图片储存目录
        String savePath = systemProperties.getProperty("banner_img_save_path");

        // 获取图片展示目录
        String showPath = systemProperties.getProperty("banner_img_show_path");

        return commonImgUpload(request, savePath, showPath);
    }

    /**
     * 公共图片上传方法
     * @param request 图片上传请求
     * @param savePath 图片储存路径
     * @param showPath 图片展示路径
     * @return
     */
    private String commonImgUpload(HttpServletRequest request, String savePath, String showPath) {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

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

        try (FileOutputStream fos = new FileOutputStream(new File(savePath, fileName))){
            // 写文件
            fos.write(multipartFile.getBytes());
        } catch (Exception e) {
            logger.error(BaseConstant.LOG_ERR_MSG + " commonImgUpload error:" + e, e);
        }

        fileName = showPath + fileName;

        logger.info(BaseConstant.LOG_MSG + " commonImgUpload fileName:" + fileName);
        JSONObject json = new JSONObject();
        json.put("fileName", fileName);
        return json.toJSONString();
    }
}
