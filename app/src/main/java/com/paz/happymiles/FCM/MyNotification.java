package com.paz.happymiles.FCM;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.text.Html;

import com.paz.happymiles.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Admin on 4/19/2017.
 */

public class MyNotification {
    public static final int ID_BIG_NOTIFICATION=1;
    public static final int ID_SMALL_NOTIFICATION=2;
    private Context ctx;
    public  MyNotification(Context mctx){
        ctx=mctx;
    }

    public void showBigNotification(String title, String msg, String url, Intent intent){
        PendingIntent pendingIntent= PendingIntent.getActivity(ctx,ID_BIG_NOTIFICATION,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.BigPictureStyle bigPictureStyle= new NotificationCompat.BigPictureStyle();
        bigPictureStyle.setBigContentTitle(title);
        bigPictureStyle.setSummaryText(Html.fromHtml(msg).toString());
        bigPictureStyle.bigLargeIcon(getBitmapFromURL(url));
        NotificationCompat.Builder builder= new NotificationCompat.Builder(ctx);
        Notification notification= builder.setTicker(title).setContentTitle(title).setSmallIcon(R.drawable.logo).setWhen(0).setContentIntent(pendingIntent).setLargeIcon(BitmapFactory.decodeResource(ctx.getResources(),R.drawable.logo)).setStyle(bigPictureStyle).setAutoCancel(true).setContentText(msg).build();
        notification.flags |=Notification.FLAG_AUTO_CANCEL;
        NotificationManager notificationManager= (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(ID_BIG_NOTIFICATION,notification);
    }

        public void sowSmallNotification(String title,String msg,Intent intent){
            PendingIntent pendingIntent= PendingIntent.getActivity(ctx,ID_SMALL_NOTIFICATION,intent,PendingIntent.FLAG_CANCEL_CURRENT);
            NotificationCompat.Builder builder= new NotificationCompat.Builder(ctx);
            Notification notification=builder.setAutoCancel(true).setContentText(msg).setContentTitle(title).setTicker(title).setWhen(0).setContentIntent(pendingIntent).setSmallIcon(R.drawable.logo).setLargeIcon(BitmapFactory.decodeResource(ctx.getResources(),R.drawable.logo)).setTicker(title).build();
            NotificationManager notificationManager= (NotificationManager)ctx.getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(ID_SMALL_NOTIFICATION,notification);

        }

    private Bitmap getBitmapFromURL(String url) {
        try {

            URL url1 = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
