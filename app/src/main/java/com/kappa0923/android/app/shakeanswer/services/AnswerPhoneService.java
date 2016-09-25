package com.kappa0923.android.app.shakeanswer.services;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.kappa0923.android.app.shakeanswer.R;
import com.kappa0923.android.app.shakeanswer.common.CallReceiver;
import com.kappa0923.android.app.shakeanswer.common.NotificationController;

/**
 * ブロードキャストと端末の動作を検出するクラスを
 * 管理するためのクラス
 */
public class AnswerPhoneService extends Service implements CallReceiver.CallStateListener {
    private CallReceiver mCallReceiver = new CallReceiver();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        registerCallReceiver();
        new NotificationController().startNotification(getApplicationContext());
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(mCallReceiver);
        new NotificationController().cancelNotification(getApplicationContext());
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

    /**
     * 着信を検出するReceiverを登録する
     */
    private void registerCallReceiver() {
        mCallReceiver.registerCallStateListener(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction(getString(R.string.action_phone_state));
        registerReceiver(mCallReceiver, filter);
    }
}
