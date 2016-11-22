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
    public static final int TYPE_1 = 1;
    public static final int TYPE_2 = 2;
    
    public static String translate(File picFile, int type) {
        switch (type) {
        case TYPE_1:
            return GraphicC1Translator.getInstance().translate(picFile);
        case TYPE_2:
            return GraphicC2Translator.getInstance().translate(picFile);
        }
        return "";
    }
    
    public static String translate(String picPath, int type) {
        if (picPath == null) {
            return "";
        }
        return translate(new File(picPath), type);
    }
    
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
