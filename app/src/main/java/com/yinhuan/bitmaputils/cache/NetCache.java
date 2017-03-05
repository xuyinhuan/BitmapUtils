package com.yinhuan.bitmaputils.cache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by yinhuan on 2017/3/5.
 *
 * 网络缓存
 */

public class NetCache {

    private LocalCache mLocalCache;
    private MemoryCache mMemoryCache;

    public NetCache(LocalCache localCache, MemoryCache memoryCache){
        this.mLocalCache = localCache;
        this.mMemoryCache = memoryCache;
    }

    /**
     * 暴露给外部的接口
     * @param ivPic
     * @param url
     */
    public void getBitmapFromNet(ImageView ivPic, String url){
        new BitmapTask().execute(ivPic,url);//开启 AsyncTask 下载图片
    }


    /**
     * <参数、进度、返回结果>
     */
    class BitmapTask extends AsyncTask<Object, Void, Bitmap>{

        private ImageView ivPic;
        private String url;

        @Override
        protected Bitmap doInBackground(Object... params) {
            //取出参数
            ivPic = (ImageView) params[0];
            url = (String) params[1];

            return downLoadBitmap(url);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            //处理返回结果
            if (bitmap != null){
                ivPic.setImageBitmap(bitmap);
                //缓存到本地
                mLocalCache.setBitmapToLocal(url,bitmap);
                //缓存到内存
                mMemoryCache.setBitmapToMemory(url,bitmap);
            }
        }
    }

    /**
     * 从网络下载图片
     * @param url
     * @return
     */
    private Bitmap downLoadBitmap(String url){
        HttpURLConnection connection = null;

        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();

            if (responseCode == 200){

                BitmapFactory.Options options = new BitmapFactory.Options();
                //为了避免内存溢出的问题，我们可以在获取网络图片后。对其进行图片压缩
                options.inSampleSize = 2;
                options.inPreferredConfig = Bitmap.Config.ARGB_4444;

                Bitmap bitmap = BitmapFactory.decodeStream(connection.getInputStream(), null, options);

                return bitmap;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //释放资源
            connection.disconnect();
        }

        return null;
    }

}
