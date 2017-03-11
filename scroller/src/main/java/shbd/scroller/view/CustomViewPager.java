package shbd.scroller.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * 项目名称：CustomView
 * 类描述：
 * 创建人：yh
 * 创建时间：2017/3/11 16:03
 * 修改人：yh
 * 修改时间：2017/3/11 16:03
 * 修改备注：
 */
public class CustomViewPager extends ViewGroup {
    //左右边界
    private int leftBorder;
    private int rightBorder;

    private float mXDown;
    private float mXMove;
    private float mLastMove;

    private int mMinSlop;

    private Scroller mScroller;

    public CustomViewPager(Context context) {
        this(context, null);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mMinSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mScroller = new Scroller(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        for (int index = 0; index < childCount; index++) {
            measureChild(getChildAt(index), widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        int childCount = getChildCount();
        for (int index = 0; index < childCount; index++) {
            View view = getChildAt(index);
            view.layout(index * view.getMeasuredWidth(), 0, (index + 1) * view.getMeasuredWidth(), view.getMeasuredHeight());
        }
        leftBorder = getChildAt(0).getLeft();
        rightBorder = getChildAt(getChildCount() - 1).getRight();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mXDown = ev.getRawX();
                mLastMove = mXDown;
                break;
            case MotionEvent.ACTION_MOVE:
                mXMove = ev.getRawX();
                float dx = Math.abs(mXMove - mXDown);
                if (dx > mMinSlop) {
                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                mXMove = event.getRawX();
                int scrollX = (int) (mLastMove - mXMove);
                scrollBy(scrollX, 0);
                mLastMove = mXMove;
                break;
            case MotionEvent.ACTION_UP:
                int dx = 0;
                if (getScrollX() < leftBorder) {
                    dx = -getScrollX();
                } else if (getScrollX() + getWidth() > rightBorder) {
                    dx = rightBorder - getWidth() - getScrollX();
                } else {
                    int childIndex = (getScrollX() + getWidth() / 2) / getWidth();
                    dx = childIndex * getWidth() - getScrollX();
                }
                mScroller.startScroll(getScrollX(), getScrollY(), dx, 0);
                invalidate();
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }
}
