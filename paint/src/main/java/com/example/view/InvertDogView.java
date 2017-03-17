package com.example.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

import com.example.paint.R;

/**
 * Created by 25110 on 2017/3/2.
 */

public class InvertDogView extends View {
    //源图，目标图，倒影图
    private Bitmap mBmpSrc, mBmpDis, mBmpInvert;

    private Paint mPaint;

    public InvertDogView(Context context) {
        this(context, null);
    }

    public InvertDogView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mPaint = new Paint();
        mBmpSrc = BitmapFactory.decodeResource(getResources(), R.drawable.dog);
        mBmpDis = BitmapFactory.decodeResource(getResources(), R.drawable.dog_invert_shade);

        Matrix matrix = new Matrix();
        matrix.setScale(1, -1);
        mBmpInvert = Bitmap.createBitmap(mBmpSrc, 0, 0, mBmpSrc.getWidth(), mBmpSrc.getHeight(), matrix, true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画出小狗图像
        canvas.drawBitmap(mBmpSrc, 0, 0, mPaint);

        //画出倒影图
        int layerId = canvas.saveLayer(0, 0, getWidth(), getHeight(), mPaint, Canvas.ALL_SAVE_FLAG);
        canvas.translate(0, mBmpSrc.getHeight());
        canvas.drawBitmap(mBmpDis, 0, 0, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(mBmpInvert, 0, 0, mPaint);
        mPaint.setXfermode(null);

        canvas.restoreToCount(layerId);
    }
}
