package shbd.beziercurve.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * 项目名称：CustomView
 * 类描述：
 * 创建人：yh
 * 创建时间：2017/3/3 15:21
 * 修改人：yh
 * 修改时间：2017/3/3 15:21
 * 修改备注：
 */
public class RedDragPointView extends FrameLayout {
    private int radius = 60;
    private Paint mPaint;
    private Path mPath;

    private boolean mTouch = false;

    //圆的圆心坐标
    private PointF startPoint;
    //手指在屏幕移动的坐标
    private PointF movePoint;
    //四个数据点和一个控制点


    public RedDragPointView(Context context) {
        this(context, null);
    }

    public RedDragPointView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        initPoint();
        mPath = new Path();
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
    }


    private void initPoint() {
        startPoint = new PointF(200, 200);
        movePoint = new PointF();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.saveLayer(0, 0, getWidth(), getHeight(), mPaint, Canvas.ALL_SAVE_FLAG);

        canvas.drawCircle(startPoint.x, startPoint.y, radius, mPaint);

        if (mTouch) {
            cacluatePath();
            canvas.drawCircle(movePoint.x,movePoint.y, radius, mPaint);
            canvas.drawPath(mPath, mPaint);

        }
        canvas.restore();
        super.dispatchDraw(canvas);
    }

    private void cacluatePath() {
        double a = Math.atan((movePoint.y - startPoint.y) / (movePoint.x - movePoint.x));
        float offsetX = (float) (radius * Math.sin(a));
        float offsetY = (float) (radius * Math.cos(a));

        float x1 = startPoint.x + offsetX;
        float y1 = startPoint.y - offsetY;

        float x2 = movePoint.x + offsetX;
        float y2 = movePoint.y - offsetY;

        float x3 = movePoint.x - offsetX;
        float y3 = movePoint.y + offsetY;

        float x4 = startPoint.x - offsetX;
        float y4 = startPoint.y + offsetY;

        float controlX = (startPoint.x + movePoint.x) / 2;
        float controlY = (startPoint.y + movePoint.y) / 2;

        mPath.reset();
        mPath.moveTo(x1, y1);
        mPath.quadTo(controlX, controlY, x2, y2);
        mPath.lineTo(x3, y3);
        mPath.quadTo(controlX, controlY, x4, y4);
        mPath.lineTo(x1, y1);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mTouch = true;
                break;
            case MotionEvent.ACTION_UP:
                mTouch = false;
                break;
        }
        movePoint.set(event.getX(), event.getY());
        invalidate();
        return true;
    }
}
