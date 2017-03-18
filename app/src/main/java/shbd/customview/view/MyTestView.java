package shbd.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * 项目名称：CustomView
 * 类描述：
 * 创建人：yh
 * 创建时间：2017/3/10 7:02
 * 修改人：yh
 * 修改时间：2017/3/10 7:02
 * 修改备注：
 */
public class MyTestView extends View {
    private Paint mPaint;

    public MyTestView(Context context) {
        this(context, null);
    }

    public MyTestView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(getWidth() / 2, getHeight() / 2);
        canvas.drawColor(Color.RED);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("TAG", "x: " + event.getX() + ",rawx" + event.getRawX());
        return true;
    }
}
