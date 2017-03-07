package shbd.customview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
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
public class TwitterView extends View {
  /*  private int width = 400;
    private int height = 400;

    private Bitmap disBmp;
    private Bitmap srcBmp;

    private Paint mPaint;

    public TwitterView(Context context) {
        this(context, null);
    }

    public TwitterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        disBmp = BitmapFactory.decodeResource(getResources(), R.drawable.light);
        srcBmp = BitmapFactory.decodeResource(getResources(), R.drawable.bg);
        mPaint = new Paint();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        int layerID = canvas.saveLayer(0, 0, getWidth(), getHeight(), mPaint, Canvas.ALL_SAVE_FLAG);
        canvas.drawBitmap(disBmp, 0, 0, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));
        canvas.drawBitmap(srcBmp, 0, 0, mPaint);

        canvas.restoreToCount(layerID);
    }*/


    private Paint mBitPaint;
    private Bitmap BmpDST,BmpSRC;
    public TwitterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mBitPaint = new Paint();
     /*   BmpDST = BitmapFactory.decodeResource(getResources(),R.drawable.light,null);
        BmpSRC = BitmapFactory.decodeResource(getResources(),R.drawable.bg,null);*/
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int layerId = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);

        canvas.drawBitmap(BmpDST,0,0,mBitPaint);
        mBitPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));
        canvas.drawBitmap(BmpSRC,0,0,mBitPaint);

        mBitPaint.setXfermode(null);
        canvas.restoreToCount(layerId);
    }
}
