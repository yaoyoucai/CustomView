package shbd.propertyanimation.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import shbd.propertyanimation.R;
import shbd.propertyanimation.view.EvaluatorView;

public class EvaluatorActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mBtnStart;
    private EvaluatorView mEvaluatorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluator);
        mBtnStart = (Button) findViewById(R.id.btn_start);
        mEvaluatorView = (EvaluatorView) findViewById(R.id.evaluator_view);

        mBtnStart.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        mEvaluatorView.startMove();
    }
}
