package com.toiletCat.utils;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ImageUtil {

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
     * @throws IOException
     */
    private static void alphaWords2Image(String srcImagePath,float alpha,
                                 String font,int fontStyle, int fontSize, Color color,
                                 String inputWords,int x,int y,String imageFormat,String toPath) throws IOException{
        FileOutputStream fos=null;
        try {
            BufferedImage image = ImageIO.read(new File(srcImagePath));
            //创建java2D对象
            Graphics2D g2d=image.createGraphics();
            //用源图像填充背景
            g2d.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null, null);
            //设置透明度
            AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
            g2d.setComposite(ac);
            //设置文字字体名称、样式、大小
            g2d.setFont(new Font(font, fontStyle, fontSize));

            g2d.setColor(color);//设置字体颜色
            g2d.drawString(inputWords, x, y); //输入水印文字及其起始x、y坐标

            g2d.dispose();
            fos=new FileOutputStream(toPath);
            ImageIO.write(image, imageFormat, fos);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(fos!=null){
                fos.close();
            }
        }
    }

    public static void main(String[] args) throws IOException {

        String imgSrc = "E:\\gitWorkSpeace\\wawaji\\src\\main\\webapp\\image\\share\\share.png";



        float alpha = 0.9f;

        String font = "宋体";

        int fontStyle = Font.BOLD;

        int fontSize = 100;

        Color color = Color.decode("#824121");

        String inputWords = "ESDC5";

        int x = 600;

        int y = 1306;

        String imageFormat = "png";

        String toPath = "D://tttt1.png";

        alphaWords2Image(imgSrc, alpha, font, fontStyle, fontSize, color, inputWords, x, y, imageFormat, toPath);
    }
}
