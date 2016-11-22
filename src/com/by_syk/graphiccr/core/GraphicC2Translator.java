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
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * 第2类图形验证码识别
 * <br />针对截至 2016-11-22 为止成都医学院教务系统登录用的验证码
 * <br />图形尺寸为 72*27
 * 
 * @author By_syk
 */
public class GraphicC2Translator {
    private static GraphicC2Translator translator = null;
    
    /**
     * 元字符宽度
     */
    private static final int UNIT_W = 13;
    
    /**
     * 元字符高度
     */
    private static final int UNIT_H = 22;
    
    /**
     * 训练元字符数
     */
    private static final int TRAIN_NUM = 32;
    
    /**
     * 所有元字符
     */
    private static final char[] TRAIN_CHARS = {'0', '1', '2', '3', '4', '5', '6', '7', '8',/* '9',*/
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
            /*'o', */'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y'/*, 'z'*/};
    
    /**
     * 有效像素颜色值
     */
    private static final int TARGET_COLOR = Color.BLACK.getRGB();
    
    /**
     * 无效像素颜色值
     */
    private static final int USELESS_COLOR = Color.WHITE.getRGB();
    
    private GraphicC2Translator() {}
    
    public static GraphicC2Translator getInstance() {
        if (translator == null) {
            translator = new GraphicC2Translator();
        }
        
        return translator;
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
        final int TARGET = 0xff000099;
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                if (img.getRGB(x, y) == TARGET) {
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
    private static List<BufferedImage> split(BufferedImage img) throws Exception {
        List<BufferedImage> subImgs = new ArrayList<BufferedImage>();
        subImgs.add(img.getSubimage(4, 0, UNIT_W, UNIT_H));
        subImgs.add(img.getSubimage(16, 0, UNIT_W, UNIT_H));
        subImgs.add(img.getSubimage(28, 0, UNIT_W, UNIT_H));
        subImgs.add(img.getSubimage(40, 0, UNIT_W, UNIT_H));
        return subImgs;
    }

    /**
     * 取出训练数据
     * 
     * @return
     * @throws Exception
     */
    private BufferedImage loadTrainData() throws Exception {
//        File file = new File(this.getClass().getResource("/resources/img/2/train.gif").getPath());
//        File file = new File("E:/JavaWebProjects/SchTtable/reserve/成都医学院/ImageCode/train/train.gif");
//        return ImageIO.read(file);
      return ImageIO.read(this.getClass().getResourceAsStream("/resources/img/2/train.gif"));
    }

    /**
     * 将训练元字符装在一起
     * 
     * @param trainImg
     * @param smallImg
     * @param ch
     * @return
     */
    private boolean addTrainImg(BufferedImage trainImg, BufferedImage smallImg, char ch) {
        int which = Arrays.binarySearch(TRAIN_CHARS, ch);
        int x = -1;
        int y = -1;
        for (int i = 0; i < TRAIN_NUM; ++i) {
            if (trainImg.getRGB(i * UNIT_W, which * (UNIT_H + 1) + UNIT_H) != Color.BLACK.getRGB()) {
                x = i * UNIT_W;
                y = which * (UNIT_H + 1);
                break;
            }
        }
        
        if (x == -1 || y == -1) {
            return false;
        }
        
        for (int i = 0; i < UNIT_W; ++i) {
            for (int j = 0; j < UNIT_H; ++j) {
                trainImg.setRGB(x + i, y + j, smallImg.getRGB(i, j));
            }
        }
        trainImg.setRGB(x, y + UNIT_H, TARGET_COLOR);
        return true;
    }

    /**
     * 训练
     * 
     * @param rawDir
     * @param targetTrainFile
     * @return
     */
    public boolean train(File rawDir, File targetTrainFile) {
        try {
            BufferedImage trainImg = new BufferedImage(UNIT_W * TRAIN_NUM, (UNIT_H + 1) * TRAIN_CHARS.length, BufferedImage.TYPE_INT_ARGB);
            for (File file : rawDir.listFiles()) {
                BufferedImage img = denoise(file);
                List<BufferedImage> listImg = split(img);
                char[] chars = file.getName().split("\\.")[0].toCharArray();
                for (int i = 0, len = listImg.size(); i < len; ++i) {
                    addTrainImg(trainImg, listImg.get(i), chars[i]);
                }
            }
            return ImageIO.write(trainImg, "GIF", targetTrainFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return false;
    }

    /**
     * 单元识别
     * 
     * @param img
     * @param trainImg
     * @return
     */
    private char recognize(BufferedImage img, BufferedImage trainImg) {
        char result = ' ';
        int width = img.getWidth();
        int height = img.getHeight();
        int min = width * height; // 最小差异像素数
        for (int i = 0; i < TRAIN_NUM; ++i) {
            for (int j = 0; j < TRAIN_CHARS.length; ++j) {
                int startX = UNIT_W * i;
                int startY = (UNIT_H + 1) * j;
                if (trainImg.getRGB(startX, startY + UNIT_H) != TARGET_COLOR) {
                    continue;
                }
                int count = 0; // 差异像素数
                for (int x = 0; x < UNIT_W; ++x) {
                    for (int y = 0; y < UNIT_H; ++y) {
                        count += (img.getRGB(x, y) != trainImg.getRGB(startX + x, startY + y) ? 1 : 0);
                        if (count >= min) {
                            break;
                        }
                    }
                }
                if (count < min) {
                    min = count;
                    result = TRAIN_CHARS[j];
                }
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
            BufferedImage trainImg = loadTrainData();
            for (BufferedImage bi : listImg) {
                result += recognize(bi, trainImg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}