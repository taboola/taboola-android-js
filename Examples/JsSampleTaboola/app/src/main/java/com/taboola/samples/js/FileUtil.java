package com.taboola.samples.js;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

public class FileUtil {
    private static final String TAG = FileUtil.class.getSimpleName();

    public static String getAssetFileContent(Context context, String fileName) {
        try {
            InputStream inputStream = context.getAssets().open(fileName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];

            inputStream.read(buffer);
            inputStream.close();
            String contentString = new String(buffer);
            return contentString;
        } catch (IOException e) {
            Log.e(TAG, "getHtmlTemplateFileContent :: error opening template file " + e.toString());
            return "";
        }
    }
}
