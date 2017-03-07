package shbd.path.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

/**
 * 项目名称：CustomView
 * 类描述：
 * 创建人：yh
 * 创建时间：2017/2/21 10:45
 * 修改人：yh
 * 修改时间：2017/2/21 10:45
 * 修改备注：
 */
public class SearchView extends View {
    private Paint mPaint;

    private Path mPathSearch;
    private Path mPathCircle;

    private PathMeasure mMeasure;

    private Handler mHandler;
    // 动画数值(用于控制动画状态,因为同一时间内只允许有一种状态出现,具体数值处理取决于当前状态)
    private float mAnimatorValue;

    //默认动画周期为2秒
    private int defaultDuration = 2000;

    private ValueAnimator mStartingAnimator;
    private ValueAnimator mSearchingAnimator;
    private ValueAnimator mEndingAnimator;

    ValueAnimator.AnimatorUpdateListener mUpdateListener;
    AnimatorListenerAdapter mAnimatorListener;

    public static enum State {
        NONE,
        STARTING,
        SEARCHING,
        ENDING
    }

    // 当前的状态(非常重要)
    private State currentState=State.NONE;

    public SearchView(Context context) {
        this(context, null);
    }

    public SearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAll();
    }

    private void initAll() {
        initPaint();
        initPath();
        initHandler();
        initListener();
        initAnimator();
    }

    private void initPath() {
        mPathSearch = new Path();
        mPathCircle = new Path();

        mMeasure = new PathMeasure();

        //放大镜圆环
        RectF ova1 = new RectF(-50, -50, 50, 50);
        mPathSearch.addArc(ova1, 45, 359.9f);

        //外部圆环
        RectF ova2 = new RectF(-100, -100, 100, 100);
        mPathCircle.addArc(ova1, 45, 359.9f);

        mMeasure.setPath(mPathCircle, false);
        float[] pos = new float[2];
        //放大镜把手
        mMeasure.getPosTan(0, pos, null);

        mPathSearch.lineTo(pos[0], pos[1]);

    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(15);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
    }

    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {

            }
        };
    }

    private void initListener() {
        mUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mAnimatorValue = (float) valueAnimator.getAnimatedValue();
            }
        };

        mAnimatorListener = new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mHandler.sendEmptyMessage(0);
            }
        };
    }

    private void initAnimator() {
        mStartingAnimator = ValueAnimator.ofFloat(0, 1).setDuration(defaultDuration);
        mSearchingAnimator = ValueAnimator.ofFloat(0, 1).setDuration(defaultDuration);
        mEndingAnimator = ValueAnimator.ofFloat(0, 1).setDuration(defaultDuration);

        mStartingAnimator.addUpdateListener(mUpdateListener);
        mSearchingAnimator.addUpdateListener(mUpdateListener);
        mEndingAnimator.addUpdateListener(mUpdateListener);

        mStartingAnimator.addListener(mAnimatorListener);
    }
}
