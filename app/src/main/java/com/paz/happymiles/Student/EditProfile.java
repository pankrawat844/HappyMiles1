package com.paz.happymiles.Student;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.Base64;
import com.loopj.android.http.RequestParams;
import com.paz.happymiles.ConstantUrls;
import com.paz.happymiles.R;
import com.rey.material.widget.Spinner;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by lenovo on 4/4/2017.
 */
public class EditProfile extends Activity {
    EditText name, email, password, phno, naionality, gardian, gardian_no, allergies, medication, roll_no;
    TextView dob, gender;
    Spinner date, month, year;
    Button next;
    ImageView logo;
    List<String> days, months, years;
    SharedPreferences sharedpreferences;
    private static int RESULT_LOAD_IMG = 1;
    String imgPath, fileName,encodedString;
    Bitmap bitmap;
    RequestParams params= new RequestParams();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        days = new ArrayList<>();
        days.add("1");
        days.add("2");
        days.add("3");
        days.add("4");
        days.add("5");
        days.add("6");
        days.add("7");
        days.add("8");
        days.add("9");
        days.add("10");
        days.add("11");
        days.add("12");
        days.add("13");
        days.add("14");
        days.add("15");
        days.add("16");
        days.add("17");
        days.add("18");
        days.add("19");
        days.add("20");
        days.add("21");
        days.add("22");
        days.add("23");
        days.add("24");
        days.add("25");
        days.add("26");
        days.add("27");
        days.add("28");
        days.add("29");
        days.add("30");
        days.add("31");


        months = new ArrayList<String>();
        months.add("January");
        months.add("February");
        months.add("March");
        months.add("April");
        months.add("May");
        months.add("June");
        months.add("July");
        months.add("August");
        months.add("September");
        months.add("October");
        months.add("November");
        months.add("December");

        years = new ArrayList<String>();
        years.add("1980");
        years.add("1981");
        years.add("1982");
        years.add("1983");
        years.add("1984");
        years.add("1985");
        years.add("1986");
        years.add("1987");
        years.add("1988");
        years.add("1989");
        years.add("1990");
        years.add("1991");
        years.add("1992");
        years.add("1993");
        years.add("1994");
        years.add("1995");
        years.add("1996");
        years.add("1997");
        years.add("1998");

        years.add("1999");
        years.add("2000");
        years.add("2001");
        years.add("2002");
        years.add("2003");
        years.add("2004");
        years.add("2005");
        years.add("2006");
        years.add("2007");
        years.add("2008");
        years.add("2009");
        years.add("2010");
        years.add("2011");
        years.add("2012");
        years.add("2013");
        years.add("2014");
        years.add("2015");
        years.add("2016");
        years.add("2017");
        logo = (ImageView) findViewById(R.id.logo);
        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        phno = (EditText) findViewById(R.id.phno);
        dob = (TextView) findViewById(R.id.dob);
        date = (Spinner) findViewById(R.id.date);
        month = (Spinner) findViewById(R.id.month);
        year = (Spinner) findViewById(R.id.year);
        gender = (TextView) findViewById(R.id.gender);
        naionality = (EditText) findViewById(R.id.naionality);
        gardian = (EditText) findViewById(R.id.gardian);
        gardian_no = (EditText) findViewById(R.id.gardian_no);
        allergies = (EditText) findViewById(R.id.allergies);
        medication = (EditText) findViewById(R.id.medication);
        roll_no = (EditText) findViewById(R.id.roll_no);
        sharedpreferences = getSharedPreferences("pref", Context.MODE_PRIVATE);
        name.setText(sharedpreferences.getString("first_name", ""));
        email.setText(sharedpreferences.getString("email", ""));
        password.setText(sharedpreferences.getString("password", ""));
        naionality.setText(sharedpreferences.getString("nationality", ""));
        gardian.setText(sharedpreferences.getString("guardian_name", ""));
        gardian_no.setText(sharedpreferences.getString("guardian_number", ""));
        allergies.setText(sharedpreferences.getString("allergies", ""));
        medication.setText(sharedpreferences.getString("medical_req", ""));

        ArrayAdapter<String> date_adapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,days);
        ArrayAdapter<String> month_adapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,months);
        ArrayAdapter<String>year_adapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,years);

        date.setAdapter(date_adapter);
        month.setAdapter(month_adapter);
        year.setAdapter(year_adapter);
        Picasso.with(this).load(sharedpreferences.getString("profile_img", "")).error(R.drawable.user).into(logo);

        next = (Button) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadImagefromGallery(v);
            }
        });
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
                ImageView imgView = (ImageView) findViewById(R.id.logo);
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

    public  void triggerImageUpload(){
//      StringRequest stringRequest = new StringRequest(Request.Method.POST, ConstantUrls.uplaod_profile_pic, new Response.Listener<String>() {
//          @Override
//          public void onResponse(String response) {
//              Toast.makeText(getApplicationContext(), response,
//                      Toast.LENGTH_LONG).show();
//
//          }
//      }, new Response.ErrorListener() {
//          @Override
//          public void onErrorResponse(VolleyError error) {
//              if (error.networkResponse.statusCode == 404) {
//                  Toast.makeText(getApplicationContext(),
//                          "Requested resource not found",
//                          Toast.LENGTH_LONG).show();
//              }
//              // When Http response code is '500'
//              else if (error.networkResponse.statusCode == 500) {
//                  Toast.makeText(getApplicationContext(),
//                          "Something went wrong at server end",
//                          Toast.LENGTH_LONG).show();
//              }
//              // When Http response code other than 404, 500
//              else {
//                  Toast.makeText(
//                          getApplicationContext(),
//                          "Error Occured n Most Common Error: n1. Device not connected to Internetn2. Web App is not deployed in App servern3. App server is not runningn HTTP Status code : "
//                                  + error.networkResponse.statusCode, Toast.LENGTH_LONG)
//                          .show();
//              }
//          }
//      }){
//          @Override
//          protected Map<String, String> getParams() throws AuthFailureError {
//
//              return params;
//          }
//      };



        com.loopj.android.http.AsyncHttpClient client = new com.loopj.android.http.AsyncHttpClient();
        // Don't forget to change the IP address to your LAN address. Port no as well.
        client.post(ConstantUrls.uplaod_profile_pic,
                params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        Toast.makeText(getApplicationContext(),"",
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
}