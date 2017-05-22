package com.paz.happymiles.FCM;

import android.content.Intent;
import android.util.Log;

//import com.google.firebase.messaging.FirebaseMessagingService;
//import com.google.firebase.messaging.RemoteMessage;
import com.paz.happymiles.Splash;

import org.json.JSONObject;

/**
 * Created by Admin on 4/18/2017.
 */

//public class FirebaseMessaing extends FirebaseMessagingService {
//    String TAG="Firebase Messaing";
//    @Override
//    public void onMessageReceived(RemoteMessage remoteMessage) {
//        if(remoteMessage.getData().size()>0){
//            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());
//            try{
//                JSONObject jsonObject= new JSONObject(remoteMessage.getData().toString());
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }
//    }
//
//
//    private void sendnotification(JSONObject notification){
//        try {
//            Log.e(TAG, "Notification JSON " + notification.toString());
//            JSONObject object = notification.getJSONObject("data");
//            String title= object.getString("title");
//            String msg= object.getString("message");
//            String img_url=object.getString("image");
//            Intent intent = new Intent(getApplicationContext(), Splash.class);
//            MyNotification myNotification= new MyNotification(this);
//            if(img_url.equals("null")){
//              myNotification.sowSmallNotification(title,msg,intent);
//            }else{
//                myNotification.showBigNotification(title,msg,img_url,intent);
//
//            }
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//    }
//}
