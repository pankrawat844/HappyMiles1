package com.paz.happymiles;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import custom_font.MyTextView;
import mehdi.sakout.fancybuttons.FancyButton;

public class MainActivity extends AppCompatActivity {
    Button login;
    MyTextView signup;
    CallbackManager mFacebookCallbackManager;
    LoginManager mLoginManager;
    FancyButton FacebookLogin;
    TextView mUserNameTextView;
    AccessTokenTracker mAccessTokenTracker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

       init();
        setupFacebookStuff();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(),Home_Activity.class);
                startActivity(in);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent singup1= new Intent(getApplicationContext(),Signup.class);
                startActivity(singup1);
            }
        });



        FacebookLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleFacebookLogin();
            }
        });
    }



    private void init(){
        login =(Button)findViewById(R.id.next);
        signup=(MyTextView)findViewById(R.id.signup);
        FacebookLogin=(FancyButton)findViewById(R.id.login_button);
    }



    private void setupFacebookStuff() {

        // This should normally be on your application class
        FacebookSdk.sdkInitialize(getApplicationContext());

        mAccessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                //updateFacebookButtonUI();
            }
        };

        mLoginManager = LoginManager.getInstance();
        mFacebookCallbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(mFacebookCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                //updateFacebookButtonUI();

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {

                                    Log.i("information",object.toString());
                                try {
                                    String email = object.getString("email");
                                    String birthday = object.getString("birthday");
                                    String id = object.getString("id");
                                    String name = object.getString("name");
                                   // tv_profile_name.setText(name);


                                    String imageurl = "https://graph.facebook.com/" + id + "/picture?type=large";

                                  //  Picasso.with(MainActivity.this).load(imageurl).into(iv_profile_pic);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });


                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender, birthday");
                request.setParameters(parameters);
                request.executeAsync();


/**
 * AccessTokenTracker to manage logout
 */
                mAccessTokenTracker = new AccessTokenTracker() {
                    @Override
                    protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken,
                                                               AccessToken currentAccessToken) {
                        if (currentAccessToken == null) {
//                            tv_profile_name.setText("");
//                            iv_profile_pic.setImageResource(R.drawable.maleicon);
                        }
                    }
                };
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mFacebookCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAccessTokenTracker.stopTracking();
      //  profileTracker.stopTracking();
    }

    private void handleFacebookLogin() {
        if (AccessToken.getCurrentAccessToken() != null){
           // mLoginManager.logOut();
        }else{
            mAccessTokenTracker.startTracking();
            mLoginManager.logInWithReadPermissions(MainActivity.this, Arrays.asList("public_profile,email, user_birthday, user_friends"));
        }

    }


    public static void printHashKey(Context pContext) {

    }
}
