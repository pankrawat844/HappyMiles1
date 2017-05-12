package com.paz.happymiles.Student;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.paz.happymiles.Adapter.Home_Adapter;
import com.paz.happymiles.Adapter.Tour_Details_Adapter;
import com.paz.happymiles.R;
import com.paz.happymiles.Student_Pojo.About_tour_pojo;
import com.paz.happymiles.Student_Pojo.Tour_Details_Pojo;

import java.util.ArrayList;
import java.util.List;

import custom_font.MyTextView;

/**
 * Created by Admin on 2/27/2017.
 */

public class Tour_Details extends Fragment {
    List<Tour_Details_Pojo> list= new ArrayList<>();
    Tour_Details_Adapter tour_details_adapter;
    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tour,container,false);
        init(view);

        recyclerView.addOnItemTouchListener(new Tour_Details_Adapter(getActivity(), new Tour_Details_Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                Dialog dialog = new Dialog(getActivity());
//                dialog.setContentView(R.layout.hotel_dialog);
                MyTextView transport= (MyTextView)view.findViewById(R.id.transport);
//                MyTextView hotel=(MyTextView)dialog.findViewById(R.id.heading);
//                hotel.setText(transport.getText().toString());
                if(position==0) {
                    Intent in = new Intent(getActivity(), Hotel_Detail.class);
                    startActivity(in);
                }
                else if(position==1) {
                    Intent in = new Intent(getActivity(), Train_Detail.class);
                    startActivity(in);
                }
                else if(position==2) {
                    Intent in = new Intent(getActivity(), Flight_Details.class);
                    startActivity(in);
                }
                else if(position==3) {
                    Intent in = new Intent(getActivity(), Bus_Details.class);
                    startActivity(in);
                }
               // dialog.show();
            }
        }));
        return view;
    }

    private void init(View view){

        recyclerView= (RecyclerView)view.findViewById(R.id.recycleview);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        tour_details_adapter = new Tour_Details_Adapter(list,getActivity());
        recyclerView.setAdapter(tour_details_adapter);
        static_data();
    }


    private void static_data(){
        Tour_Details_Pojo pojo= new Tour_Details_Pojo("Hotel Details",R.drawable.t1);
        list.add(pojo);
        Tour_Details_Pojo pojo1= new Tour_Details_Pojo("Trains Details",R.drawable.t2);
        list.add(pojo1);
        Tour_Details_Pojo pojo2= new Tour_Details_Pojo("Flight Details",R.drawable.t3);
        list.add(pojo2);
        Tour_Details_Pojo pojo3= new Tour_Details_Pojo("Bus Details",R.drawable.t4);
        list.add(pojo3);
        Tour_Details_Pojo pojo4= new Tour_Details_Pojo("Tour Directors",R.drawable.t5);
        list.add(pojo4);
        tour_details_adapter.notifyDataSetChanged();
    }
}
