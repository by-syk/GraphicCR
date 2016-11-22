package com.by_syk.graphiccr.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class ExtraUtil {
    public static boolean downloadFile(String urlStr, String saveFile) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            URL url = new URL(urlStr);
            URLConnection urlConnection = url.openConnection();
            inputStream = urlConnection.getInputStream();
            outputStream = new FileOutputStream(saveFile);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        return false;
    }
}
