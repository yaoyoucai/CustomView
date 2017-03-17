package com.example.view;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

import com.example.paint.R;

/**
 * Created by 25110 on 2017/3/2.
 */

public class RoundDogView extends View {
    private Paint mPaint;
    private Bitmap mBmpSrc;
    private Bitmap mBmpDis;

    public RoundDogView(Context context) {
        this(context, null);
    }

    public RoundDogView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mBmpSrc = BitmapFactory.decodeResource(getResources(), R.drawable.dog);
        mBmpDis = BitmapFactory.decodeResource(getResources(), R.drawable.dog_shade);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int layerId = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);

        canvas.drawBitmap(mBmpDis, 0, 0, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(mBmpSrc, 0, 0, mPaint);
        mPaint.setXfermode(null);

        canvas.restoreToCount(layerId);
    }
}
