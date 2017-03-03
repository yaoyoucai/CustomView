package shbd.beziercurve.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

/**
 * 项目名称：CustomView
 * 类描述：
 * 创建人：yh
 * 创建时间：2017/2/22 10:56
 * 修改人：yh
 * 修改时间：2017/2/22 10:56
 * 修改备注：
 */
public class MagicCircleView extends View {
    private static final float C = 0.551915024494f;     // 一个常量，用来计算绘制圆形贝塞尔曲线控制点的位置

    private Paint mPaint;
    private float mCircleRadius = 200;                  // 圆的半径
    private float mDifference = mCircleRadius * C;        // 圆形的控制点与数据点的差值

    private PointF[] mData = new PointF[4];               // 顺时针记录绘制圆形的四个数据点
    private PointF[] mCtrl = new PointF[8];              // 顺时针记录绘制圆形的八个控制点

    public MagicCircleView(Context context) {
        this(context,null);
    }

    public MagicCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        initPoint();
    }

    private void initPaint() {
        mPaint=new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.FILL);
    }

    private void initPoint() {
        for (int i = 0; i < 8; i++) {
            if (i < 4) {
                PointF dataPoint = new PointF();
                mData[i] = dataPoint;
            }
            PointF ctrlPoint = new PointF();
            mCtrl[i] = ctrlPoint;
        }
        mData[0].x = mCircleRadius;
        mData[0].y = 0;

        mData[1].x = 0;
        mData[1].y = mCircleRadius;

        mData[2].x = -mCircleRadius;
        mData[2].y = 0;

        mData[3].x = 0;
        mData[3].y = -mCircleRadius;

        mCtrl[0].x = mCircleRadius;
        mCtrl[0].y = mDifference;

        mCtrl[1].x = mDifference;
        mCtrl[1].y = mCircleRadius;

        mCtrl[2].x = -mDifference;
        mCtrl[2].y = mCircleRadius;

        mCtrl[3].x = -mCircleRadius;
        mCtrl[3].y = mDifference;

        mCtrl[4].x = -mCircleRadius;
        mCtrl[4].y = -mDifference;


        mCtrl[5].x = -mDifference;
        mCtrl[5].y = -mCircleRadius;

        mCtrl[6].x = mDifference;
        mCtrl[6].y = -mCircleRadius;

        mCtrl[7].x = mCircleRadius;
        mCtrl[7].y = -mDifference;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(500,getHeight()/2);
        canvas.scale(1, -1);

        Path path=new Path();
        for (int i = 0, j = 0; i < 4; i++, j += 2) {
            path.moveTo(mData[i].x, mData[i].y);
            if (i == 3) {
                path.cubicTo(mCtrl[j].x, mCtrl[j].y, mCtrl[j + 1].x, mCtrl[j + 1].y, mData[0].x, mData[0].y);
            } else {
                path.cubicTo(mCtrl[j].x, mCtrl[j].y, mCtrl[j + 1].x, mCtrl[j + 1].y, mData[i + 1].x, mData[i + 1].y);
            }
            canvas.drawPath(path, mPaint);
        }
        super.onDraw(canvas);
    }
}
