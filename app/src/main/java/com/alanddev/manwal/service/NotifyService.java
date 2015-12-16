package com.alanddev.manwal.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.alanddev.manwal.R;
import com.alanddev.manwal.ui.MainActivity;

import java.util.Calendar;
import java.util.Date;

public class NotifyService extends Service {

    public static final int GREETNG_ID = 1;
    private static final int BUDGET_ID = 2;

    public NotifyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        makeNotificationComplete();
        return Service.START_STICKY;
    }

    private void makeNotificationComplete() {
        String contentText="";
        String contentTitle=getResources().getString(R.string.notify_title);
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int dayofweek = calendar.get(Calendar.DAY_OF_WEEK);
        int dayofmonth = calendar.get(Calendar.DAY_OF_MONTH);
        int dayofyear = calendar.get(Calendar.DAY_OF_YEAR);
        if(hour>=7&&hour<=9){
            if(dayofyear==1){
                contentText = getResources().getString(R.string.notify_startyear);
            }else if(dayofmonth==1){
                contentText = getResources().getString(R.string.notify_startmonth);
            }else if(dayofweek==2){
                contentText = getResources().getString(R.string.notify_startweek);
            }else {
                contentTitle = getResources().getString(R.string.notify_morning);
            }
        }else if(hour>=18&&hour<=20){
            contentText = getResources().getString(R.string.notify_afternoon);
        }else if(hour>=22&hour<=23){
            contentText = getResources().getString(R.string.notify_afternoon);
        }
        if(!contentText.equals("")) {
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.mipmap.ic_currency)
                            .setContentTitle(contentTitle)
                            .setContentText(contentText);
            Intent notifyIntent =
                    new Intent(this, MainActivity.class);
            notifyIntent.putExtra("NOTIFICATION",1);
            notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent notifyPendingIntent =
                    PendingIntent.getActivity(
                            this,
                            0,
                            notifyIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );
            mBuilder.setContentIntent(notifyPendingIntent);
            // Gets an instance of the NotificationManager service
            NotificationManager mNotifyMgr =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            // Builds the notification and issues it.
            mNotifyMgr.notify(GREETNG_ID, mBuilder.build());
        }

    }

}
