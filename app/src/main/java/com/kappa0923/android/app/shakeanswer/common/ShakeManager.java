package com.kappa0923.android.app.shakeanswer.common;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * 端末が振られた時のリスナを管理するクラス
 */
public class ShakeManager implements SensorEventListener {
    private final static int TIME_THRESHOLD = 100;
    private final static int VELOCITY_THRESHOLD = 30;
    private SensorManager mSensorManager;
    private ShakeListener mShakeListener;
    private long mLastTime = 0;
    private float mLastX = -1.0f, mLastY = -1.0f, mLastZ = -1.0f;

    /**
     * 加速度センサの開始
     * @param context コンテキスト
     */
    public void startShakeListener(Context context) {
        mSensorManager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
    }

    /**
     * 加速度センサの終了
     */
    public void endShakeListener() {
        if (mSensorManager != null) {
            mSensorManager.unregisterListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
            mSensorManager = null;
        }
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        long nowTime = System.currentTimeMillis();
        if (nowTime - mLastTime > TIME_THRESHOLD) {
            float velocitySec = Math.abs(event.values[0] + event.values[1] + event.values[2] - mLastX - mLastY - mLastZ);
            if (velocitySec > VELOCITY_THRESHOLD) {
                if (mShakeListener != null) {
                    mShakeListener.onShake();
                }
            }
            mLastTime = nowTime;
            mLastX = event.values[0];
            mLastY = event.values[1];
            mLastZ = event.values[2];
        }
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
