package com.example.zoomimage.view;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

/**
 * Created by 25110 on 2017/2/18.
 */

public class ZoomImageView extends ImageView implements ViewTreeObserver.OnGlobalLayoutListener {
    private boolean mOnce=false;
    //图片初始缩放比例
    private float mInitScale;

    //图片缩放矩阵
    private Matrix mScaleMatrix;

    public ZoomImageView(Context context) {
        this(context,null);
    }

    public ZoomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mScaleMatrix=new Matrix();
        setScaleType(ScaleType.MATRIX);
    }

    @Override
    protected void onAttachedToWindow() {
        getViewTreeObserver().addOnGlobalLayoutListener(this);
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        getViewTreeObserver().removeGlobalOnLayoutListener(this);
        super.onDetachedFromWindow();
    }

    @Override
    public void onGlobalLayout() {
        if (!mOnce){
            Drawable drawable = getDrawable();
            if (drawable==null)
                return;

            int width=drawable.getIntrinsicWidth();
            int height=drawable.getIntrinsicHeight();

            int widgetWidth=getWidth();
            int widgetHeight=getHeight();

            float scale=1.0f;

            if (width>widgetWidth&&height<widgetHeight){
                scale=widgetWidth*1.0f/width;
            }

            if (width<widgetWidth&&height>widgetHeight){
                scale=widgetHeight*1.0f/height;
            }

            if((width>widgetWidth&&height>widgetHeight)||(width<widgetWidth&&height<widgetHeight)){
                scale=Math.min(widgetWidth*1.0f/width,widgetHeight*1.0f/height);
            }
            mInitScale=scale;

            mScaleMatrix.postTranslate(widgetWidth/2-width/2,widgetHeight/2-height/2);
            mScaleMatrix.postScale(0.72f,0.72f,widgetWidth/2,widgetHeight/2);
            setImageMatrix(mScaleMatrix);

            mOnce=true;
        }
    }
}
