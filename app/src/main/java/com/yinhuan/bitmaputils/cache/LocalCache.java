package com.yinhuan.bitmaputils.cache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.yinhuan.bitmaputils.MD5Encoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by yinhuan on 2017/3/5.
 *
 * 本地缓存
 */

public class LocalCache {

    //本地缓存路径
    private static final String CACHE_PATH = Environment.getExternalStorageDirectory()+"/MeiMei";


    /**
     * 从网络下载图片后，缓存到本地
     * @param url
     * @param bitmap
     */
    public void setBitmapToLocal(String url, Bitmap bitmap){

        try {
            //使用 MD5 加密图片的网络地址，作为图片名称
            String fileName = MD5Encoder.encode(url);
            //传入父路径和子路径构造文件对象
            File file = new File(CACHE_PATH, fileName);

            Log.d("LocalCache", "CACHE_PATH - >" + CACHE_PATH);//CACHE_PATH - >
            Log.d("LocalCache", "file - >"+file);//

            File parentFile = file.getParentFile();//parentFile -> CACHE_PATH

            //确保父文件已经创建
            if (!parentFile.exists()){
                parentFile.mkdir();
            }

            //写进本地
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 从本地获取图片缓存
     * @param url
     * @return
     */
    public Bitmap getBitmapFromLocal(String url){

        String fileName = null;

        try {
            fileName = MD5Encoder.encode(url);
            File file = new File(CACHE_PATH,fileName);

            //解析文件输入流，转成 Bitmap
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));

            return bitmap;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


}
