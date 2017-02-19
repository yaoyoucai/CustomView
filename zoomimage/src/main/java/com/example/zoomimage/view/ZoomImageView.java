package com.example.zoomimage.view;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

/**
 * Created by 25110 on 2017/2/18.
 */

public class ZoomImageView extends ImageView implements ViewTreeObserver.OnGlobalLayoutListener, ScaleGestureDetector.OnScaleGestureListener, View.OnTouchListener {
    private boolean mOnce = false;
    //图片初始缩放比例
    private float mInitScale;

    private float mMaxScale;

    //图片缩放矩阵
    private Matrix mScaleMatrix;

    private ScaleGestureDetector mScaleGestureDetector;

    public ZoomImageView(Context context) {
        this(context, null);
    }

    public ZoomImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZoomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mScaleMatrix = new Matrix();
        setScaleType(ScaleType.MATRIX);

        //手势识别必须在三个方法的构造方法中使用，在两个方法的构造方法中使用无法识别，目前原因未知
        mScaleGestureDetector = new ScaleGestureDetector(getContext(), this);

        setOnTouchListener(this);
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
        if (!mOnce) {
            Drawable drawable = getDrawable();
            if (drawable == null)
                return;

            int width = drawable.getIntrinsicWidth();
            int height = drawable.getIntrinsicHeight();

            int widgetWidth = getWidth();
            int widgetHeight = getHeight();

            float scale = 1.0f;

            if (width > widgetWidth && height < widgetHeight) {
                scale = widgetWidth * 1.0f / width;
            }

            if (width < widgetWidth && height > widgetHeight) {
                scale = widgetHeight * 1.0f / height;
            }

            if ((width > widgetWidth && height > widgetHeight) || (width < widgetWidth && height < widgetHeight)) {
                scale = Math.min(widgetWidth * 1.0f / width, widgetHeight * 1.0f / height);
            }
            mInitScale = scale;
            mMaxScale = scale * 20;

            mScaleMatrix.postTranslate(widgetWidth / 2 - width / 2, widgetHeight / 2 - height / 2);
            mScaleMatrix.postScale(mInitScale, mInitScale, widgetWidth / 2, widgetHeight / 2);
            setImageMatrix(mScaleMatrix);

            mOnce = true;
        }
    }

    @Override
    public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
        //得到用户预缩放比例
        float scaleFactor = scaleGestureDetector.getScaleFactor();

        //得到当前图片的缩放比例
        float scale = getScale();

        if (getDrawable() == null)
            return true;

        if ((scale > mInitScale && scaleFactor < 1.0f) || (scale < mMaxScale && scaleFactor > 1.0f)) {
            if (scale * scaleFactor > mMaxScale) {
                scaleFactor = mMaxScale / scale;
            }
            if (scale * scaleFactor < mInitScale) {
                scaleFactor = mInitScale / scale;
            }

            mScaleMatrix.postScale(scaleFactor, scaleFactor, scaleGestureDetector.getFocusX(), scaleGestureDetector.getFocusY());

            checkBorderAndWidth();

            setImageMatrix(mScaleMatrix);

        }

        return true;
    }


    /**
     * 检测并防止在缩放的过程中出现白边
     */
    private void checkBorderAndWidth() {
        float delaX=0f;
        float delaY=0f;

        RectF rect = getMatrixRectF();

        if (rect.width()>=getWidth()){
            if (rect.right<getWidth()){
                delaX=getWidth()-rect.right;
            }
            if (rect.left>0){
                delaX=-rect.left;
            }
        }

        if (rect.height()>=getHeight()){
            if (rect.bottom<getHeight()){
                delaY=getHeight()-rect.bottom;
            }
             if (rect.top>0){
                delaY=-rect.top;
            }
        }

        if (rect.width()<getWidth()){
            delaX=getWidth()/2-rect.right+rect.width()/2;
        }

        if (rect.height()<getHeight()){
            delaY=getHeight()/2-rect.bottom+rect.height()/2;
        }

        mScaleMatrix.postTranslate(delaX,delaY);
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return mScaleGestureDetector.onTouchEvent(motionEvent);
    }

    public float getScale() {
        float[] values = new float[9];
        mScaleMatrix.getValues(values);
        return values[Matrix.MSCALE_X];
    }

    public RectF getMatrixRectF() {
        RectF rectF=null;
        Drawable drawable = getDrawable();
        if (drawable!=null){
            rectF=new RectF(0,0,drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
            mScaleMatrix.mapRect(rectF);
        }
        return rectF;
    }
}
