package shbd.customview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * 项目名称：CustomView
 * 类描述：
 * 创建人：yh
 * 创建时间：2017/3/2 14:15
 * 修改人：yh
 * 修改时间：2017/3/2 14:15
 * 修改备注：
 */
public class TestView extends View {
    private int width = 400;
    private int height = 400;

    private Bitmap disBmp;
    private Bitmap srcBmp;

    private Paint mPaint;

    public TestView(Context context) {
        this(context, null);
    }

    public TestView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        disBmp = makeDst(width, height);
        srcBmp = makeSrc(width, height);
        /*disBmp = BitmapFactory.decodeResource(getResources(), R.drawable.dis);
        srcBmp = BitmapFactory.decodeResource(getResources(), R.drawable.src);*/
        mPaint = new Paint();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        int layerID = canvas.saveLayer(0, 0, width * 2, height * 2, mPaint, Canvas.ALL_SAVE_FLAG);
        canvas.drawBitmap(disBmp, 0, 0, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));
        canvas.drawBitmap(srcBmp, width / 2, height / 2, mPaint);
        canvas.restoreToCount(layerID);

  /*      int layerID = canvas.saveLayer(0, 0, getWidth(), getHeight(), mPaint, Canvas.ALL_SAVE_FLAG);
        canvas.drawBitmap(disBmp, 0, 0, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.LIGHTEN));
        canvas.drawBitmap(srcBmp, 0, 0, mPaint);

        canvas.restoreToCount(layerID);*/
    }

    private Bitmap makeDst(int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(0xFFFFCC44);
        canvas.drawOval(new RectF(0, 0, width, height), mPaint);
        return bitmap;
    }


    private Bitmap makeSrc(int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(0xFF66AAFF);
        canvas.drawRect(new RectF(0, 0, width, height), mPaint);
        return bitmap;
    }
}
