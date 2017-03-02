package com.paz.happymiles.Student_Pojo;

import android.view.View;

/**
 * Created by Admin on 2/27/2017.
 */

public class About_tour_pojo {
    String day,day_details;
    int tour_img;
    private View.OnClickListener requestBtnClickListener;

    public About_tour_pojo(String day, int tour_img,String day_details) {
        this.day = day;
        this.tour_img = tour_img;
        this.day_details=day_details;
    }

    public String getDay_details() {
        return day_details;
    }

    public void setDay_details(String day_details) {
        this.day_details = day_details;
    }

    public View.OnClickListener getRequestBtnClickListener() {
        return requestBtnClickListener;
    }

    public void setRequestBtnClickListener(View.OnClickListener requestBtnClickListener) {
        this.requestBtnClickListener = requestBtnClickListener;
    }
    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getTour_img() {
        return tour_img;
    }

    public void setTour_img(int tour_img) {
        this.tour_img = tour_img;
    }
}
