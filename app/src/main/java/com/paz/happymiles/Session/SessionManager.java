package com.paz.happymiles.Session;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Admin on 3/1/2017.
 */

public class SessionManager {
    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static final String PREF_NAME = "pref";
    private static final String IS_LOGIN = "IsLoggedIn";


    public  SessionManager(Context context){
        this.context=context;
        this.sharedPreferences=this.context.getSharedPreferences(PREF_NAME,0);
        this.editor=this.sharedPreferences.edit();
        this.editor.commit();
    }

    public void createLoginSession(String Id,String tour_code) {
        this.editor.putBoolean(IS_LOGIN, true);
        this.editor.putString("roll_no", Id);
        this.editor.putString("tour_code",tour_code);
        this.editor.commit();
    }


}
