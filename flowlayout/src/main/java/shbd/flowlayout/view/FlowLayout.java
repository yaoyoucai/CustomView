package shbd.flowlayout.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：CustomView
 * 类描述：
 * 创建人：yh
 * 创建时间：2017/3/17 16:09
 * 修改人：yh
 * 修改时间：2017/3/17 16:09
 * 修改备注：
 */
public class FlowLayout extends ViewGroup {
    private List<String> mDatas = new ArrayList<>();
    private List<Line> mLines = new ArrayList<>();

    //块的背景颜色
    private int mPieceBgColor = Color.BLUE;
    //块的字体颜色
    private int mPieceTextColor = Color.BLACK;

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        addChildView(context);
    }

    /**
     * 添加子控件
     */
    private void addChildView(Context context) {
        for (String data : mDatas) {
            addPieceButton(context, data);
        }
    }

    private void addPieceButton(Context context, String data) {
        Button button = new Button(context);
        button.setText(data);
        button.setTextColor(mPieceTextColor);
        button.setBackgroundColor(mPieceBgColor);

        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        addView(button, lp);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
    }

    public void setDatas(List<String> datas) {
        this.mDatas = datas;
    }

    /**
     * 代表每一行
     */
    private static class Line {
        public int getHeight() {
            return 0;
        }

        public int getWidth() {
            return 0;
        }
    }
}
