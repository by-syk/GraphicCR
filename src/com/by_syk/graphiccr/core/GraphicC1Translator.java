/**
 * Copyright 2016 By_syk
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.by_syk.graphiccr.core;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import com.by_syk.graphiccr.util.ExtraUtil;

/**
 * 第1类图形验证码识别
 * <br />针对截至 2016-11-22 为止东北大学秦皇岛分校教务系统登录用的验证码
 * <br />图形尺寸为 60*20
 * 
 * @author By_syk
 */
public class GraphicC1Translator {
    private static GraphicC1Translator translator = null;
    
    private Map<BufferedImage, Character> trainMap = null;
    
    /**
     * 元字符宽度
     */
    private static final int UNIT_W = 10;
    /**
     * 元字符高度
     */
    private static final int UNIT_H = 12;
    
    /**
     * 有效像素颜色值
     */
    private static final int TARGET_COLOR = Color.BLACK.getRGB();
    
    /**
     * 无效像素颜色值
     */
    private static final int USELESS_COLOR = Color.WHITE.getRGB();
    
    private GraphicC1Translator() {}
    
    public static GraphicC1Translator getInstance() {
        if (translator == null) {
            translator = new GraphicC1Translator();
        }
        
        return translator;
    }
    
    /**
     * 目标像素判断
     * <br />（基于亮度）
     * 
     * @param colorInt
     * @return
     */
    private boolean isTarget(int colorInt) {
        Color color = new Color(colorInt);
        float[] hsb = new float[3];
        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsb);
        return hsb[2] < 0.6f; // 亮度
    }

    /**
     * 去噪
     * 
     * @param picFile 图形验证码文件
     * @return
     * @throws Exception
     */
    private BufferedImage denoise(File picFile) throws Exception {
        BufferedImage img = ImageIO.read(picFile);
        int width = img.getWidth();
        int height = img.getHeight();
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                if (isTarget(img.getRGB(x, y))) {
                    img.setRGB(x, y, TARGET_COLOR);
                } else {
                    img.setRGB(x, y, USELESS_COLOR);
                }
            }
        }
        return img;
    }

    /**
     * 分割元字符
     * 
     * @param img
     * @return
     * @throws Exception
     */
    private List<BufferedImage> split(BufferedImage img) throws Exception {
        List<BufferedImage> subImgs = new ArrayList<BufferedImage>();
        subImgs.add(img.getSubimage(5, 4, UNIT_W, UNIT_H));
        subImgs.add(img.getSubimage(18, 4, UNIT_W, UNIT_H));
        subImgs.add(img.getSubimage(32, 4, UNIT_W, UNIT_H));
        subImgs.add(img.getSubimage(44, 4, UNIT_W, UNIT_H));
        return subImgs;
    }
    
    /**
     * 取出训练数据
     * 
     * @return
     * @throws Exception
     */
    private Map<BufferedImage, Character> loadTrainData() throws Exception {
        if (trainMap == null) {
            trainMap = new HashMap<>();
            String trainLog = ExtraUtil.readFile(this.getClass().getResourceAsStream("/resources/img/1/1.txt"));
            if (trainLog == null) {
                return trainMap;
            }
            for (String name : trainLog.split("\n")) {
                trainMap.put(ImageIO.read(this.getClass().getResourceAsStream("/resources/img/1/"
                        + name)), name.charAt(0));
            }
        }
        
        return trainMap;
    }

    /**
     * 训练
     */
    public void train() {
        // 由于样本具有很强的规律性，已通过 PS 训练完成
    }

    /**
     * 单元识别
     * 
     * @param img
     * @param trainImgMap
     * @return
     */
    private char recognize(BufferedImage img, Map<BufferedImage, Character> trainImgMap) {
        char result = ' ';
        int width = img.getWidth();
        int height = img.getHeight();
        int min = width * height; // 最小差异像素数
        for (BufferedImage bi : trainImgMap.keySet()) {
            int count = 0; // 差异像素数
            for (int x = 0; x < width; ++x) {
                for (int y = 0; y < height; ++y) {
                    count += (img.getRGB(x, y) != bi.getRGB(x, y) ? 1 : 0);
                    if (count >= min) {
                        break;
                    }
                }
            }
            if (count < min) {
                min = count;
                result = trainImgMap.get(bi);
            }
        }
        return result;
    }

    /**
     * 识别
     * 
     * @param picFile 图形验证码文件
     * @return
     */
    public String translate(File picFile) {
        String result = "";
        try {
            BufferedImage img = denoise(picFile);
            List<BufferedImage> listImg = split(img);
            Map<BufferedImage, Character> map = loadTrainData();
            for (BufferedImage bi : listImg) {
                result += recognize(bi, map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}