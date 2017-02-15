package shbd.beziercurve.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 项目名称：CustomView
 * 类描述：
 * 创建人：yh
 * 创建时间：2017/2/15 10:01
 * 修改人：yh
 * 修改时间：2017/2/15 10:01
 * 修改备注：
 */
public class QuadBezierView extends View {
    private Paint mPaint;

    private PointF start, end, control;

    public QuadBezierView(Context context) {
        this(context, null);
    }

    public QuadBezierView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);

        start = new PointF();
        end = new PointF();
        control = new PointF();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        int centerX = w / 2;
        int centerY = h / 2;

        start.x = centerX - 200;
        start.y = centerY;

        end.x = centerX + 200;
        end.y = centerY;

        control.x = centerX;
        control.y = centerY - 200;

        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        control.x = event.getX();
        control.y = event.getY();
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setColor(Color.GRAY);
        mPaint.setStrokeWidth(20);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        //绘制控制点和数据点
        canvas.drawPoint(start.x, start.y, mPaint);
        canvas.drawPoint(end.x, end.y, mPaint);
        canvas.drawPoint(control.x, control.y, mPaint);

        //绘制辅助线
        mPaint.setStrokeWidth(4);
        canvas.drawLine(start.x, start.y, control.x, control.y, mPaint);
        canvas.drawLine(end.x, end.y, control.x, control.y, mPaint);

        //绘制bezier曲线
        mPaint.setColor(Color.RED);
        Path path = new Path();
        path.moveTo(start.x, start.y);
        path.quadTo(control.x, control.y, end.x, end.y);
        canvas.drawPath(path, mPaint);

        super.onDraw(canvas);
    }
}
