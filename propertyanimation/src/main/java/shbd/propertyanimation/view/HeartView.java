package shbd.propertyanimation.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * 项目名称：CustomView
 * 类描述：
 * 创建人：yh
 * 创建时间：2017/2/16 14:02
 * 修改人：yh
 * 修改时间：2017/2/16 14:02
 * 修改备注：
 */
public class HeartView extends LinearLayout implements View.OnClickListener {
    public HeartView(Context context) {
        this(context, null);
    }

    public HeartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        Button button = new Button(getContext());
        button.setOnClickListener(this);
        button.setText("添加一个心");
    }

    @Override
    public void onClick(View view) {
        addHeart();
    }

    private void addHeart() {

    }
}
