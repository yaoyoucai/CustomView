package shbd.bind2.activity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import shbd.bind2.service.RemoteService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //远程服务
    private ICompute mRemote;

    private Button mBtnService;

    //判断是否已经绑定服务
    private boolean isBind = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bind();

        mBtnService = (Button) findViewById(R.id.btn_start);
        mBtnService.setOnClickListener(this);
    }

    private void bind() {
        Intent intent = new Intent(this, RemoteService.class);
        isBind = bindService(intent, mServiceConnection, Service.BIND_AUTO_CREATE);
    }

    @Override
    public void onClick(View view) {
        String result = null;
        try {
            result = mRemote.add("abc", "edf");
        } catch (RemoteException e) {
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        if (isBind) {
            unbindService(mServiceConnection);
        }
        super.onDestroy();
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mRemote = ICompute.Stub.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };


}
