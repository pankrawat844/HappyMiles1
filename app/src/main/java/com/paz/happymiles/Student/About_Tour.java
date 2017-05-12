package com.paz.happymiles.Student;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kennyc.bottomsheet.BottomSheet;
import com.paz.happymiles.Adapter.FoldingCellListAdapter;
import com.paz.happymiles.Adapter.Home_Adapter;
import com.paz.happymiles.Connection.Connectivity;
import com.paz.happymiles.ConstantUrls;
import com.paz.happymiles.Home_Activity;
import com.paz.happymiles.MainActivity;
import com.paz.happymiles.R;
import com.paz.happymiles.Recylevie_animation.OnItemClickListener;
import com.paz.happymiles.Recylevie_animation.RecyclerInsetsDecoration;
import com.paz.happymiles.Session.SessionManager;
import com.paz.happymiles.Student_Pojo.About_tour_pojo;
import com.ramotion.foldingcell.FoldingCell;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Admin on 2/27/2017.
 */

public class About_Tour extends Fragment  {
    RecyclerView recyclerView;
    Home_Adapter home_adapter;
    List<About_tour_pojo> list= new ArrayList<>();
    int[] img={R.drawable.day1,R.drawable.day2,R.drawable.day3,R.drawable.day4,R.drawable.day5,R.drawable.day1,R.drawable.day2,R.drawable.day3,R.drawable.day4,R.drawable.day5,R.drawable.day1,R.drawable.day2,R.drawable.day3,R.drawable.day4};
    public static SparseArray<Bitmap> photoCache = new SparseArray<Bitmap>(1);
    RequestQueue requestQueue;
    SharedPreferences sharedPreferences;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.about_tour,container,false);
        recyclerView= (RecyclerView) view.findViewById(R.id.recycleview);
         requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        sharedPreferences= getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new RecyclerInsetsDecoration(getActivity()));
        home_adapter = new Home_Adapter(list,getActivity());
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
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
     //   init(view);

    }
    private OnItemClickListener recyclerRowClickListener = new OnItemClickListener() {
    @Override
    public void onClick(View v, int position) {

        About_tour_pojo selectedBook = list.get(position);

        Intent detailIntent = new Intent(getActivity(), Days_Details.class);
        detailIntent.putExtra("position", position);
        detailIntent.putExtra("selected_book", selectedBook);

        ImageView coverImage = (ImageView) v.findViewById(R.id.bg_img);
        ((ViewGroup) coverImage.getParent()).setTransitionGroup(false);
        photoCache.put(position,  ((BitmapDrawable)coverImage.getDrawable()).getBitmap());

        // Setup the transition to the detail activity
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(),
                new Pair<View, String>(coverImage, "cover" + position));

        startActivity(detailIntent, options.toBundle());
    }
};
//    private void init(View view){
//
//         recyclerView= (RecyclerView)view.findViewById(R.id.recycleview);
//        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
//        recyclerView.setLayoutManager(layoutManager);
//        home_adapter = new Home_Adapter(list,getActivity());
//        recyclerView.setAdapter(home_adapter);
//        static_data();
//    }

