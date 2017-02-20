package shbd.matrix.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import shbd.matrix.R;

/**
 * 项目名称：CustomView
 * 类描述：
 * 创建人：yh
 * 创建时间：2017/2/20 13:48
 * 修改人：yh
 * 修改时间：2017/2/20 13:48
 * 修改备注：
 */
public class RotateView extends View {
    private Paint mPaint;
    private Bitmap mBitmap;

    private int currentDegree;

    public RotateView(Context context) {
        this(context, null);
    }

    public RotateView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RotateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint();
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.STROKE);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.poly_test, options);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(getWidth() / 2, getHeight() / 2);

        Camera camera = new Camera();
        camera.translate(-mBitmap.getWidth() / 2, -mBitmap.getHeight() / 2, 0);
        camera.rotateX(currentDegree);

        Matrix matrix = new Matrix();
        camera.getMatrix(matrix);
        canvas.drawBitmap(mBitmap, matrix, mPaint);

        ValueAnimator animator = ValueAnimator.ofInt(0, 360);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                currentDegree = (int) valueAnimator.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.setDuration(30000);
        animator.start();

        super.onDraw(canvas);

    }
}
