package shbd.customview.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import shbd.customview.R;

public class ActivityB extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);
}

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, ActivityC.class);
        startActivity(intent);
    }
}
