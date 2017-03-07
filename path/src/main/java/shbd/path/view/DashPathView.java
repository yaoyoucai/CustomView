package shbd.path.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * 项目名称：CustomView
 * 类描述：具有动画效果的虚线
 * 创建人：yh
 * 创建时间：2017/3/2 10:26
 * 修改人：yh
 * 修改时间：2017/3/2 10:26
 * 修改备注：
 */
public class DashPathView extends View {
    private Paint mPaint;

    private Path mPath;

    private float offset;

    public DashPathView(Context context) {
        this(context, null);
    }

    public DashPathView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        initPath();
        startMoveAnim();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(5);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    private void initPath() {
        mPath = new Path();
        mPath.moveTo(100, 600);
        mPath.lineTo(400, 100);
        mPath.lineTo(700, 900);
    }

    private void startMoveAnim() {
        ValueAnimator animator = ValueAnimator.ofFloat(0, 220);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                offset = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setDuration(600);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setPathEffect(new DashPathEffect(new float[]{30, 40, 100, 50}, offset));
        canvas.drawPath(mPath, mPaint);
    }
}
