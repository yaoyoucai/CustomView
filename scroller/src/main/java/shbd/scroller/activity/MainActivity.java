package shbd.scroller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mBtnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnStart = (Button) findViewById(R.id.btn_start);
        mBtnStart.setOnClickListener(this);
        Log.e("tag", " MainActivity onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("tag", " MainActivity onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("tag", " MainActivity onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("tag", " MainActivity onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("tag", " MainActivity onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("tag", " MainActivity onDestroy");
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.btn_start:
                intent.setClass(this, SecondActivity.class);
                break;
        }

        getApplicationContext().startActivity(intent);

    }
}
