package shbd.propertyanimation.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.ValueAnimator;

import shbd.propertyanimation.PointFEvaluator;

/**
 * 项目名称：CustomView
 * 类描述：
 * 创建人：yh
 * 创建时间：2017/2/16 11:02
 * 修改人：yh
 * 修改时间：2017/2/16 11:02
 * 修改备注：
 */
public class EvaluatorView extends View {
    private static final float RADIUS = 100f;
    private Paint mPaint;

    private PointF currentPoint;

    private boolean isStarting = false;

    public EvaluatorView(Context context) {
        this(context, null);
    }

    public EvaluatorView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (currentPoint == null) {
            currentPoint = new PointF(RADIUS, RADIUS);
        }
        canvas.drawCircle(currentPoint.x, currentPoint.y, RADIUS, mPaint);
        super.onDraw(canvas);
    }

    public void startMove() {
        if (!isStarting) {
            PointF start = new PointF(RADIUS, RADIUS);
            PointF end = new PointF(getWidth() - RADIUS, getHeight() - RADIUS);

            ValueAnimator animator = ValueAnimator.ofObject(new PointFEvaluator(), start, end);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    currentPoint = (PointF) valueAnimator.getAnimatedValue();
                    invalidate();
                }
            });

            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    isStarting = true;
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    isStarting = false;
                }
            });
            animator.setDuration(5000);
            animator.start();
        }


    }


}
