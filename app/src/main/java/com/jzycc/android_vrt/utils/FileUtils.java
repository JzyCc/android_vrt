package com.jzycc.android_vrt.utils;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author : Jzy
 * date   : 18-10-12
 */
public class FileUtils {

    public static String FiletoString(Context context, String fileName) {
        String line = "";
        String result = "\n";
        try{
            InputStreamReader inputStream = new InputStreamReader(context.getResources().getAssets().open(fileName));
            BufferedReader bufferedReader = new BufferedReader(inputStream);
            while ((line = bufferedReader.readLine())!=null){
                result += (line+"\n");
            }
        }catch (IOException e){
            Log.e("jzy111", "onCreate: ",e );
            result = "";
        }
        return result;
    }
}
