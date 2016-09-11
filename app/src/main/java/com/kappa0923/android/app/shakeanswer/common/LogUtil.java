package com.kappa0923.android.app.shakeanswer.common;

import android.util.Log;

/**
 * 自作Utilクラス
 */
public class LogUtil {
    private static final String TAG = "log_shake_answer";

    /**
     * 自作ログクラス
     * ログ形式 クラス名.メソッド名:ログテキスト
     * @param logLevel ログレベル
     * @param logText  ログテキスト
     */
    public static void log(int logLevel, String logText) {
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[2];
        String method = stackTraceElement.getClassName() + "." + stackTraceElement.getMethodName() + ":";

        switch (logLevel) {
            case Log.DEBUG:
                Log.d(TAG, method + logText);
                break;
            case Log.INFO:
                Log.i(TAG, method + logText);
                break;
            case Log.ERROR:
                Log.e(TAG, method + logText);
                break;
            case Log.VERBOSE:
                Log.v(TAG, method + logText);
                break;
        }
    }
}
