package com.paz.happymiles;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import java.lang.reflect.Array;

public class Transport_Detail extends AppCompatActivity {
        com.rey.material.widget.Spinner spinner;
    String[] arr={"Flight","Train","Bus"};
    RelativeLayout train_layout,bus_layout,flight_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transport__detail);
        spinner=(com.rey.material.widget.Spinner) findViewById(R.id.spinner);
        flight_layout=(RelativeLayout)findViewById(R.id.flight_layout);
        train_layout=(RelativeLayout)findViewById(R.id.train_layout);
        bus_layout=(RelativeLayout)findViewById(R.id.bus_layout);
        train_layout.setVisibility(View.GONE);
        bus_layout.setVisibility(View.GONE);
        flight_layout.setVisibility(View.VISIBLE);
        ArrayAdapter<String> arrayAdapter= new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,arr);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new com.rey.material.widget.Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(com.rey.material.widget.Spinner parent, View view, int position, long id) {
                if(position==0){
                    train_layout.setVisibility(View.GONE);
                    bus_layout.setVisibility(View.GONE);
                    flight_layout.setVisibility(View.VISIBLE);

                }else if(position==1){
                    train_layout.setVisibility(View.VISIBLE);
                    bus_layout.setVisibility(View.GONE);
                    flight_layout.setVisibility(View.GONE);
                }else if(position==2){
                    train_layout.setVisibility(View.GONE);
                    bus_layout.setVisibility(View.VISIBLE);
                    flight_layout.setVisibility(View.GONE);
                }
            }
        });
    }
}
