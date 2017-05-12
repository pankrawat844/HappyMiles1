package com.paz.happymiles.Tour_Manager;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.paz.happymiles.Faculty.MainActvity;
import com.paz.happymiles.R;
import com.paz.happymiles.Student.About_Tour;
import com.paz.happymiles.Student.Payment;
import com.paz.happymiles.Student.Profile;
import com.paz.happymiles.Student.Tour_Details;

import java.util.List;

/**
 * Created by Admin on 3/16/2017.
 */

public class Tab_Activity extends AppCompatActivity implements TabLayout.OnTabSelectedListener{

    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        tabLayout=(TabLayout)findViewById(R.id.tab);
        viewPager=(ViewPager)findViewById(R.id.viewpager);
        tabLayout.addTab(tabLayout.newTab().setText("My Profile").setIcon(R.mipmap.profile1));
        tabLayout.addTab(tabLayout.newTab().setText("Approvals").setIcon(R.mipmap.abt1));
        tabLayout.addTab(tabLayout.newTab().setText("Requests").setIcon(R.mipmap.tour1));

        Home_TabAdapter tabAdapter= new Home_TabAdapter(getSupportFragmentManager(),3);
        viewPager.setAdapter(tabAdapter);
        // tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnTabSelectedListener(this);
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.appthemecolor));

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
        tabLayout.getTabAt(tab.getPosition()).select();
        if (tab.getPosition() == 1) {

            tabLayout.getTabAt(tab.getPosition()).setIcon(R.mipmap.abt2);
            tabLayout.getTabAt(1).setIcon(R.mipmap.tour1);
            tabLayout.getTabAt(2).setIcon(R.mipmap.payment1);
            tabLayout.getTabAt(3).setIcon(R.mipmap.profile1);
        }

        if(tab.getPosition()==2){
            tabLayout.getTabAt(tab.getPosition()).setIcon(R.mipmap.payment2);
            tabLayout.getTabAt(0).setIcon(R.mipmap.abt1);
            tabLayout.getTabAt(1).setIcon(R.mipmap.tour1);
            tabLayout.getTabAt(3).setIcon(R.mipmap.profile1);

        }
        if(tab.getPosition()==0){
            tabLayout.getTabAt(tab.getPosition()).setIcon(R.mipmap.profile2);
            tabLayout.getTabAt(0).setIcon(R.mipmap.abt1);
            tabLayout.getTabAt(1).setIcon(R.mipmap.tour1);
            tabLayout.getTabAt(2).setIcon(R.mipmap.payment1);

        }
    }
    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }


    private class Home_TabAdapter extends FragmentStatePagerAdapter {
        List<String> tabs;
        int tabcount;
        public Home_TabAdapter(FragmentManager fm, int tabcount) {

            super(fm);
            this.tabcount=tabcount;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    About_Tour about_tour= new About_Tour();
                    return  about_tour;

                case 1:
                    Tour_Details tour_details= new Tour_Details();
                    return tour_details;
                case 2:
                    Payment payment= new Payment();
                    return payment;
                case 3:
                    Profile profile= new Profile();
                    return profile;
                default:
                    About_Tour tour = new About_Tour();
                    return tour;
            }
        }



        @Override
        public int getCount() {
            return tabcount;
        }
    }
}
