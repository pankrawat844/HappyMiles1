package com.paz.happymiles.Student;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.paz.happymiles.Adapter.Home_Adapter;
import com.paz.happymiles.Adapter.Hotel_Detail_Adapter;
import com.paz.happymiles.Connection.Connectivity;
import com.paz.happymiles.ConstantUrls;
import com.paz.happymiles.Hotel_Day_Detail;
import com.paz.happymiles.R;
import com.paz.happymiles.Recylevie_animation.OnItemClickListener;
import com.paz.happymiles.Recylevie_animation.RecyclerInsetsDecoration;
import com.paz.happymiles.Student_Pojo.About_tour_pojo;
import com.paz.happymiles.Student_Pojo.Hotel_Detail_Pojo;
import com.rey.material.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Admin on 3/2/2017.
 */

public class Hotel_Detail extends Activity {

    RecyclerView recyclerView;
    Hotel_Detail_Adapter home_adapter;
    List<Hotel_Detail_Pojo> list = new ArrayList<>();
    int[] img = {R.drawable.day1, R.drawable.day2, R.drawable.day3, R.drawable.day4, R.drawable.day5, R.drawable.day1, R.drawable.day2, R.drawable.day3, R.drawable.day4, R.drawable.day5, R.drawable.day1, R.drawable.day2, R.drawable.day3, R.drawable.day4};
    public static SparseArray<Bitmap> photoCache = new SparseArray<Bitmap>(1);
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hotel_dialog);
        sharedPreferences=getSharedPreferences("pref",0);
        recyclerView= (RecyclerView) findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new RecyclerInsetsDecoration(this));
        home_adapter = new Hotel_Detail_Adapter(list, this);
        recyclerView.setAdapter(home_adapter);

        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

        //  static_data();
        iternity_data();
        home_adapter.setOnItemClickListener(recyclerRowClickListener);
    }


    private OnItemClickListener recyclerRowClickListener = new OnItemClickListener() {
        @Override
        public void onClick(View v, int position) {

            Hotel_Detail_Pojo selectedBook = list.get(position);

            Intent detailIntent = new Intent(Hotel_Detail.this, Hotel_Day_Detail.class);
            detailIntent.putExtra("position", position);
            detailIntent.putExtra("selected_book", selectedBook);

            ImageView coverImage = (ImageView) v.findViewById(R.id.bg_img);
            ((ViewGroup) coverImage.getParent()).setTransitionGroup(false);
            photoCache.put(position, ((BitmapDrawable) coverImage.getDrawable()).getBitmap());

            // Setup the transition to the detail activity
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Hotel_Detail.this,
                    new Pair<View, String>(coverImage, "cover" + position));

            startActivity(detailIntent, options.toBundle());
        }
    };
//    private void init(View view){
//
//         recyclerView= (RecyclerView)view.findViewById(R.id.recycleview);
//        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
//        recyclerView.setLayoutManager(layoutManager);
//        home_adapter = new Home_Adapter(list,this);
//        recyclerView.setAdapter(home_adapter);
//        static_data();
//    }


    private void iternity_data() {
        Connectivity connectivity = new Connectivity();
        if (connectivity.isNetworkAvailable(this)) {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            final SweetAlertDialog dialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
            dialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            dialog.setTitleText("Loading");
            dialog.setCancelable(false);
            dialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, ConstantUrls.hotel_detail, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                 //   Toast.makeText(getApplicationContext(), response.toString(), 3).show();
                    Log.e("result", response.toString());
                    try {
                        JSONArray arr= new JSONArray(response);
                        dialog.dismiss();
                        for (int i = 0; i < arr.length(); i++) {
                            JSONObject jsonObject = arr.getJSONObject(i);
                            Hotel_Detail_Pojo pojo = new Hotel_Detail_Pojo(jsonObject.getString("hotel_name"), jsonObject.getString("hotel_cntct_no"), jsonObject.getString("hotel_location"), "http://" + jsonObject.getString("hotel_img"), jsonObject.getString("day"));
                            list.add(pojo);
                        }
                        home_adapter.notifyDataSetChanged();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    dialog.dismiss();
//                    Hotel_Detail_Pojo pojo = new Hotel_Detail_Pojo("Welcome Hotel","123456789","Rz-30,Old Rajapuri,Uttam Nagar","https://www.omnihotels.com/-/media/images/hotels/ausctr/pool/ausctr-omni-austin-hotel-downtown-evening-pool.jpg?h=660&la=en&w=1170","1");
//                    list.add(pojo);
//                    home_adapter.notifyDataSetChanged();
                }
            }
            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> param = new HashMap<String, String>();
                    param.put("tour_code", sharedPreferences.getString("tour_code",""));
                    return param;
                }
            };

            requestQueue.add(stringRequest);
        } else {
            Toast.makeText(this, "Internet is not connected,Please Try Again", Toast.LENGTH_LONG).show();

        }
    }

}