//    private void static_data(){
//    About_tour_pojo pojo= new About_tour_pojo("Day-1",R.drawable.day1,"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc mollis nunc massa, quis suscipit orci mattis a. Nulla rutrum fringilla finibus. Morbi ac dui in lacus finibus hendrerit. Ut vestibulum. Nulla rutrum fringilla finibus. Morbi ac dui in lacus finibus hendrerit. Ut vestibulum. Nulla rutrum fringilla finibus. Morbi ac dui in lacus finibus hendrerit. Ut vestibulum, Nulla rutrum fringilla finibus. Morbi ac dui in lacus finibus hendrerit. Ut vestibulum, Morbi ac dui in lacus finibus hendrerit. Ut vestibulum, Nulla rutrum fringilla finibus. Morbi ac dui in lacus finibus hendrerit. Ut vestibulum, Ut vestibulum, Nulla rutrum fringilla finibus. Morbi ac dui in lacus finibus hendrerit. Ut vestibulum, Morbi ac dui in lacus finibus hendrerit. Ut vestibulum, Nulla rutrum fringilla finibus. Morbi ac dui in lacus finibus hendrerit. Ut vestibulum");
//        list.add(pojo);
//        About_tour_pojo pojo1= new About_tour_pojo("Day-2",R.drawable.day2,"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc mollis nunc massa, quis suscipit orci mattis a. Nulla rutrum fringilla finibus. Morbi ac dui in lacus finibus hendrerit. Ut vestibulum. Nulla rutrum fringilla finibus. Morbi ac dui in lacus finibus hendrerit. Ut vestibulum. Nulla rutrum fringilla finibus. Morbi ac dui in lacus finibus hendrerit. Ut vestibulum, Nulla rutrum fringilla finibus. Morbi ac dui in lacus finibus hendrerit. Ut vestibulum, Morbi ac dui in lacus finibus hendrerit. Ut vestibulum, Nulla rutrum fringilla finibus. Morbi ac dui in lacus finibus hendrerit. Ut vestibulum, Ut vestibulum, Nulla rutrum fringilla finibus. Morbi ac dui in lacus finibus hendrerit. Ut vestibulum, Morbi ac dui in lacus finibus hendrerit. Ut vestibulum, Nulla rutrum fringilla finibus. Morbi ac dui in lacus finibus hendrerit. Ut vestibulum");
//        list.add(pojo1);
//        About_tour_pojo pojo2= new About_tour_pojo("Day-3",R.drawable.day3,"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc mollis nunc massa, quis suscipit orci mattis a. Nulla rutrum fringilla finibus. Morbi ac dui in lacus finibus hendrerit. Ut vestibulum. Nulla rutrum fringilla finibus. Morbi ac dui in lacus finibus hendrerit. Ut vestibulum. Nulla rutrum fringilla finibus. Morbi ac dui in lacus finibus hendrerit. Ut vestibulum, Nulla rutrum fringilla finibus. Morbi ac dui in lacus finibus hendrerit. Ut vestibulum, Morbi ac dui in lacus finibus hendrerit. Ut vestibulum, Nulla rutrum fringilla finibus. Morbi ac dui in lacus finibus hendrerit. Ut vestibulum, Ut vestibulum, Nulla rutrum fringilla finibus. Morbi ac dui in lacus finibus hendrerit. Ut vestibulum, Morbi ac dui in lacus finibus hendrerit. Ut vestibulum, Nulla rutrum fringilla finibus. Morbi ac dui in lacus finibus hendrerit. Ut vestibulum");
//        list.add(pojo2);
//        About_tour_pojo pojo3= new About_tour_pojo("Day-4",R.drawable.day4,"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc mollis nunc massa, quis suscipit orci mattis a. Nulla rutrum fringilla finibus. Morbi ac dui in lacus finibus hendrerit. Ut vestibulum. Nulla rutrum fringilla finibus. Morbi ac dui in lacus finibus hendrerit. Ut vestibulum. Nulla rutrum fringilla finibus. Morbi ac dui in lacus finibus hendrerit. Ut vestibulum, Nulla rutrum fringilla finibus. Morbi ac dui in lacus finibus hendrerit. Ut vestibulum, Morbi ac dui in lacus finibus hendrerit. Ut vestibulum, Nulla rutrum fringilla finibus. Morbi ac dui in lacus finibus hendrerit. Ut vestibulum, Ut vestibulum, Nulla rutrum fringilla finibus. Morbi ac dui in lacus finibus hendrerit. Ut vestibulum, Morbi ac dui in lacus finibus hendrerit. Ut vestibulum, Nulla rutrum fringilla finibus. Morbi ac dui in lacus finibus hendrerit. Ut vestibulum");
//        list.add(pojo3);
//        About_tour_pojo pojo4= new About_tour_pojo("Day-5",R.drawable.day5,"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc mollis nunc massa, quis suscipit orci mattis a. Nulla rutrum fringilla finibus. Morbi ac dui in lacus finibus hendrerit. Ut vestibulum. Nulla rutrum fringilla finibus. Morbi ac dui in lacus finibus hendrerit. Ut vestibulum. Nulla rutrum fringilla finibus. Morbi ac dui in lacus finibus hendrerit. Ut vestibulum, Nulla rutrum fringilla finibus. Morbi ac dui in lacus finibus hendrerit. Ut vestibulum, Morbi ac dui in lacus finibus hendrerit. Ut vestibulum, Nulla rutrum fringilla finibus. Morbi ac dui in lacus finibus hendrerit. Ut vestibulum, Ut vestibulum, Nulla rutrum fringilla finibus. Morbi ac dui in lacus finibus hendrerit. Ut vestibulum, Morbi ac dui in lacus finibus hendrerit. Ut vestibulum, Nulla rutrum fringilla finibus. Morbi ac dui in lacus finibus hendrerit. Ut vestibulum");
//        list.add(pojo4);
//        home_adapter.notifyDataSetChanged();
//    }

    private void iternity_data(){
        Connectivity connectivity= new Connectivity();
        if(connectivity.isNetworkAvailable(getActivity())) {
            SessionManager sessionManager= new SessionManager(getActivity());

            final SweetAlertDialog dialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
            dialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            dialog.setTitleText("Loading");
            dialog.setCancelable(false);
            dialog.show();
            JsonArrayRequest stringRequest = new JsonArrayRequest( ConstantUrls.get_iternity+"?tour_code="+sharedPreferences.getString("tour_code","0"), new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                   //     Toast.makeText(getActivity(),response.toString(),3).show();
                    Log.e("result",response.toString());
                    try {
                        dialog.dismiss();
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject jsonObject = response.getJSONObject(i);
                            About_tour_pojo pojo = new About_tour_pojo(jsonObject.getString("day"),img[i], jsonObject.getString("descrptn"));
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
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> param = new HashMap<String, String>();
                    param.put("tour_code", sharedPreferences.getString("tour_code","0"));
                    return param;
                }
            };

            requestQueue.add(stringRequest);
        }else
        {
            Toast.makeText(getActivity(),"Internet is not connected,Please Try Again",Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onResume() {
        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    // handle back button's click listener
                    Toast.makeText(getActivity(), "Back press", Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                    SharedPreferences.Editor editor= sharedPreferences.edit();
                    editor.putBoolean("finish",true);
                    editor.commit();
                    return true;
                }
                return false;
            }
        });
    }
}
