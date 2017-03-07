package shbd.customview.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import shbd.customview.R;
import shbd.customview.view.AddressPickView;

public class ActivityA extends AppCompatActivity implements AddressPickView.OnPickerClickListener, View.OnClickListener {
    AddressPickView mAddressPickView;

    private Button mBtnStart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAddressPickView = new AddressPickView(this);
        mAddressPickView.setOnPickerClickListener(this);
        mBtnStart = (Button) findViewById(R.id.btn_start);
        mBtnStart.setOnClickListener(this);
    }

    @Override
    public void onPickerClick(String selectData, String zipCode) {

    }

    @Override
    public void onClick(View v) {
        mAddressPickView.show(mBtnStart);

    }
}
