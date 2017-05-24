package com.paz.happymiles;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.paz.happymiles.Connection.Connectivity;
import com.paz.happymiles.Session.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import custom_font.MyTextView;
import mehdi.sakout.fancybuttons.FancyButton;

public class MainActivity extends AppCompatActivity {
    Button login;
    MyTextView signup,forget_password;
    EditText email,password;
    //    CallbackManager mFacebookCallbackManager;
//    LoginManager mLoginManager;
    FancyButton FacebookLogin;
    TextView mUserNameTextView;
    StringRequest stringRequest;
    // AccessTokenTracker mAccessTokenTracker;
    RequestQueue requestQueue;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.main_activity);
         requestQueue= Volley.newRequestQueue(this);
            sharedPreferences= getSharedPreferences("pref",MODE_PRIVATE);
        if(sharedPreferences.getBoolean("islogin",false)){
            Intent in = new Intent(getApplicationContext(), Home_Activity.class);
            startActivity(in);
            finish();
        }
        init();
        //setupFacebookStuff();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Connectivity connectivity= new Connectivity();
                if(connectivity.isNetworkAvailable(MainActivity.this)) {
                    final SweetAlertDialog dialog = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                    dialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    dialog.setTitleText("Loading...");
                    dialog.setCancelable(false);
                    dialog.show();
                     stringRequest= new StringRequest(Request.Method.POST, ConstantUrls.login, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                dialog.dismiss();
                                JSONObject jsonObject = new JSONObject(response);
                                if(jsonObject.getString("msg").equals("Login Successfully!")){
                                    Toast.makeText(MainActivity.this,"Login Successfully!",3).show();
                                    SharedPreferences.Editor editor= sharedPreferences.edit();
                                    editor.putBoolean("islogin",true);
                                    editor.putString("first_name",jsonObject.getString("first_name"));
                                    editor.putString("middle_name",jsonObject.getString("middle_name"));
                                    editor.putString("last_name",jsonObject.getString("surname"));
                                    editor.putString("gender",jsonObject.getString("gender"));
                                    editor.putString("rollno",jsonObject.getString("rollno"));
                                    editor.putString("email",jsonObject.getString("email"));
                                    editor.putString("mobile_no",jsonObject.getString("mobile_no"));
                                    editor.putString("dob",jsonObject.getString("dob"));
                                    editor.putString("passport_number",jsonObject.getString("passport_number"));
                                    editor.putString("expiry_date",jsonObject.getString("expiry_date"));
                                    editor.putString("guardian_name",jsonObject.getString("guardian_name"));
                                    editor.putString("guardian_number",jsonObject.getString("guardian_number"));
                                    editor.putString("allergies",jsonObject.getString("allergies"));
                                    editor.putString("medical_req",jsonObject.getString("medical_req"));
                                    editor.putString("profile_img",jsonObject.getString("profile_image"));
                                    editor.putString("nationality",jsonObject.getString("nationality"));
                                    editor.commit();

                                    Intent in = new Intent(getApplicationContext(), Home_Activity.class);
                                startActivity(in);
                                    finish();
                                }else{
                                    Toast.makeText(MainActivity.this,"Wrong Login Credentials.Please Enter Correct Details.",3).show();

                                }

                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            dialog.dismiss();
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String,String> param= new HashMap<String, String>();
                            param.put("email",email.getText().toString());
                            param.put("password",password.getText().toString());
                            return param;
                        }
                    };
                    requestQueue.add(stringRequest);
                }else{
                    Snackbar.make(getCurrentFocus(),"Internet is not connected,Please Try Again",Snackbar.LENGTH_LONG).show();

                }

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent singup1= new Intent(getApplicationContext(),Signup.class);
                startActivity(singup1);
            }
        });


//        FacebookLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (AccessToken.getCurrentAccessToken() != null)
//                    mLoginManager.logOut();
//
//                handleFacebookLogin();
//            }
//        });

        forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this,Forget_Password.class);
                startActivity(in);
            }
        });
    }



    private void init(){
        login =(Button)findViewById(R.id.next);
        signup=(MyTextView)findViewById(R.id.signup);
        FacebookLogin=(FancyButton)findViewById(R.id.login_button);
        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        forget_password=(MyTextView) findViewById(R.id.forget);

    }


//    private void setupFacebookStuff() {
//
//        // This should normally be on your application class
//
//        mAccessTokenTracker = new AccessTokenTracker() {
//            @Override
//            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
//                //updateFacebookButtonUI();
//            }
//        };
//
//        mLoginManager = LoginManager.getInstance();
//        mFacebookCallbackManager = CallbackManager.Factory.create();
//
//        LoginManager.getInstance().registerCallback(mFacebookCallbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//
//                //updateFacebookButtonUI();
//
//                GraphRequest request = GraphRequest.newMeRequest(
//                        loginResult.getAccessToken(),
//                        new GraphRequest.GraphJSONObjectCallback() {
//                            @Override
//                            public void onCompleted(JSONObject object, GraphResponse response) {
//
//                                    Log.i("information",object.toString());
//                                try {
//                                    String email = object.getString("email");
//                                    String birthday = object.getString("birthday");
//                                    String id = object.getString("id");
//                                    String name = object.getString("name");
//                                   // tv_profile_name.setText(name);
//
//
//                                    String imageurl = "https://graph.facebook.com/" + id + "/picture?type=large";
//
//                                  //  Picasso.with(MainActivity.this).load(imageurl).into(iv_profile_pic);
//                                 //   SessionManager sessionManager= new SessionManager(this);
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//
//                            }
//
//                        });
//
//
//                Bundle parameters = new Bundle();
//                parameters.putString("fields", "id,name,email,gender, birthday");
//                request.setParameters(parameters);
//                request.executeAsync();
//
//
///**
// * AccessTokenTracker to manage logout
// */
//                mAccessTokenTracker = new AccessTokenTracker() {
//                    @Override
//                    protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
//                        if (currentAccessToken == null) {
////                            tv_profile_name.setText("");
////                            iv_profile_pic.setImageResource(R.drawable.maleicon);
//                        }
//                    }
//                };
//            }
//
//            @Override
//            public void onCancel() {
//
//            }
//
//            @Override
//            public void onError(FacebookException error) {
//
//            }
//        });
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        mFacebookCallbackManager.onActivityResult(requestCode, resultCode, data);
//    }

    @Override
    protected void onStop() {
        super.onStop();
        // mAccessTokenTracker.stopTracking();
      //  profileTracker.stopTracking();
    }

//    private void handleFacebookLogin() {
//        if (AccessToken.getCurrentAccessToken() != null){
//           mLoginManager.logOut();
//        }else{
//            mAccessTokenTracker.startTracking();
//            mLoginManager.logInWithReadPermissions(MainActivity.this, Arrays.asList("public_profile,email, user_birthday, user_friends"));
//        }
//
//    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
