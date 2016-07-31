package com.kappa0923.android.app.shakeanswer.activity.common;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * 端末が振られた時のリスナを管理するクラス
 */
public class ShakeManager implements SensorEventListener {
    private SensorManager mSensorManager;
    private ShakeListener mShakeListener;

    public void startShakeListener(Context context) {
        mSensorManager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void setOnShakeListener(ShakeListener listener) {
        this.mShakeListener = listener;
    }

    public interface ShakeListener {
        void onShake();
    }
}
