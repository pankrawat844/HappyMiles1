package com.paz.happymiles.Connection;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Admin on 3/1/2017.
 */

public class Connectivity {

    public boolean isNetworkAvailable(Activity activity){
        ConnectivityManager connectivityManager=(ConnectivityManager)activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager==null){
            return false;
        }else{
            NetworkInfo[] networkInfos=connectivityManager.getAllNetworkInfo();
            if(networkInfos!=null){
                for(int i=0;i<networkInfos.length;i++){
                    if(networkInfos[i].getState()==NetworkInfo.State.CONNECTED){
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
