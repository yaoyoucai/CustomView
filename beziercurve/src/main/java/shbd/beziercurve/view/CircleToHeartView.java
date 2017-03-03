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
 * 创建时间：2017/2/15 11:32
 * 修改人：yh
 * 修改时间：2017/2/15 11:32
 * 修改备注：
 */
public class CircleToHeartView extends View {
    private static final float C = 0.551915024494f;     // 一个常量，用来计算绘制圆形贝塞尔曲线控制点的位置

    private Paint mPaint;
    private int mCenterX, mCenterY;

    private PointF mCenter = new PointF(0, 0);
    private float mCircleRadius = 200;                  // 圆的半径
    private float mDifference = mCircleRadius * C;        // 圆形的控制点与数据点的差值

    private PointF[] mData = new PointF[4];               // 顺时针记录绘制圆形的四个数据点
    private PointF[] mCtrl = new PointF[8];              // 顺时针记录绘制圆形的八个控制点

    private float mDuration = 1000;                     // 变化总时长
    private float mCurrent = 0;                         // 当前已进行时长
    private float mCount = 100;                         // 将时长总共划分多少份
    private float mPiece = mDuration / mCount;            // 每一份的时长

    public CircleToHeartView(Context context) {
        this(context, null);
    }

    public CircleToHeartView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPaint=new Paint();
        mPaint.setColor(0xFFfe626d);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(1);
        mPaint.setAntiAlias(true);

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
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mCenterX = w / 2;
        mCenterY = h / 2;
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(mCenterX, mCenterY);
        canvas.scale(1, -1);

        //绘制坐标系
        drawCoordinateSystem(canvas);

        //绘制辅助线
        drawAuxiliaryLine(canvas);

        //绘制bezier曲线
        mPaint.setColor(Color.RED);
        Path path = new Path();
        path.moveTo(mData[0].x, mData[0].y);
        for (int i = 0, j = 0; i < 4; i++, j += 2) {
            if (i == 3) {
                path.cubicTo(mCtrl[j].x, mCtrl[j].y, mCtrl[j + 1].x, mCtrl[j + 1].y, mData[0].x, mData[0].y);
            } else {
                path.cubicTo(mCtrl[j].x, mCtrl[j].y, mCtrl[j + 1].x, mCtrl[j + 1].y, mData[i + 1].x, mData[i + 1].y);
            }
        }
        canvas.drawPath(path, mPaint);

        mCurrent += mPiece;
        if (mCurrent < mDuration) {
            mData[1].y -= 120 / mCount;
            mCtrl[4].x += 20 / mCount;
            mCtrl[5].y += 80 / mCount;
            mCtrl[6].y += 80 / mCount;
            mCtrl[7].x -= 20 / mCount;
            postInvalidateDelayed((long) mPiece);
        }
        super.onDraw(canvas);
    }

    private void drawAuxiliaryLine(Canvas canvas) {
        canvas.save();

        mPaint.setColor(Color.GRAY);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(20);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);

        //绘制数据点和控制点
        for (int i = 0; i < 8; i++) {
            if (i < 4) {
                canvas.drawPoint(mData[i].x, mData[i].y, mPaint);
            }
            canvas.drawPoint(mCtrl[i].x, mCtrl[i].y, mPaint);
        }

        mPaint.setStrokeWidth(4);
        //绘制辅助线
        for (int i = 1, j = 1; i <= 3; i++, j += 2) {
            canvas.drawLine(mData[i].x, mData[i].y, mCtrl[j].x, mCtrl[j].y, mPaint);
            canvas.drawLine(mData[i].x, mData[i].y, mCtrl[j + 1].x, mCtrl[j + 1].y, mPaint);
        }
        canvas.drawLine(mData[0].x, mData[0].y, mCtrl[0].x, mCtrl[0].y, mPaint);
        canvas.drawLine(mData[0].x, mData[0].y, mCtrl[7].x, mCtrl[7].y, mPaint);
    }

    private void drawCoordinateSystem(Canvas canvas) {
        canvas.save();

        Paint mCoordinatePaint = new Paint();
        mCoordinatePaint.setAntiAlias(true);
        mCoordinatePaint.setStrokeWidth(4);
        mCoordinatePaint.setColor(Color.RED);
        mCoordinatePaint.setStyle(Paint.Style.STROKE);

        canvas.drawLine(-2000, 0, 2000, 0, mCoordinatePaint);
        canvas.drawLine(0, 2000, 0, -2000, mCoordinatePaint);

        canvas.restore();
    }
}
