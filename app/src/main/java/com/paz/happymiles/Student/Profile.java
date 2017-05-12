package com.paz.happymiles.Student;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.paz.happymiles.MainActivity;
import com.paz.happymiles.R;
import com.rey.material.widget.ImageView;
import com.rey.material.widget.Spinner;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Admin on 2/27/2017.
 */

public class Profile extends Fragment {
    TextView profile,guardian_detail,meal_pref,med_req,edit_profile;

    Context context;
    de.hdodenhof.circleimageview.CircleImageView profile_pic;
    public Spinner spinner, spinner1, spinner2, spinner3;
    List<String> list, day, month, year;
    private static int RESULT_LOAD_IMG = 1;
    SharedPreferences sharedPreferences;
    android.widget.ImageView logout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile, container, false);
        sharedPreferences= getActivity().getSharedPreferences("pref",Context.MODE_PRIVATE);

        profile = (TextView) view.findViewById(R.id.text1);
        profile_pic = (CircleImageView) view.findViewById(R.id.profile);
        guardian_detail=(TextView)view.findViewById(R.id.text2);
        meal_pref=(TextView)view.findViewById(R.id.text3);
        med_req=(TextView)view.findViewById(R.id.text4);
        logout=(android.widget.ImageView)view.findViewById(R.id.imageView2);
        edit_profile=(TextView)view.findViewById(R.id.edit_profile);
        Picasso.with(getActivity()).load(sharedPreferences.getString("profile_img","")).error(R.drawable.user).into(profile_pic);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor= sharedPreferences.edit();
                editor.clear();
                editor.commit();
               Intent intent =new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getActivity(),EditProfile.class);
                startActivity(intent);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile= new Intent(getActivity(), Profile_Detail.class);

                startActivity(profile);
            }
        });

        guardian_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent g= new Intent(getActivity(), Guardian_Detail.class);

                startActivity(g);
            }
        });

        meal_pref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent g= new Intent(getActivity(), Meal_Pref.class);

                startActivity(g);
            }
        });

        med_req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent g= new Intent(getActivity(), Medical_Req.class);

                startActivity(g);
            }
        });

        profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
//                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//// Start the Intent
//                startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
            }
        });
        return view;

    }


    @Override
    public void onResume() {
        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    // handle back button's click listener
                   // Toast.makeText(getActivity(), "Back press", Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                    SharedPreferences.Editor editor= sharedPreferences.edit();
                    editor.putBoolean("finish",true);
                    editor.commit();
                    return true;
                }
                return false;
            }
        });
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
