package com.yinhuan.bitmaputils.cache;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by yinhuan on 2017/3/5.
 */

public class BitmapUtils {

    /**
     * 内存缓存
     */
    private MemoryCache mMemoryCache;

    /**
     * 本地缓存
     */
    private LocalCache mLocalCache;

    /**
     * 网络缓存
     */
    private NetCache mNetCache;

    private static class SingletonHolder{
        private static final BitmapUtils instance = new BitmapUtils();
    }

    public static BitmapUtils getInstance(){
        return SingletonHolder.instance;
    }

    private BitmapUtils(){
        mMemoryCache = new MemoryCache();
        mLocalCache = new LocalCache();
        mNetCache = new NetCache(mLocalCache,mMemoryCache);
    }

    /**
     * 加载图片
     * @param ivPic 用于显示图片的 ImageView
     * @param url   用于获取图片的网络地址
     */
    public void loadPic(ImageView ivPic, String url){

        Bitmap bitmap;

        //优先从内存中加载，速度最快
        bitmap = mMemoryCache.getBitmapFromMemory(url);
        if (bitmap != null){
            ivPic.setImageBitmap(bitmap);
            return;
        }

        //如果内存中没有缓存，从本地 SD 卡加载
        bitmap = mLocalCache.getBitmapFromLocal(url);
        if (bitmap != null){
            ivPic.setImageBitmap(bitmap);
            //从本地获取图片后，缓存到内存中
            mMemoryCache.setBitmapToMemory(url,bitmap);
            return;
        }

        //最后才是从网络获取
        mNetCache.getBitmapFromNet(ivPic, url);
    }

}
