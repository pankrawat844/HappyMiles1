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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.eminayar.panter.DialogType;
import com.eminayar.panter.PanterDialog;
import com.eminayar.panter.interfaces.OnTextInputConfirmListener;
import com.paz.happymiles.Connection.Connectivity;
import com.paz.happymiles.Session.SessionManager;
import com.paz.happymiles.Student.About_Tour;
import com.paz.happymiles.Student.Payment;
import com.paz.happymiles.Student.Profile;
import com.paz.happymiles.Student.Tour_Details;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Home_Activity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {
    TabLayout tabLayout;
    ViewPager viewPager;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        tabLayout = (TabLayout) findViewById(R.id.tab);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        sharedPreferences = getSharedPreferences("pref", MODE_PRIVATE);

        if (sharedPreferences.getBoolean("finish", false)) {
            SharedPreferences.Editor editor= sharedPreferences.edit();
            editor.putBoolean("finish",false);
            editor.commit();
            finish();
        } else {
            if (!sharedPreferences.getBoolean("tour_code_enter", false)) {

                call_dialog();
            } else {
                tabLayout.addTab(tabLayout.newTab().setText("About Tour").setIcon(R.mipmap.abt1));
                tabLayout.addTab(tabLayout.newTab().setText("Tour Details").setIcon(R.mipmap.tour1));
                tabLayout.addTab(tabLayout.newTab().setText("Payment").setIcon(R.mipmap.payment1));
                tabLayout.addTab(tabLayout.newTab().setText("My Profile").setIcon(R.mipmap.profile1));


                Home_TabAdapter tabAdapter = new Home_TabAdapter(getSupportFragmentManager(), 4);
                viewPager.setAdapter(tabAdapter);
                // tabLayout.setupWithViewPager(viewPager);
                tabLayout.setOnTabSelectedListener(Home_Activity.this);
                tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(Home_Activity.this, R.color.appthemecolor));

                viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

            }


        }
    }
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
        tabLayout.getTabAt(tab.getPosition()).select();
        if (tab.getPosition() == 0) {

            tabLayout.getTabAt(tab.getPosition()).setIcon(R.mipmap.abt2);
            tabLayout.getTabAt(1).setIcon(R.mipmap.tour1);
            tabLayout.getTabAt(2).setIcon(R.mipmap.payment1);
            tabLayout.getTabAt(3).setIcon(R.mipmap.profile1);
        }
        if (tab.getPosition() == 1) {
            tabLayout.getTabAt(tab.getPosition()).setIcon(R.mipmap.tour2);
            tabLayout.getTabAt(0).setIcon(R.mipmap.abt1);
            tabLayout.getTabAt(2).setIcon(R.mipmap.payment1);
            tabLayout.getTabAt(3).setIcon(R.mipmap.profile1);
        }
        if (tab.getPosition() == 2) {
            tabLayout.getTabAt(tab.getPosition()).setIcon(R.mipmap.payment2);
            tabLayout.getTabAt(0).setIcon(R.mipmap.abt1);
            tabLayout.getTabAt(1).setIcon(R.mipmap.tour1);
            tabLayout.getTabAt(3).setIcon(R.mipmap.profile1);

        }
        if (tab.getPosition() == 3) {
            tabLayout.getTabAt(tab.getPosition()).setIcon(R.mipmap.profile2);
            tabLayout.getTabAt(0).setIcon(R.mipmap.abt1);
            tabLayout.getTabAt(1).setIcon(R.mipmap.tour1);
            tabLayout.getTabAt(2).setIcon(R.mipmap.payment1);

        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }


    private class Home_TabAdapter extends FragmentStatePagerAdapter {
        List<String> tabs;
        int tabcount;

        public Home_TabAdapter(FragmentManager fm, int tabcount) {

            super(fm);
            this.tabcount = tabcount;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    About_Tour about_tour = new About_Tour();
                    return about_tour;

                case 1:
                    Tour_Details tour_details = new Tour_Details();
                    return tour_details;
                case 2:
                    Payment payment = new Payment();
                    return payment;
                case 3:
                    Profile profile = new Profile();
                    return profile;
                default:
                    About_Tour tour = new About_Tour();
                    return tour;
            }
        }


        @Override
        public int getCount() {
            return tabcount;
        }

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
                                    tabLayout.addTab(tabLayout.newTab().setText("About Tour").setIcon(R.mipmap.abt1));
                                    tabLayout.addTab(tabLayout.newTab().setText("Tour Details").setIcon(R.mipmap.tour1));
                                    tabLayout.addTab(tabLayout.newTab().setText("Payment").setIcon(R.mipmap.payment1));
                                    tabLayout.addTab(tabLayout.newTab().setText("My Profile").setIcon(R.mipmap.profile1));
                                    dialo.dismiss();
                                    dialog.dismiss();

                                    Home_TabAdapter tabAdapter = new Home_TabAdapter(getSupportFragmentManager(), 4);
                                    viewPager.setAdapter(tabAdapter);
                                    // tabLayout.setupWithViewPager(viewPager);
                                    tabLayout.setOnTabSelectedListener(Home_Activity.this);
                                    tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(Home_Activity.this, R.color.appthemecolor));

                                    viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

                                } else {
                                    dialo.dismiss();
                                    Toast.makeText(Home_Activity.this, "Wrong Tour Code.Please Enter Correct Details.", 3).show();
                                    exit.setVisibility(View.VISIBLE);
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