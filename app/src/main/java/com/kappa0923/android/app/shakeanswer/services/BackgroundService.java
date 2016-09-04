package com.kappa0923.android.app.shakeanswer.services;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.kappa0923.android.app.shakeanswer.R;
import com.kappa0923.android.app.shakeanswer.common.CallReceiver;

/**
 * ブロードキャストと端末の動作を検出するクラスを
 * 管理するためのクラス
 */
public class BackgroundService extends Service implements CallReceiver.CallStateListener {
    private CallReceiver mCallReceiver;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        mCallReceiver = new CallReceiver();
        mCallReceiver.registerCallStateListener(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction(getString(R.string.action_phone_state));
        registerReceiver(mCallReceiver, filter);
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(mCallReceiver);
    }

    @Override
    public void onRinging() {

    }

    @Override
    public void onOffhook() {

    }

    @Override
    public void onIdle() {

    }
}
