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
 * 创建时间：2017/2/15 10:44
 * 修改人：yh
 * 修改时间：2017/2/15 10:44
 * 修改备注：
 */
public class CubicBezierView extends View {
    //可拖动控制点
    private boolean isLeftControl = true;

    private Paint mPaint;

    private PointF start, end, control1, control2;

    public CubicBezierView(Context context) {
        this(context, null);
    }

    public CubicBezierView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);


        start = new PointF();
        end = new PointF();
        control1 = new PointF();
        control2 = new PointF();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        int centerX = w / 2;
        int centerY = h / 2;

        start.x = centerX - 500;
        start.y = centerY;

        end.x = centerX + 500;
        end.y = centerY;

        control1.x = centerX - 500;
        control1.y = centerY - 500;

        control2.x = centerX + 500;
        control2.y = centerY - 500;

        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isLeftControl) {
            control1.x = event.getX();
            control1.y = event.getY();
        } else {
            control2.x = event.getX();
            control2.y = event.getY();
        }

        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //绘制数据点和控制点
        mPaint.setColor(Color.GRAY);
        mPaint.setStrokeWidth(20);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawPoint(start.x, start.y, mPaint);
        canvas.drawPoint(end.x, end.y, mPaint);
        canvas.drawPoint(control1.x, control1.y, mPaint);
        canvas.drawPoint(control2.x, control2.y, mPaint);

        //绘制辅助线
        mPaint.setStrokeWidth(4);
        canvas.drawLine(start.x, start.y, control1.x, control1.y, mPaint);
        canvas.drawLine(control1.x, control1.y, control2.x, control2.y, mPaint);
        canvas.drawLine(control2.x, control2.y, end.x, end.y, mPaint);

        //绘制bezier曲线
        mPaint.setColor(Color.RED);
        Path path = new Path();
        path.moveTo(start.x, start.y);
        path.cubicTo(control1.x, control1.y, control2.x, control2.y, end.x, end.y);
        canvas.drawPath(path, mPaint);

        super.onDraw(canvas);

    }

    public void setControlLeft(boolean isLeftControl) {
        this.isLeftControl = isLeftControl;
    }
}
