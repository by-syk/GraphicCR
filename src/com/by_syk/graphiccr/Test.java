package com.by_syk.graphiccr;

import java.io.File;

import com.by_syk.graphiccr.core.GraphicC1Translator;
import com.by_syk.graphiccr.util.ExtraUtil;

public class Test {
    public static void main(String[] args) {
        testGraphicC1();
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
}
