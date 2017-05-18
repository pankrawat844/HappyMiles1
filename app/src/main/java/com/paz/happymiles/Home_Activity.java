package com.paz.happymiles;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import com.paz.happymiles.Student.About_Tour;
import com.paz.happymiles.Student.Hotel_Detail;
import com.paz.happymiles.Student.Payment;
import com.paz.happymiles.Student.Profile;
import com.paz.happymiles.Student.Tour_Details;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Home_Activity extends AppCompatActivity {
    RelativeLayout personal_relative,daily_relative,payment,tour_codinator,hotel_info,transpor_detail;
    ImageView daily_internity_img;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        sharedPreferences= getSharedPreferences("pref",0);
        personal_relative=(RelativeLayout)findViewById(R.id.personal_relative);
        daily_relative=(RelativeLayout) findViewById(R.id.daily_relative);
        payment=(RelativeLayout)findViewById(R.id.payment_img);
        hotel_info=(RelativeLayout)findViewById(R.id.hotel_rel);
        tour_codinator=(RelativeLayout)findViewById(R.id.tour_cordinator_img);
        transpor_detail=(RelativeLayout)findViewById(R.id.transport);
        daily_relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in= new Intent(Home_Activity.this,About_Tour.class);
                startActivity(in);
            }
        });
        if (!sharedPreferences.getBoolean("tour_code_enter", false)) {

            call_dialog();

        }
            payment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent= new Intent(Home_Activity.this,Payment.class);
                    startActivity(intent);
                }
            });
        personal_relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Home_Activity.this,Profile.class);
                startActivity(intent);
            }
        });


        hotel_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Home_Activity.this, Hotel_Detail.class);
                startActivity(in);
            }
        });

        transpor_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Home_Activity.this, Transport_Detail.class);
                startActivity(in);
            }
        });


        tour_codinator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Home_Activity.this, Tour_Cordinator.class);
                startActivity(in);
            }
        });
    }


    private void call_dialog() {
       final Dialog dialog = new Dialog(Home_Activity.this, R.style.CustomDialog2);
        dialog.setContentView(R.layout.dialog_tour_code);
        dialog.setCancelable(false);
        final EditText tour_code = (EditText) dialog.findViewById(R.id.edittext);
        final Button exit = (Button) dialog.findViewById(R.id.exit);
        Button submit = (Button) dialog.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue requestQueue = Volley.newRequestQueue(Home_Activity.this);
                Connectivity connectivity = new Connectivity();


                if (connectivity.isNetworkAvailable(Home_Activity.this)) {
                    final SweetAlertDialog dialo = new SweetAlertDialog(Home_Activity.this, SweetAlertDialog.PROGRESS_TYPE);
                    dialo.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    dialo.setTitleText("Loading");
                    dialo.setCancelable(false);
                    dialo.show();
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, ConstantUrls.check_status, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {

                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.getString("enrol_status").equals("Yes")) {
                                    Toast.makeText(Home_Activity.this, response, 3).show();
                                    Log.e("response", response);

                                    SharedPreferences.Editor editor= sharedPreferences.edit();
                                    editor.putString("tour_code",tour_code.getText().toString());
                                    editor.putBoolean("tour_code_enter",true);
                                    editor.commit();
                                    dialo.dismiss();
                                    dialog.dismiss();


                                } else {
                                    dialo.dismiss();
                                    Toast.makeText(Home_Activity.this, "Wrong Tour Code.Please Enter Correct Details.", 3).show();
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            dialo.dismiss();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> param = new HashMap<String, String>();
                            param.put("email", sharedPreferences.getString("email",""));
                            param.put("tour_code", tour_code.getText().toString());
                            return param;
                        }
                    };
                    requestQueue.add(stringRequest);
                } else {
                    Snackbar.make(getCurrentFocus(), "Internet is not connected,Please Try Again", Snackbar.LENGTH_LONG).show();

                }
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              onBackPressed();
                SharedPreferences.Editor editor= sharedPreferences.edit();
                editor.clear();
                editor.commit();
            }
        });

        dialog.show();



    }


    @Override
    public void onBackPressed() {

            super.onBackPressed();
            //additional code

    }
}