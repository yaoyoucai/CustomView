package shbd.flowlayout.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import shbd.flowlayout.view.FlowLayout;

public class MainActivity extends AppCompatActivity {
    private FlowLayout mFLowLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFLowLayout = (FlowLayout) findViewById(R.id.flow_layout)
    }
}
