package shbd.path.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * 项目名称：CustomView
 * 类描述：
 * 创建人：yh
 * 创建时间：2017/2/25 10:04
 * 修改人：yh
 * 修改时间：2017/2/25 10:04
 * 修改备注：
 */
public class RadarView extends View {
    //数据个数
    private int count;
    //最大半径
    private float radius;
    //雷达区画笔
    private Paint mMainPaint;
    //数据区画笔
    private Paint mValuePaint;
    //文本区画笔
    private Paint mTextPaint;
    //等分的弧度值
    private double mRadians;

    private String[] mTitles = {"a", "b", "c", "d", "e", "f"};
    private double[] mValues = {100, 80, 40, 30, 50, 70};
    //数据最大值
    private int mMaxValue = 100;

    public RadarView(Context context) {
        this(context, null);
    }

    public RadarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        count = 6;
        radius = Math.min(w, h) / 2 * 0.9f;
        mRadians = 2 * Math.PI / count;
        super.onSizeChanged(w, h, oldw, oldh);
    }

    private void initPaint() {
        mMainPaint = new Paint();
        mMainPaint.setColor(Color.GRAY);
        mMainPaint.setStrokeWidth(5);
        mMainPaint.setAntiAlias(true);
        mMainPaint.setStyle(Paint.Style.STROKE);

        mTextPaint = new Paint();
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(50);
        mMainPaint.setStyle(Paint.Style.STROKE);

        mValuePaint = new Paint();
        mValuePaint.setColor(Color.BLUE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(getWidth() / 2, getHeight() / 2);
        drawPolygon(canvas);
        drawLines(canvas);
        drawText(canvas);
        drawRigon(canvas);
        super.onDraw(canvas);
    }

    /**
     * 绘制多边形
     *
     * @param canvas
     */
    private void drawPolygon(Canvas canvas) {
        float r = radius / (count - 1);
        Path path = new Path();
        for (int i = 1; i < count; i++) {
            float currentR = r * i;
            path.reset();
            for (int j = 0; j < count; j++) {
                if (j == 0) {
                    path.moveTo(currentR, 0);
                } else {
                    float x = (float) (currentR * Math.cos(mRadians * j));
                    float y = (float) (currentR * Math.sin(mRadians * j));
                    path.lineTo(x, y);
                }
            }
            path.close();
            canvas.drawPath(path, mMainPaint);
        }

    }

    /**
     * 绘制多边形上的线条
     *
     * @param canvas
     */
    private void drawLines(Canvas canvas) {
        Path path = new Path();
        for (int i = 0; i < count; i++) {
            path.reset();
            float x = (float) (radius * Math.cos(mRadians * i));
            float y = (float) (radius * Math.sin(mRadians * i));
            path.lineTo(x, y);
            canvas.drawPath(path, mMainPaint);
        }
    }

    /**
     * 绘制文本
     *
     * @param canvas
     */
    private void drawText(Canvas canvas) {
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float fontHeight = fontMetrics.descent - fontMetrics.ascent;
        for (int i = 0; i < count; i++) {
            float x = (float) ((radius + fontHeight / 2) * Math.cos(mRadians * i));
            float y = (float) ((radius + fontHeight / 2) * Math.sin(mRadians * i));
            if (mRadians >= 0 && mRadians < Math.PI / 2) {
                canvas.drawText(mTitles[i], x, y, mTextPaint);
            }
            if (mRadians >= Math.PI / 2 && mRadians < Math.PI) {
                float dis = mTextPaint.measureText(mTitles[i]);
                canvas.drawText(mTitles[i], x - dis, y, mTextPaint);
            }
            if (mRadians >= Math.PI && mRadians < Math.PI * 3 / 2) {
                float dis = mTextPaint.measureText(mTitles[i]);
                canvas.drawText(mTitles[i], x - dis, y, mTextPaint);
            }
            if (mRadians >= Math.PI * 3 / 2 && mRadians <= 2 * Math.PI) {
                canvas.drawText(mTitles[i], x, y, mTextPaint);
            }

        }
    }

    /**
     * 绘制区域
     *
     * @param canvas
     */
    private void drawRigon(Canvas canvas) {
        Path path = new Path();
        for (int i = 0; i < mValues.length; i++) {
            double percent = mValues[i] / mMaxValue;
            float x = (float) (percent * radius * Math.cos(mRadians * i));
            float y = (float) (percent * radius * Math.sin(mRadians * i));

            if (i == 0) {
                path.moveTo(x, y);
            } else {
                path.lineTo(x, y);
            }
            canvas.drawCircle(x, y, 10, mValuePaint);
        }
        path.close();

        mValuePaint.setAlpha(255);
        mValuePaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path, mValuePaint);

        mValuePaint.setStyle(Paint.Style.FILL);
        mValuePaint.setAlpha(127);
        canvas.drawPath(path, mValuePaint);


    }
}
