package com.paz.happymiles.Student_Pojo;

/**
 * Created by Admin on 5/19/2017.
 */

public class Main_Pojo {
    String name, color_code;
    int img;

    public Main_Pojo(int img, String name, String color_code) {
        this.img = img;
        this.name = name;
        this.color_code = color_code;
    }

    public String getColor_code() {
        return color_code;
    }

    public void setColor_code(String color_code) {
        this.color_code = color_code;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
