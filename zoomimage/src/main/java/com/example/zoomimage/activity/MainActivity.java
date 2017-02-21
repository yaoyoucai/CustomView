package com.example.zoomimage.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.example.zoomimage.R;
import com.example.zoomimage.view.ZoomImageView;

public class MainActivity extends AppCompatActivity {
    private ViewPager mVpContent;

    private int [] imgs=new int[]{R.drawable.meizi1,R.drawable.meizi2,R.drawable.meizi3};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mVpContent= (ViewPager) findViewById(R.id.vp_content);
        mVpContent.setAdapter(new MyPagerAdapter());
    }


    private class MyPagerAdapter extends PagerAdapter{
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public ZoomImageView instantiateItem(ViewGroup container, int position) {
                ZoomImageView imageView=new ZoomImageView(getApplicationContext());
            container.addView(imageView);
            imageView.setImageResource(imgs[position]);
            return imageView;
        }

        @Override
        public int getCount() {
            return imgs.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view ==object;
        }
    }
}

