package com.paz.happymiles.Student;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.Base64;
import com.loopj.android.http.RequestParams;
import com.paz.happymiles.ConstantUrls;
import com.paz.happymiles.MainActivity;
import com.paz.happymiles.R;
import com.rey.material.widget.ImageView;
import com.rey.material.widget.Spinner;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Admin on 2/27/2017.
 */

public class Profile extends AppCompatActivity {

    Context context;
    de.hdodenhof.circleimageview.CircleImageView profile_pic;
    android.widget.ImageView passport_img;
    public Spinner spinner, spinner1, spinner2, spinner3;
    List<String> list, day, month, year;
    SharedPreferences sharedPreferences;
        static EditText passport_first_name,passpost_last_name,passpost_middle_name,meal_pref,passport_no,email,emergency_email,phone_no,emer_contact_no,dob,nationality,gardian_name,gardian_no,allergies,medication_req,blood_grp,batch,roll_no,standard,special_note;
    private static int RESULT_LOAD_IMG = 1;
    String imgPath, fileName,encodedString;
    Bitmap bitmap;
    RequestParams params= new RequestParams();
    DatePickerDialog datePickerDialog;
    SimpleDateFormat dateFormat;
    static RequestQueue reqeust;
    Button submit;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            setContentView(R.layout.profile);
         reqeust = Volley.newRequestQueue(Profile.this);
        sharedPreferences= getSharedPreferences("pref",Context.MODE_PRIVATE);
        dateFormat= new SimpleDateFormat("dd-MM-yyyy", Locale.UK);
            init();

        Picasso.with(this).load(sharedPreferences.getString("profile_img","")).error(R.drawable.user).into(profile_pic);

        profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadImagefromGallery(v);
            }
        });

        passport_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadImagefromGallery(v);
            }
        });

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            datePickerDialog.show();
            }
        });
        Calendar newcal=Calendar.getInstance();
         datePickerDialog= new DatePickerDialog(Profile.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

            Calendar calendar= Calendar.getInstance();
                calendar.set(year,month,dayOfMonth);
                dob.setText(dateFormat.format(calendar.getTime()));
            }
        },newcal.get(Calendar.YEAR),newcal.get(Calendar.MONTH),newcal.get(Calendar.DAY_OF_MONTH));

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (passport_first_name.getText().toString().equals("") || passpost_middle_name.getText().toString().equals("") || passpost_last_name.getText().toString().equals("") || passport_no.getText().toString().equals("") || passpost_last_name.getText().toString().equals("") || meal_pref.getText().toString().equals("") || email.getText().toString().equals("") || emer_contact_no.getText().toString().equals("") || dob.getText().toString().equals("")
                        || nationality.getText().toString().equals("") || gardian_name.getText().toString().equals("") || gardian_no.getText().toString().equals("") || allergies.getText().toString().equals("") || medication_req.getText().toString().equals("") || blood_grp.getText().toString().equals("") || batch.getText().toString().equals("") || roll_no.getText().toString().equals("") || standard.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "All Field Are Mandatory", Toast.LENGTH_LONG).show();
                } else {
                    upate_data();
                }
            }
        });
    }


    private void init(){

        profile_pic=(CircleImageView)findViewById(R.id.logo);
        passport_first_name=(EditText)findViewById(R.id.name);
        passpost_last_name=(EditText)findViewById(R.id.last_name);
        passpost_middle_name=(EditText)findViewById(R.id.middle_name);
        passpost_last_name=(EditText)findViewById(R.id.last_name);
        passport_no=(EditText)findViewById(R.id.passport);
        passport_img=(android.widget.ImageView)findViewById(R.id.passort_img);
        email=(EditText)findViewById(R.id.email);
        emergency_email=(EditText)findViewById(R.id.emergency_email);
        phone_no=(EditText)findViewById(R.id.phno);
        emer_contact_no=(EditText)findViewById(R.id.emergency_phno);
        dob=(EditText)findViewById(R.id.dob);
        nationality=(EditText)findViewById(R.id.naionality);
        gardian_name=(EditText)findViewById(R.id.gardian);
        gardian_no=(EditText)findViewById(R.id.gardian_no);
        allergies=(EditText)findViewById(R.id.allergies);
        medication_req=(EditText)findViewById(R.id.medication);
        blood_grp=(EditText)findViewById(R.id.blood_grp);
        batch=(EditText)findViewById(R.id.batch_per_year);
        roll_no=(EditText)findViewById(R.id.roll_no);
        standard=(EditText)findViewById(R.id.standard);
        profile_pic = (CircleImageView) findViewById(R.id.logo);
        special_note=(EditText)findViewById(R.id.note);
        meal_pref=(EditText)findViewById(R.id.meals);
        submit=(Button)findViewById(R.id.next);
        special_note.setFilters(new InputFilter[]{new InputFilter.LengthFilter(500)});
        passport_first_name.setText(sharedPreferences.getString("first_name",""));
        passpost_middle_name.setText(sharedPreferences.getString("middle_name",""));
        passpost_last_name.setText(sharedPreferences.getString("last_name",""));
        passport_no.setText(sharedPreferences.getString("passport_number",""));
        email.setText(sharedPreferences.getString("email",""));
        gardian_name.setText(sharedPreferences.getString("guardian_name",""));
        gardian_no.setText(sharedPreferences.getString("guardian_number",""));
        allergies.setText(sharedPreferences.getString("allergies",""));
        medication_req.setText(sharedPreferences.getString("medical_req",""));
        roll_no.setText(sharedPreferences.getString("rollno",""));
        phone_no.setText(sharedPreferences.getString("mobile_no",""));
        dob.setText(sharedPreferences.getString("dob",""));
        nationality.setText(sharedPreferences.getString("nationality",""));
    }

    @Override
    public void onResume() {
        super.onResume();


    }

    public void uploadImage() {
        // When Image is selected from Gallery
        if (imgPath != null && !imgPath.isEmpty()) {
//            prgDialog.setMessage("Converting Image to Binary Data");
//            prgDialog.show();
//            // Convert image to String using Base64
            encodeImagetoString();
            // When Image is not selected from Gallery
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    "You must select image from gallery before you try to upload",
                    Toast.LENGTH_LONG).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgPath = cursor.getString(columnIndex);
                cursor.close();
                android.widget.ImageView imgView = (android.widget.ImageView) findViewById(R.id.logo);
                // Set the Image in ImageView
                imgView.setImageBitmap(BitmapFactory
                        .decodeFile(imgPath));
                // Get the Image's file name
                String fileNameSegments[] = imgPath.split("/");
                fileName = fileNameSegments[fileNameSegments.length - 1];
                // Put file name in Async Http Post Param which will used in Php web app
                params.put("filename", fileName);
                params.put("email", email.getText().toString());
            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

    }

    public void loadImagefromGallery(View view) {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }


    public void encodeImagetoString() {
        new AsyncTask<Void, Void, String>() {

            protected void onPreExecute() {

            }

            ;

            @Override
            protected String doInBackground(Void... params) {
                BitmapFactory.Options options = null;
                options = new BitmapFactory.Options();
                options.inSampleSize = 3;
                bitmap = BitmapFactory.decodeFile(imgPath,
                        options);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                // Must compress the Image to reduce image size to make upload easy
                bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
                byte[] byte_arr = stream.toByteArray();
                // Encode Image to String
                encodedString = Base64.encodeToString(byte_arr, 0);
                return "";
            }

            @Override
            protected void onPostExecute(String msg) {
                // prgDialog.setMessage("Calling Upload");
                // Put converted Image string into Async Http Post param
                params.put("image", encodedString);
                // Trigger Image upload
                triggerImageUpload();
            }
        }.execute(null, null, null);
    }

    public  void triggerImageUpload() {

        com.loopj.android.http.AsyncHttpClient client = new com.loopj.android.http.AsyncHttpClient();
        // Don't forget to change the IP address to your LAN address. Port no as well.
        client.post(ConstantUrls.uplaod_profile_pic,
                params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        Toast.makeText(getApplicationContext(), "",
                                Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        if (statusCode == 404) {
                            Toast.makeText(getApplicationContext(),
                                    "Requested resource not found",
                                    Toast.LENGTH_LONG).show();
                        }
                        // When Http response code is '500'
                        else if (statusCode == 500) {
                            Toast.makeText(getApplicationContext(),
                                    "Something went wrong at server end",
                                    Toast.LENGTH_LONG).show();
                        }
                        // When Http response code other than 404, 500
                        else {
                            Toast.makeText(
                                    getApplicationContext(),
                                    "Error Occured n Most Common Error: n1. Device not connected to Internetn2. Web App is not deployed in App servern3. App server is not runningn HTTP Status code : "
                                            + statusCode, Toast.LENGTH_LONG)
                                    .show();
                        }
                    }

                });


    }


    private static void upate_data(){

        StringRequest stringRequest= new StringRequest(Request.Method.POST, ConstantUrls.update_profile, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("result",response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map= new HashMap<>();
                map.put("first_name",passport_first_name.getText().toString());
                map.put("middle_name",passpost_middle_name.getText().toString());
                map.put("surname",passpost_last_name.getText().toString());
                map.put("dob",dob.getText().toString());
                map.put("gender","");
                map.put("passport_number",passport_no.getText().toString());
                map.put("expiry_date","");
                map.put("guardian_name",gardian_name.getText().toString());
                map.put("guardian_number",gardian_no.getText().toString());
                map.put("email",email.getText().toString());
                map.put("mobile_no",phone_no.getText().toString());
                map.put("nationality",nationality.getText().toString());
                map.put("allergies",allergies.getText().toString());
                map.put("medical_req",medication_req.getText().toString());
                map.put("rollno",roll_no.getText().toString());
                map.put("emergency_email",emergency_email.getText().toString());
                map.put("meal",meal_pref.getText().toString());
                map.put("blood_group",blood_grp.getText().toString());
                map.put("batch_year",batch.getText().toString());
                map.put("emergency_no",emer_contact_no.getText().toString());
                map.put("standard",standard.getText().toString());
                map.put("special_note",special_note.getText().toString());

                return map;

            }
        };
        reqeust.add(stringRequest);
    }

}
