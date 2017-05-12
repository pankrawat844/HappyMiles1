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
    private static final String E_MAIL = "email";
    private static final String TOUR_CODE = "tour_code";
    private static final String Phone_no = "phone_no";
    private static final String ROLL_NO = "roll_no";
    private static final String Allergies = "allegies";
    private static final String DOB = "dob";
    private static final String GENDER = "gender";
    private static final String PASSPORT_NO = "passport_no";
    private static final String PROFILE_IMG = "profile_pic";

    public  SessionManager(Context context){
        this.context=context;
        this.sharedPreferences=this.context.getSharedPreferences(PREF_NAME,0);
        this.editor=this.sharedPreferences.edit();
        this.editor.commit();
    }

    public void createLoginSession(String Id,String tour_code,String email,String phone_no,String dob,String gender,String allergies,String profile_img) {
        this.editor.putBoolean(IS_LOGIN, true);
        this.editor.putString(ROLL_NO, Id);
        this.editor.putString(TOUR_CODE,tour_code);
        this.editor.putString(Phone_no,phone_no);
        this.editor.putString(E_MAIL,email);

        this.editor.putString(DOB,dob);
        this.editor.putString(Allergies,allergies);
        this.editor.putString(PROFILE_IMG,profile_img);
        this.editor.commit();
    }


}
