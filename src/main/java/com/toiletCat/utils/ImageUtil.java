package com.toiletCat.utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageUtil {

    private static final Logger logger = LoggerFactory.getLogger(ImageUtil.class);

    /**
     * 在源图片上设置水印文字
     * @param srcImagePath  源图片路径
     * @param alpha 透明度（0<alpha<1）
     * @param font  字体（例如：宋体）
     * @param fontStyle     字体格式(例如：普通样式--Font.PLAIN、粗体--Font.BOLD )
     * @param fontSize  字体大小
     * @param color 字体颜色(例如：黑色--Color.BLACK)
     * @param inputWords        输入显示在图片上的文字
     * @param x     文字显示起始的x坐标
     * @param y     文字显示起始的y坐标
     * @param imageFormat   写入图片格式（png/jpg等）
     * @param toPath    写入图片路径
     */
    public static Boolean alphaWords2Image(String srcImagePath, float alpha,
                                 String font, int fontStyle, int fontSize, Color color,
                                 String inputWords, int x, int y, String imageFormat, String toPath) {

        try (FileOutputStream fos = new FileOutputStream(toPath)) {

            BufferedImage image = ImageIO.read(new File(srcImagePath));

            // 创建java2D对象
            Graphics2D g2d=image.createGraphics();

            // 用源图像填充背景
            g2d.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null, null);

            // 设置透明度
            AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);

            g2d.setComposite(ac);

            // 设置文字字体名称、样式、大小
            g2d.setFont(new Font(font, fontStyle, fontSize));

            // 设置字体颜色
            g2d.setColor(color);

            // 输入水印文字及其起始x、y坐标
            g2d.drawString(inputWords, x, y);

            g2d.dispose();

            ImageIO.write(image, imageFormat, fos);

        } catch (Exception e) {

            logger.error("alphaWords2Image error: " + e.getMessage(), e);

            return false;
        }

        return true;
    }



    public static void main(String[] args) throws IOException {

        String imgSrc = "E:\\gitWorkSpeace\\wawaji\\src\\main\\webapp\\image\\share\\share.png";



        float alpha = 0.9f;

        String font = "宋体";

        int fontStyle = Font.BOLD;

        int fontSize = 80;

        Color color = Color.decode("#824121");

        String inputWords = "ESDC5";

        int x = 400;

        int y = 790;

        String imageFormat = "png";

        String toPath = "D://test1.png";

        String dd = "D://test2.jpg";

        alphaWords2Image(imgSrc, alpha, font, fontStyle, fontSize, color, inputWords, x, y, imageFormat, toPath);
        
    }
}
