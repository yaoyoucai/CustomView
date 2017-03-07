package shbd.customview.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import shbd.customview.aidl.ICompute;

public class ComputeService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    private IBinder binder = new ICompute.Stub() {
        @Override
        public String strcat(String x, String y) throws RemoteException {
            return x + y;
        }
    };
}
