package shbd.scroller.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

/**
 * 项目名称：CustomView
 * 类描述：
 * 创建人：yh
 * 创建时间：2017/3/11 14:59
 * 修改人：yh
 * 修改时间：2017/3/11 14:59
 * 修改备注：
 */
public class SpringbackView extends View {
    private float mStartX;
    private float mStartY;

    private Scroller mScroller;

    public SpringbackView(Context context) {
        this(context, null);
    }

    public SpringbackView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLUE);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        View viewParent = (View) getParent();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStartX = event.getX();
                mStartY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                float y = event.getY();
                int dx = (int) (x - mStartX);
                int dy = (int) (y - mStartY);
                viewParent.scrollBy(-dx, -dy);
                break;
            case MotionEvent.ACTION_UP:
                mScroller.startScroll(viewParent.getScrollX(),viewParent. getScrollY(),
                                      -viewParent.getScrollX(), -viewParent.getScrollY(), 500);
                invalidate();
                break;
        }
        return true;
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            View viewParent = (View) getParent();
            viewParent.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }
}
