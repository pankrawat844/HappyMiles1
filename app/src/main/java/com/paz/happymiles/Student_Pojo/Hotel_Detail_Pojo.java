package com.paz.happymiles.Student_Pojo;

import java.io.Serializable;

/**
 * Created by Admin on 5/16/2017.
 */

public class Hotel_Detail_Pojo implements Serializable{
    String hotel_name,hotel_contact_no,hotel_location,hotel_img;
    String day;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getHotel_img() {
        return hotel_img;
    }

    public void setHotel_img(String hotel_img) {
        this.hotel_img = hotel_img;
    }

    public Hotel_Detail_Pojo(String hotel_name, String hotel_contact_no, String hotel_location, String hotel_img,String day) {
        this.hotel_name = hotel_name;
        this.hotel_contact_no = hotel_contact_no;
        this.hotel_location = hotel_location;
        this.hotel_img = hotel_img;
        this.day=day;
    }

    public String getHotel_name() {
        return hotel_name;
    }



    public void setHotel_name(String hotel_name) {
        this.hotel_name = hotel_name;
    }

    public String getHotel_contact_no() {
        return hotel_contact_no;
    }

    public void setHotel_contact_no(String hotel_contact_no) {
        this.hotel_contact_no = hotel_contact_no;
    }

    public String getHotel_location() {
        return hotel_location;
    }

    public void setHotel_location(String hotel_location) {
        this.hotel_location = hotel_location;
    }
}
