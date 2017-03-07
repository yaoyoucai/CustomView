package shbd.customview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

import shbd.customview.R;

/**
 * 项目名称：CustomView
 * 类描述：
 * 创建人：yh
 * 创建时间：2017/3/2 16:45
 * 修改人：yh
 * 修改时间：2017/3/2 16:45
 * 修改备注：
 */
public class DogView extends View {
    Bitmap mBmpSrc;
    Bitmap mBmpDis;

    private Paint mPaint;

    public DogView(Context context) {
        this(context, null);
    }

    public DogView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mBmpSrc = BitmapFactory.decodeResource(getResources(), R.drawable.dog,null);
        mBmpDis = BitmapFactory.decodeResource(getResources(), R.drawable.dog_shade,null);
        mPaint=new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
            int layerId = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
        canvas.drawBitmap(mBmpDis, 0, 0, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(mBmpSrc, 0, 0, mPaint);
        mPaint.setXfermode(null);
        canvas.restoreToCount(layerId);

    }
}
