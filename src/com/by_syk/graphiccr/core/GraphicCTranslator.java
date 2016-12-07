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

import java.io.File;

import com.by_syk.graphiccr.util.ExtraUtil;

public class GraphicCTranslator {
    /**
     * 第1类图形验证码识别
     * <br />针对截至 2016-11-22 为止东北大学秦皇岛分校教务系统登录用的验证码
     * <br />图形尺寸为 60*20
     */
    public static final int TYPE_1 = 1;
    
    /**
     * 第2类图形验证码识别
     * <br />针对截至 2016-11-22 为止成都医学院、四川理工学院教务管理系统登录用的验证码
     * <br />图形尺寸为 72*27
     */
    public static final int TYPE_2 = 2;
    
    /**
     * 第3类图形验证码识别
     * <br />针对截至 2016-11-22 为止蚌埠医学院教务网络管理系统登录用的验证码
     * <br />图形尺寸为 122*54
     */
    public static final int TYPE_3 = 3;
    
    /**
     * 第4类图形验证码识别
     * <br />针对截至 2016-12-01 为止山东交通学院综合教务系统登录用的验证码
     * <br />图形尺寸为 60*20
     */
    public static final int TYPE_4 = 4;
    
    /**
     * 第5类图形验证码识别
     * <br />针对截至 2016-12-03 为止南昌大学教学一体化服务平台登录用的验证码
     * <br />图形尺寸为 62*22
     */
    public static final int TYPE_5 = 5;
    
    /**
     * 第6类图形验证码识别
     * <br />针对截至 2016-12-07 为止安徽工业大学教务管理系统登录用的验证码
     * <br />图形尺寸为 60*22
     */
    public static final int TYPE_6 = 6;
    
    /**
     * 识别指定文件的图形验证码
     * 
     * @param picFile
     * @param type
     * @return
     */
    public static String translate(File picFile, int type) {
        switch (type) {
        case TYPE_1:
            return GraphicC1Translator.getInstance().translate(picFile);
        case TYPE_2:
            return GraphicC2Translator.getInstance().translate(picFile);
        case TYPE_3:
            return GraphicC3Translator.getInstance().translate(picFile);
        case TYPE_4:
            return GraphicC4Translator.getInstance().translate(picFile);
        case TYPE_5:
            return GraphicC5Translator.getInstance().translate(picFile);
        case TYPE_6:
            return GraphicC6Translator.getInstance().translate(picFile);
        }
        return "";
    }
    
    /**
     * 识别指定文件的图形验证码
     * 
     * @param picPath
     * @param type
     * @return
     */
    public static String translate(String picPath, int type) {
        if (picPath == null) {
            return "";
        }
        return translate(new File(picPath), type);
    }
    
    /**
     * 识别指定链接的图形验证码
     * 
     * @param urlStr
     * @param cacheFile
     * @param type
     * @return
     */
    public static String translate(String urlStr, File cacheFile, int type) {
        if (urlStr == null) {
            return "";
        }
        boolean ok = ExtraUtil.downloadFile(urlStr, cacheFile);
        if (ok) {
            return translate(cacheFile, type);
        }
        return "";
    }
}
