package shbd.propertyanimation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import shbd.propertyanimation.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mBtnEvaluator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnEvaluator = (Button) findViewById(R.id.btn_evaluator);
        mBtnEvaluator.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.btn_evaluator:
                intent.setClass(this, EvaluatorActivity.class);
                break;
        }
        startActivity(intent);
    }
}
