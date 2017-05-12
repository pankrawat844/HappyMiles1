package com.paz.happymiles.Student;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.paz.happymiles.R;

/**
 * Created by Admin on 4/4/2017.
 */

public class Meal_Pref extends Activity {
    TextView meal;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meal_pref);
        init();
        SharedPreferences sp= getSharedPreferences("pref",0);
        meal.setText(sp.getString("meal",""));
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private  void init(){
        meal=(TextView)findViewById(R.id.meal);
        btn=(Button)findViewById(R.id.btn);
    }
}
