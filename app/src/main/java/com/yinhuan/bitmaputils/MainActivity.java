package com.yinhuan.bitmaputils;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.yinhuan.bitmaputils.cache.BitmapUtils;

public class MainActivity extends AppCompatActivity {


    private static final String picUrl = "http://7xi8d6.com1.z0.glb.clouddn.com/2017-03-02-16906481_1495916493759925_5770648570629718016_n.jpg";

    private ImageView pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pic = (ImageView) findViewById(R.id.pic);
        BitmapUtils.getInstance().loadPic(pic,picUrl);

    }
}
