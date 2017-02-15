package shbd.beziercurve.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;

import shbd.beziercurve.R;
import shbd.beziercurve.view.CubicBezierView;

public class CubicalBezierActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    private RadioGroup mRgControl;
    private CubicBezierView mCubicBezierView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cubical_bezier);
        mRgControl = (RadioGroup) findViewById(R.id.rg_control);
        mCubicBezierView = (CubicBezierView) findViewById(R.id.cubic_bezier_view);

        mRgControl.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.rb_left_control:
                mCubicBezierView.setControlLeft(true);
                break;
            case R.id.rb_right_control:
                mCubicBezierView.setControlLeft(false);
                break;
        }
    }
}
