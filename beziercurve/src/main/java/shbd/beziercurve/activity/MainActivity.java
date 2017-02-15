package shbd.beziercurve.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import shbd.beziercurve.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mBtnQuad;
    private Button mBtnCubi;
    private Button mBtnCircleToHeart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnQuad = (Button) findViewById(R.id.btn_cubi);
        mBtnCubi = (Button) findViewById(R.id.btn_quad);
        mBtnCircleToHeart = (Button) findViewById(R.id.btn_circle_to_heart);

        mBtnQuad.setOnClickListener(this);
        mBtnCubi.setOnClickListener(this);
        mBtnCircleToHeart.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.btn_quad:
                intent.setClass(this, QuadraticBezierActivity.class);
                break;
            case R.id.btn_cubi:
                intent.setClass(this, CubicalBezierActivity.class);
                break;
            case R.id.btn_circle_to_heart:
                intent.setClass(this, CircleToHeartActivity.class);
                break;
        }
        startActivity(intent);
    }
}
