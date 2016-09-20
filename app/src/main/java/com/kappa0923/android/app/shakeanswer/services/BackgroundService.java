package com.kappa0923.android.app.shakeanswer.services;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.KeyEvent;

import com.kappa0923.android.app.shakeanswer.R;
import com.kappa0923.android.app.shakeanswer.common.CallReceiver;
import com.kappa0923.android.app.shakeanswer.common.ShakeManager;

/**
 * ブロードキャストと端末の動作を検出するクラスを
 * 管理するためのクラス
 */
public class BackgroundService extends Service implements CallReceiver.CallStateListener, ShakeManager.ShakeListener {
    private static final int SPECIFIED_COUNT = 10;

    private CallReceiver mCallReceiver = new CallReceiver();
    private ShakeManager mShakeManager;
    private int mShakeCount;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        registerCallReceiver();
        initService();
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(mCallReceiver);
    }

    @Override
    public void onRinging() {
        if (mShakeManager == null) {
            mShakeManager = new ShakeManager();
            mShakeManager.setOnShakeListener(this);
            mShakeManager.startShakeListener(getApplicationContext());
        }

    }

    @Override
    public void onOffhook() {
        initService();
    }

    @Override
    public void onIdle() {
        initService();
    }

    @Override
    public void onShake() {
        mShakeCount++;
        if (mShakeCount > SPECIFIED_COUNT) {
            doCatchPhone();
        }
    }

    /**
     * Serviceの初期化
     */
    private void initService() {
        if (mShakeManager != null) {
            mShakeManager.endShakeListener();
            mShakeManager = null;
        }
        mShakeCount = 0;
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

    /**
     * 着信に応答する
     */
    private void doCatchPhone() {
        Intent btnUp = new Intent(Intent.ACTION_MEDIA_BUTTON);
        btnUp.putExtra(Intent.EXTRA_KEY_EVENT,
                new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_HEADSETHOOK));
        sendOrderedBroadcast(btnUp, "android.permission.CALL_PRIVILEGED");
        initService();
    }
}
