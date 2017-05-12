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

public class Medical_Req extends Activity {
    Button btn;
    TextView medical_req,allergies;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medical_req);

        init();
        SharedPreferences sp= getSharedPreferences("pref",0);
       allergies.setText(sp.getString("allergies",""));
        medical_req.setText(sp.getString("medical_req",""));
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void init(){
            medical_req=(TextView)findViewById(R.id.medical);
            allergies=(TextView)findViewById(R.id.allergies);
        btn=(Button)findViewById(R.id.btn);
    }
}
