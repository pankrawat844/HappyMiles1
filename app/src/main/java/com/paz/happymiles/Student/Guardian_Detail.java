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

public class Guardian_Detail extends Activity {
    TextView guardian_name,guardian_no;
    Button btn;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guardian_detail);
        sp=getSharedPreferences("pref",0);
        guardian_name=(TextView)findViewById(R.id.guardian);
        guardian_no=(TextView)findViewById(R.id.phno);
        btn=(Button)findViewById(R.id.btn);
        guardian_name.setText(sp.getString("guardian_name",""));
        guardian_no.setText(sp.getString("guardian_number",""));
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
