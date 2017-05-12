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

public class Profile_Detail extends Activity {
    SharedPreferences sp;
    TextView name,dob,email,natinality,phno,sex;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_detail);
        init();
        sp= getSharedPreferences("pref", MODE_PRIVATE);
        name.setText(sp.getString("first_name",""));
        dob.setText(sp.getString("dob",""));
        email.setText(sp.getString("email",""));
        natinality.setText(sp.getString("nationality",""));
        phno.setText(sp.getString("mobile_no",""));
        sex.setText(sp.getString("gender",""));
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private  void init(){
        name=(TextView)findViewById(R.id.name);
        dob=(TextView)findViewById(R.id.dob);
        email=(TextView)findViewById(R.id.email);
        natinality=(TextView)findViewById(R.id.naionality);
        phno=(TextView)findViewById(R.id.phno);
        sex=(TextView)findViewById(R.id.gender);
        btn=(Button)findViewById(R.id.btn);
    }
}
