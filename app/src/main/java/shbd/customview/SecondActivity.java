package shbd.customview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Log.e("tag", " SecondActivity onCreate");
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.e("tag", " SecondActivity onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("tag", " SecondActivity onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("tag", " SecondActivity onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("tag", " SecondActivity onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("tag", " SecondActivity onDestroy");
    }
}
