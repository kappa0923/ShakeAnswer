package com.kappa0923.android.app.shakeanswer.activity.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

/**
 * 電話の着信を検出するためのクラス
 */
public class CallReceiver extends BroadcastReceiver {
    private CallStateListener mCallStateListener;

    @Override
    public void onReceive(Context context, Intent intent) {
        PhoneStateListener phoneStateListener = new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String number) {
                if (mCallStateListener == null) return;
                switch (state) {
                    case TelephonyManager.CALL_STATE_RINGING:
                        mCallStateListener.onRinging();
                        break;
                    case TelephonyManager.CALL_STATE_OFFHOOK:
                        mCallStateListener.onOffhook();
                        break;
                    case TelephonyManager.CALL_STATE_IDLE:
                        mCallStateListener.onIdle();
                        break;
                }
            }
        };

        TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
    }

    public void setCallStateListener(CallStateListener callStateListener) {
        mCallStateListener = callStateListener;
    }

    public interface CallStateListener {
        public void onRinging();
        public void onOffhook();
        public void onIdle();
    }
}
