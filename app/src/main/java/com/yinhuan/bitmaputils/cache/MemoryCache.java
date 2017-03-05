package com.yinhuan.bitmaputils.cache;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by yinhuan on 2017/3/5.
 * <p>
 * 内存缓存
 */

public class MemoryCache {

    private LruCache<String, Bitmap> mMemoryCache;

    public MemoryCache() {
        //获取到手机最大允许内存的 1/8
        long maxMemory = Runtime.getRuntime().maxMemory() / 8;

        mMemoryCache = new LruCache<String, Bitmap>((int) maxMemory) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                //计算每个条目的大小
                int byteCount = value.getByteCount();
                return byteCount;
            }
        };

    }

    /**
     * 缓存到内存
     * @param url
     * @param bitmap
     */
    public void setBitmapToMemory(String url, Bitmap bitmap) {
        mMemoryCache.put(url, bitmap);
    }

    /**
     * 从内存中读取缓存
     * @param url
     * @return
     */
    public Bitmap getBitmapFromMemory(String url) {
        Bitmap bitmap = mMemoryCache.get(url);
        return bitmap;
    }


}
/**
 * Android 系统在加载图片时是解析每一个像素的信息，再把每一个像素全部保存至内存中
 * 图片大小 = 图片的总像素 * 每个像素占用的大小
 * 单色图：每个像素占用1/8个字节，16色图：每个像素占用1/2个字节，
 * 256色图：每个像素占用1个字节，24位图：每个像素占用3个字节（常见的rgb构成的图片）
 * 例如一张1920x1080的JPG图片，在 Android 系统中是以ARGB格式解析的
 * 即一个像素需占用4个字节，图片的大小=1920x1080x4=7M
 */