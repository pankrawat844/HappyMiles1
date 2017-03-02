package com.paz.happymiles.Student_Pojo;

/**
 * Created by Admin on 2/27/2017.
 */

public class Tour_Details_Pojo {
    String transport;
    int bg_img;

    public Tour_Details_Pojo(String transport, int bg_img) {
        this.transport = transport;
        this.bg_img = bg_img;
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public int getBg_img() {
        return bg_img;
    }

    public void setBg_img(int bg_img) {
        this.bg_img = bg_img;
    }
}
