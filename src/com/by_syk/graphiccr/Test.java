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

package com.by_syk.graphiccr;

import java.io.File;

import com.by_syk.graphiccr.core.GraphicC1Translator;
import com.by_syk.graphiccr.core.GraphicC2Translator;
import com.by_syk.graphiccr.util.ExtraUtil;

public class Test {
    public static void main(String[] args) {
        testGraphicC2();
    }
    
    private static void testGraphicC1() {
        File testDir = new File("E:/JavaWebProjects/SchTtable/reserve/东北大学秦皇岛分校/ImageCode/test");
        
        for (int i = 0; i < 10; ++i) {
            ExtraUtil.downloadFile("http://jwpt.neuq.edu.cn/ACTIONVALIDATERANDOMPICTURE.APPPROCESS",
                    new File(testDir, System.currentTimeMillis() + ".jpg").getPath());
            System.out.println("DOWNLOAD TEST " + (i + 1));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        System.out.println("DOWNLOAD TEST DONE");

        GraphicC1Translator translator = GraphicC1Translator.getInstance();
        for (File file : testDir.listFiles()) {
            String result = translator.translate(file);
            file.renameTo(new File(file.getParentFile(), result + ".jpg"));
            System.out.println("TRANSLATE " + result);
        }
        
        System.out.println("TRANSLATE DONE");
    }
    
    private static void testGraphicC2() {
        GraphicC2Translator translator = GraphicC2Translator.getInstance();
        File rawDir = new File("E:/JavaWebProjects/SchTtable/reserve/成都医学院/ImageCode/raw");
        File trainFile = new File("E:/JavaWebProjects/SchTtable/reserve/成都医学院/ImageCode/train/train.gif");
        File testDir = new File("E:/JavaWebProjects/SchTtable/reserve/成都医学院/ImageCode/test");
        
        translator.train(rawDir, trainFile);
        
        System.out.println("TRAIN DONE");

        for (int i = 0; i < 100; ++i) {
            ExtraUtil.downloadFile("http://222.197.143.7/CheckCode.aspx",
                    new File(testDir, System.currentTimeMillis() + ".gif").getPath());
            System.out.println("DOWNLOAD TEST " + (i + 1));
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        System.out.println("DOWNLOAD TEST DONE");

        for (File file : testDir.listFiles()) {
            String result = translator.translate(file);
            file.renameTo(new File(file.getParentFile(), result + ".gif"));
            System.out.println("TRANSLATE " + result);
        }
        
        System.out.println("TRANSLATE DONE");
    }
}
