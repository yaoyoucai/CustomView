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
        addView(button, lp);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width_mode = MeasureSpec.getMode(widthMeasureSpec);
        int height_mode = MeasureSpec.getMode(heightMeasureSpec);
        int width_size = MeasureSpec.getSize(widthMeasureSpec);
        int height_size = MeasureSpec.getSize(heightMeasureSpec);

        LayoutParams layoutParams = getLayoutParams();
        int height = layoutParams.height;

        //当传入的mode为at_most时的宽度
        int maxWidth = 0;

        int childCount = getChildCount();
        Line line = new Line();
        mLines.clear();
        mLines.add(line);
        for (int index = 0; index < childCount; index++) {
            View childView = getChildAt(index);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams lp = (MarginLayoutParams) childView.getLayoutParams();

            int childWidth = childView.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            int width = width_size - getPaddingLeft() - getPaddingRight();
            if (line.getWidth() + childWidth > width) {
                maxWidth = Math.max(childWidth, line.getWidth());
                line = new Line();
                line.addView(childView);
                mLines.add(line);
            } else {
                line.addView(childView);
            }

            if (index == childCount - 1) {
                maxWidth = Math.max(maxWidth, line.getWidth());
            }
        }

        //得到整个控件的高度
        if (height_mode == MeasureSpec.AT_MOST) {
            height_size = getPaddingTop() + getPaddingBottom();
            for (Line curLine : mLines) {
                height_size += curLine.getHeight();
            }
            width_size = maxWidth;
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
            MarginLayoutParams lp = (MarginLayoutParams) view.getLayoutParams();
            lineWidth += view.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            lineHeight = Math.max(lineHeight, view.getMeasuredHeight() + lp.topMargin + lp.bottomMargin);
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
