package com.example.zoomimage.view;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

/**
 * Created by 25110 on 2017/2/18.
 */

public class ZoomImageView extends ImageView implements ViewTreeObserver.OnGlobalLayoutListener, ScaleGestureDetector.OnScaleGestureListener, View.OnTouchListener {
    private boolean mOnce = false;
    //图片初始缩放比例
    private float mInitScale;
    private float mMidScale;
    private float mMaxScale;

    //图片缩放矩阵
    private Matrix mScaleMatrix;

    private ScaleGestureDetector mScaleGestureDetector;
    private GestureDetector mGestureDetector;

    private int mLastPointCount;

    private float mLastX;
    private float mLastY;

    private boolean isCanDrag;

    private int scaledTouchSlop;

    private boolean isAutoScale;

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
        mGestureDetector=new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                if (isAutoScale)
                    return true;
                float x=e.getX();
                float y=e.getY();

                if (getScale()>=mMidScale){
                   postDelayed(new AutoScaleRunnable(mInitScale,x,y),16);
                }else {
                    postDelayed(new AutoScaleRunnable(mMidScale,x,y),16);
                }
                return true;
            }
        });
        setOnTouchListener(this);

        scaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
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
            mMidScale=scale*5;
            mMaxScale = scale * 10;

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

            checkBorder();

            setImageMatrix(mScaleMatrix);

        }

        return true;
    }


    /**
     * 检测并防止在缩放或移动的过程中出现白边
     */
    private void checkBorder() {
        float delaX = 0f;
        float delaY = 0f;

        RectF rect = getMatrixRectF();

        if (rect.width() >= getWidth()) {
            if (rect.right < getWidth()) {
                delaX = getWidth() - rect.right;
            }
            if (rect.left > 0) {
                delaX = -rect.left;
            }
        }

        if (rect.height() >= getHeight()) {
            if (rect.bottom < getHeight()) {
                delaY = getHeight() - rect.bottom;
            }
            if (rect.top > 0) {
                delaY = -rect.top;
            }
        }

        if (rect.width() < getWidth()) {
            delaX = getWidth() / 2 - rect.right + rect.width() / 2;
        }

        if (rect.height() < getHeight()) {
            delaY = getHeight() / 2 - rect.bottom + rect.height() / 2;
        }

        mScaleMatrix.postTranslate(delaX, delaY);
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
        if (mGestureDetector.onTouchEvent(motionEvent))
            return true;
        mScaleGestureDetector.onTouchEvent(motionEvent);

        //记录多指触控时中心点的位置
        float x = 0;
        float y = 0;

        //得到多指触控的手指数量
        int pointerCount = motionEvent.getPointerCount();

        for (int i = 0; i < pointerCount; i++) {
            x += motionEvent.getX(i);
            y += motionEvent.getY(i);
        }

        x = x / pointerCount;
        y = y / pointerCount;

        if (pointerCount != mLastPointCount) {
            isCanDrag = false;
            mLastPointCount = pointerCount;
            mLastX = x;
            mLastY = y;
        }

        RectF rectF = getMatrixRectF();
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if ((rectF.width()>getWidth()+0.01)||(rectF.height()>getHeight()+0.01)){
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if ((rectF.width()>getWidth()+0.01)||(rectF.height()>getHeight()+0.01)){
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                float dx = x - mLastX;
                float dy = y - mLastY;
                if (!isCanDrag) {
                    isCanDrag = isMoveAction(dx, dy);
                }

                if (isCanDrag) {
                    if (getDrawable() != null) {
                        RectF rect = getMatrixRectF();

                        //当图片宽度小于屏幕宽度，不允许左右移动;
                        if (rect.width() <= getWidth()) {
                            dx = 0;
                        }
                        //当图片高度小于屏幕高度，不允许上下移动
                        if (rect.height() <= getHeight()) {
                            dy = 0;
                        }
                        mScaleMatrix.postTranslate(dx, dy);
                        checkBorder();
                        setImageMatrix(mScaleMatrix);
                    }
                }
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mLastPointCount = 0;
                break;

        }
        return true;
    }

    /**
     * 判断是否是移动操作
     *
     * @param dx
     * @param dy
     * @return
     */
    private boolean isMoveAction(float dx, float dy) {
        return Math.sqrt(dx * dx + dy * dy) >= scaledTouchSlop;
    }

    public float getScale() {
        float[] values = new float[9];
        mScaleMatrix.getValues(values);
        return values[Matrix.MSCALE_X];
    }

    public RectF getMatrixRectF() {
        RectF rectF = null;
        Drawable drawable = getDrawable();
        if (drawable != null) {
            rectF = new RectF(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            mScaleMatrix.mapRect(rectF);
        }
        return rectF;
    }

    /**
     * 控制缩放快慢的runnable
     */
    private class AutoScaleRunnable implements Runnable{
        private static final float BIGGER=1.07f;
        private static final float SMALLER=0.93f;

        private float tempScale;

        private float mTargetScale;
        private float x;
        private float y;

        public AutoScaleRunnable(float mTargetScale, float x, float y) {
            this.mTargetScale = mTargetScale;
            this.x = x;
            this.y = y;

            if (getScale()<mTargetScale){
                tempScale=BIGGER;
            }else if (getScale()>mTargetScale){
                tempScale=SMALLER;
            }
        }

        @Override
        public void run() {
            checkBorder();
            mScaleMatrix.postScale(tempScale,tempScale,x,y);
            setImageMatrix(mScaleMatrix);

            float currentScale=getScale();
            if ((tempScale>1.0f&&currentScale<mTargetScale)||(tempScale<1.0f&&currentScale>mTargetScale)){
              postDelayed(this,16);
            }
            else {
                float scale=mTargetScale/currentScale;
                checkBorder();
                mScaleMatrix.postScale(scale,scale,x,y);
                setImageMatrix(mScaleMatrix);
                isAutoScale=false;
            }
        }
    }
}
