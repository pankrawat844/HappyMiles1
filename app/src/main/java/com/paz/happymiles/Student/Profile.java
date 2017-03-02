package com.paz.happymiles.Student;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.paz.happymiles.R;
import com.rey.material.app.Dialog;
import com.rey.material.widget.Spinner;

import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Admin on 2/27/2017.
 */

public class Profile extends Fragment {
    TextView profile;
    EditText name;
    EditText email;
    EditText number;
    EditText nation;
    Button button;
    Context context;
    de.hdodenhof.circleimageview.CircleImageView profile_pic;
    public Spinner spinner, spinner1, spinner2, spinner3;
    List<String> list, day, month, year;
    private static int RESULT_LOAD_IMG = 1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile, container, false);

        profile = (TextView) view.findViewById(R.id.text1);
        profile_pic = (CircleImageView) view.findViewById(R.id.profile);
        list = new ArrayList<>();
        list.add("Male");
        list.add("Female");
        day = new ArrayList<String>();
        day.add("1");
        day.add("2");
        day.add("3");
        day.add("4");
        day.add("5");
        day.add("6");
        day.add("7");
        day.add("8");
        day.add("9");
        day.add("10");
        day.add("11");
        day.add("12");
        day.add("13");
        day.add("14");
        day.add("15");
        day.add("16");
        day.add("17");
        day.add("18");
        day.add("19");
        day.add("20");
        day.add("21");
        day.add("22");
        day.add("23");
        day.add("24");
        day.add("25");
        day.add("26");
        day.add("27");
        day.add("28");
        day.add("29");
        day.add("30");
        day.add("31");

        month = new ArrayList<String>();
        month.add("January");
        month.add("February");
        month.add("March");
        month.add("April");
        month.add("May");
        month.add("June");
        month.add("July");
        month.add("August");
        month.add("September");
        month.add("October");
        month.add("November");
        month.add("December");

        year = new ArrayList<String>();
        year.add("1999");
        year.add("2000");
        year.add("2001");
        year.add("2002");
        year.add("2003");
        year.add("2004");
        year.add("2005");
        year.add("2006");
        year.add("2007");
        year.add("2008");
        year.add("2009");
        year.add("2010");
        year.add("2011");
        year.add("2012");
        year.add("2013");
        year.add("2014");
        year.add("2015");
        year.add("2016");
        year.add("2017");
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_data);
                dialog.applyStyle(R.style.CustomDialog1);
//                name=(EditText)dialog.findViewById(R.id.txt1);
//                email=(EditText)dialog.findViewById(R.id.txt2);
//                number=(EditText)dialog.findViewById(R.id.num);
//                nation=(EditText)dialog.findViewById(R.id.nation);
//                button=(Button)dialog.findViewById(R.id.button);
//                spinner=(Spinner)dialog.findViewById(R.id.spinr);
//                spinner1 = (Spinner) dialog.findViewById(R.id.spinr1);
//                spinner2 = (Spinner) dialog.findViewById(R.id.spinr2);
//                spinner3=(Spinner)dialog.findViewById(R.id.spinr3);

//                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list);
//                //adapter.setDropDownViewResource(R.layout.miles);
//                spinner.setAdapter(adapter);
//
//
//                ArrayAdapter<String> adaptr = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, month);
//                // adapter.setDropDownViewResource(R.layout.miles);
//                spinner1.setAdapter(adaptr);
//
//
//
//                ArrayAdapter<String> adapt = new ArrayAdapter<String>(getActivity(),  android.R.layout.simple_list_item_1, day);
//                //adapter.setDropDownViewResource(R.layout.miles);
//                spinner2.setAdapter(adapt);
//
//
//                ArrayAdapter<String> yr = new ArrayAdapter<String>(getActivity(),  android.R.layout.simple_list_item_1, year);
//                // adapter.setDropDownViewResource(R.layout.miles);
//                spinner3.setAdapter(yr);

//                button.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//                    }
//                });
                dialog.show();
            }
        });

        profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
// Start the Intent
                startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
            }
        });
        return view;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == Activity.RESULT_OK
                    && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                // Get the cursor
                Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String imgDecodableString = cursor.getString(columnIndex);
                cursor.close();

                // Set the Image in ImageView after decoding the String
                profile_pic.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));

            } else {
                Toast.makeText(getActivity(), "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

    }

}
