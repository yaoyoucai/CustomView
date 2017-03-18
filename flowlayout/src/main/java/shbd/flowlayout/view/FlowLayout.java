package shbd.flowlayout.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
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
    private Context mContext;
    private List<String> mDatas = new ArrayList<>();
    private List<Line> mLines = new ArrayList<>();

    //块的背景颜色
    private int mPieceBgColor = Color.BLUE;
    //块的字体颜色
    private int mPieceTextColor = Color.BLACK;

    //控件最小宽度
    private int mMinWidth = 200;

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public void setDatas(List<String> datas) {
        this.mDatas = datas;
        addChildView(mContext);
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
        MarginLayoutParams lp = new MarginLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        lp.leftMargin = 50;
        lp.rightMargin = 50;
        lp.topMargin = 50;
        lp.bottomMargin = 50;
        addView(button, lp);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int width_mode = MeasureSpec.getMode(widthMeasureSpec);
        int height_mode = MeasureSpec.getMode(heightMeasureSpec);
        int width_size = MeasureSpec.getSize(widthMeasureSpec);
        int height_size = MeasureSpec.getSize(heightMeasureSpec);

        if (width_mode == MeasureSpec.AT_MOST && width_size > mMinWidth) {
            width_size = mMinWidth;
        }

        int childCount = getChildCount();
        Line line = new Line();
        mLines.clear();
        mLines.add(line);
        for (int index = 0; index < childCount; index++) {
            View childView = getChildAt(index);
            int measuredWidth = childView.getMeasuredWidth() ;
            int width = width_size - getPaddingLeft() - getPaddingRight();
            if (line.getWidth() + measuredWidth > width) {
                line = new Line();
                line.addView(childView);
                mLines.add(line);
            } else {
                line.addView(childView);
            }
        }

        //得到整个控件的高度
        if (height_mode == MeasureSpec.AT_MOST) {
            height_size = getPaddingTop() + getPaddingBottom();
            for (Line curLine : mLines) {
                height_size += curLine.getHeight();
            }
        }
        super.onMeasure(MeasureSpec.makeMeasureSpec(width_size, width_mode),
                MeasureSpec.makeMeasureSpec(height_size, height_mode));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int curHeight = getPaddingTop();
        for (int index = 0; index < mLines.size(); index++) {
            Line line = mLines.get(index);
            List<View> childViewList = line.getViewList();

            int curWidth = getPaddingLeft();
            for (int i = 0; i < childViewList.size(); i++) {

                View childView = childViewList.get(i);
                childView.layout(curWidth, curHeight,
                        curWidth + childView.getMeasuredWidth(),
                        curHeight + line.getHeight());

                curWidth += childView.getMeasuredWidth();

            }

            curHeight += line.getHeight();
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.drawColor(Color.RED);
        super.dispatchDraw(canvas);
    }

    /**
     * 代表每一行
     */
    private static class Line {
        private List<View> mViewList;

        //行宽和高
        private int lineWidth;
        private int lineHeight;

        public Line() {
            mViewList = new ArrayList<>();
        }

        public List<View> getViewList() {
            return mViewList;
        }

        public void addView(View view) {
            mViewList.add(view);
            lineWidth += view.getMeasuredWidth();
            lineHeight = Math.max(lineHeight, view.getMeasuredHeight());
        }

        public int getHeight() {
            return lineHeight;
        }

        public int getWidth() {
            return lineWidth;
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(mContext, attrs);
    }
}
