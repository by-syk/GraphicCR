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
import com.by_syk.graphiccr.core.GraphicC3Translator;
import com.by_syk.graphiccr.core.GraphicC4Translator;
import com.by_syk.graphiccr.core.GraphicC5Translator;
import com.by_syk.graphiccr.util.ExtraUtil;

public class Test {
    public static void main(String[] args) {
        testGraphicC5();
    }
    
    private static void testGraphicC1() {
        File testDir = new File("E:/JavaProjects/GraphicCR/reserve/GraphicC/1/test");
        
        for (int i = 0; i < 10; ++i) {
            ExtraUtil.downloadFile("http://jwpt.neuq.edu.cn/ACTIONVALIDATERANDOMPICTURE.APPPROCESS",
                    new File(testDir, System.currentTimeMillis() + ".jpg"));
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
        File rawDir = new File("E:/JavaProjects/GraphicCR/reserve/GraphicC/2/raw");
        File trainFile = new File("E:/JavaProjects/GraphicCR/reserve/GraphicC/2/train/train.png");
        File testDir = new File("E:/JavaProjects/GraphicCR/reserve/GraphicC/2/test");
        
        translator.train(rawDir, trainFile);
        
        System.out.println("TRAIN DONE");

        for (int i = 0; i < 80; ++i) {
            ExtraUtil.downloadFile("http://61.139.105.138/CheckCode.aspx",
                    new File(testDir, System.currentTimeMillis() + ".gif"));
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
    
    private static void testGraphicC3() {
        GraphicC3Translator translator = GraphicC3Translator.getInstance();
        File rawDir = new File("E:/JavaProjects/GraphicCR/reserve/GraphicC/3/raw");
        File trainFile = new File("E:/JavaProjects/GraphicCR/reserve/GraphicC/3/train/train.png");
        File testDir = new File("E:/JavaProjects/GraphicCR/reserve/GraphicC/3/test");
        
        translator.train(rawDir, trainFile);
        
        System.out.println("TRAIN DONE");

        for (int i = 0; i < 100; ++i) {
            ExtraUtil.downloadFile("http://211.70.128.23/JWWEB/sys/ValidateCode.aspx",
                    new File(testDir, System.currentTimeMillis() + ".jpg"));
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
            file.renameTo(new File(file.getParentFile(), result + ".jpg"));
            System.out.println("TRANSLATE " + result);
        }
        
        System.out.println("TRANSLATE DONE");
    }
    
    private static void testGraphicC4() {
        File testDir = new File("E:/JavaProjects/GraphicCR/reserve/GraphicC/4/test");
        
        for (int i = 0; i < 100; ++i) {
            ExtraUtil.downloadFile("http://211.64.127.221/validateCodeAction.do?random=0.44780089727138145",
                    new File(testDir, System.currentTimeMillis() + ".jpg"));
            System.out.println("DOWNLOAD TEST " + (i + 1));
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        System.out.println("DOWNLOAD TEST DONE");

        GraphicC4Translator translator = GraphicC4Translator.getInstance();
        for (File file : testDir.listFiles()) {
            String result = translator.translate(file);
            file.renameTo(new File(file.getParentFile(), result + ".jpg"));
            System.out.println("TRANSLATE " + result);
        }
        
        System.out.println("TRANSLATE DONE");
    }
    
    private static void testGraphicC5() {
        File testDir = new File("E:/JavaProjects/GraphicCR/reserve/GraphicC/5/test");
        
        for (int i = 0; i < 100; ++i) {
            ExtraUtil.downloadFile("http://218.64.56.18/jsxsd/verifycode.servlet?t=0.7339572516226678",
                    new File(testDir, System.currentTimeMillis() + ".jpg"));
            System.out.println("DOWNLOAD TEST " + (i + 1));
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        System.out.println("DOWNLOAD TEST DONE");

        GraphicC5Translator translator = GraphicC5Translator.getInstance();
        for (File file : testDir.listFiles()) {
            String result = translator.translate(file);
            file.renameTo(new File(file.getParentFile(), result + ".jpg"));
            System.out.println("TRANSLATE " + result);
        }
        
        System.out.println("TRANSLATE DONE");
    }
}
