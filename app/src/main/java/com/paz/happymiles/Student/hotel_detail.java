package com.paz.happymiles.Student;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.paz.happymiles.R;
import com.rey.material.widget.Spinner;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Admin on 3/2/2017.
 */

public class Hotel_Detail extends Activity{
    String[] hotel= {"Advised","Confirmed","Tentive"};
    Spinner hotel_status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hotel_dialog);
//        hotel_status=(Spinner)findViewById(R.id.hotel_status);
//        ArrayAdapter<String> hotel_adapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,hotel);
//        hotel_status.setAdapter(hotel_adapter);
    }
}
