package com.kappa0923.android.app.shakeanswer.common;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.kappa0923.android.app.shakeanswer.R;
import com.kappa0923.android.app.shakeanswer.activities.MainActivity;

/**
 * 通知バーの制御を行なうクラス
 */
public class NotificationController {
    private static final int INTENT_REQUEST_CODE = 0;

    /**
     * 通知の開始
     * @param context コンテキスト
     */
    public void startNotification(Context context) {
        Notification notification = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentIntent(createLaunchIntent(context))
                .build();
        notification.flags = Notification.FLAG_ONGOING_EVENT;
        NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(INTENT_REQUEST_CODE, notification);
    }

    /**
     * 通知の終了
     * @param context コンテキスト
     */
    public void cancelNotification(Context context) {
        NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(INTENT_REQUEST_CODE);
    }

    /**
     * 通知をタップしたときに起動するActivityへの
     * PendingIntentを返す
     * @param context コンテキスト
     * @return 生成されたPendingIntent
     */
    private PendingIntent createLaunchIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, INTENT_REQUEST_CODE, intent, 0);

        return pendingIntent;
    }
}
