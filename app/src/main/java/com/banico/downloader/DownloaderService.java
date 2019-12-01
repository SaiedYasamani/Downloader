package com.banico.downloader;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class DownloaderService extends Service {

    private final String ACTION_DOWNLOAD = "action_download";

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CreateNotificationChannel();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent.getExtras() != null){

            String action = intent.getExtras().getString("action");
            if(action != null){
                switch (action) {
                    case ACTION_DOWNLOAD:
                        CreateNotification();
                        break;
                }
            }
        }
        return START_STICKY;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void CreateNotificationChannel() {
        NotificationChannel notificationChannel = new NotificationChannel("mychanell", "mychanell", NotificationManager.IMPORTANCE_DEFAULT);
        NotificationManagerCompat.from(this).createNotificationChannel(notificationChannel);
    }

    private void CreateNotification() {
        Notification notification = new NotificationCompat.Builder(this, "mychanell")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentText("")
                .setAutoCancel(false)
                .setSound(null)
                .setVibrate(null)
                .setPriority(NotificationManagerCompat.IMPORTANCE_DEFAULT)
                .setProgress(100, 0, false)
                .build();
        NotificationManagerCompat.from(this).notify(1, notification);
        startForeground(1, notification);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
