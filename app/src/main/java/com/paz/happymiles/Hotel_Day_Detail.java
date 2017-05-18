package com.paz.happymiles;

import android.animation.Animator;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.paz.happymiles.Recylevie_animation.CustomAnimatorListener;
import com.paz.happymiles.Recylevie_animation.CustomTransitionListener;
import com.paz.happymiles.Recylevie_animation.Utils;
import com.paz.happymiles.Student.About_Tour;
import com.paz.happymiles.Student.Hotel_Detail;
import com.paz.happymiles.Student_Pojo.About_tour_pojo;
import com.paz.happymiles.Student_Pojo.Hotel_Detail_Pojo;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Admin on 5/16/2017.
 */

public class Hotel_Day_Detail extends Activity{

    @InjectView(R.id.card_view)
    FrameLayout contentCard;

    @InjectView(R.id.activity_detail_main_container)
    View mainContaienr;
    @InjectView(R.id.activity_detail_titles_container)  View titlesContainer;
    @InjectView(R.id.activity_detail_toolbar)
    Toolbar toolbar;
    @InjectView(R.id.activity_detail_fab)               View fabButton;
    @InjectView(R.id.activity_detail_book_info)
    LinearLayout bookInfoLayout;
    @InjectView(R.id.activity_detail_content)
    TextView contentTextView;
    @InjectView(R.id.activity_detail_rating_title)      TextView ratingTextView;
    @InjectView(R.id.activity_detail_rating_value)      TextView ratingValueTextView;
    @InjectView(R.id.activity_detail_summary_title)     TextView hotel_name;
    @InjectView(R.id.activity_detail_title)             TextView titleTextView;
    @InjectView(R.id.activity_detail_subtitle)          TextView subtitleTextView;
    private Hotel_Detail_Pojo pojo;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hotel_day_detail);


        ButterKnife.inject(this);
        final int position = getIntent().getIntExtra("position", 0);
        pojo = (Hotel_Detail_Pojo) getIntent().getSerializableExtra("selected_book");
        Bitmap bookCoverBitmap = Hotel_Detail.photoCache.get(position);
        //  Bitmap bookCoverBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.arrow);

        ImageView toolbarBookCover = (ImageView) findViewById(R.id.activity_detail_cover);
        toolbarBookCover.setImageBitmap(bookCoverBitmap);
        fabButton.setScaleX(0);
        fabButton.setScaleY(0);
        Utils.configureHideYView(contentCard);
        Utils.configureHideYView(bookInfoLayout);
        Utils.configureHideYView(mainContaienr);
        toolbar.setBackground(new BitmapDrawable(getResources(), bookCoverBitmap));
        //  toolbar.setBackgroundResource(R.drawable.bg);
        toolbar.setTransitionName("cover" + position);
        getWindow().getSharedElementEnterTransition().addListener(sharedTransitionListener);
        Palette.generateAsync(bookCoverBitmap, paletteListener);

    }


    private CustomTransitionListener sharedTransitionListener = new CustomTransitionListener() {

        @Override
        public void onTransitionEnd(Transition transition) {

            super.onTransitionEnd(transition);

            ViewPropertyAnimator showTitleAnimator = Utils.showViewByScale(mainContaienr);
            showTitleAnimator.setListener(new CustomAnimatorListener() {

                @Override
                public void onAnimationEnd(Animator animation) {

                    super.onAnimationEnd(animation);
                    titlesContainer.startAnimation(AnimationUtils.loadAnimation(Hotel_Day_Detail.this, R.anim.alpha_on));
                    titlesContainer.setVisibility(View.VISIBLE);

                    Utils.showViewByScale(fabButton).start();
                    Utils.showViewByScale(bookInfoLayout).start();
                }
            });

            showTitleAnimator.start();
        }
    };
    @Override
    public void onBackPressed() {

        ViewPropertyAnimator hideTitleAnimator = Utils.hideViewByScaleXY(fabButton);

        titlesContainer.startAnimation(AnimationUtils.loadAnimation(Hotel_Day_Detail.this, R.anim.alpha_off));
        titlesContainer.setVisibility(View.INVISIBLE);

        Utils.hideViewByScaleY(bookInfoLayout);

        hideTitleAnimator.setListener(new CustomAnimatorListener() {

            @Override
            public void onAnimationEnd(Animator animation) {

                ViewPropertyAnimator hideFabAnimator = Utils.hideViewByScaleY(mainContaienr);
                hideFabAnimator.setListener(new CustomAnimatorListener() {

                    @Override
                    public void onAnimationEnd(Animator animation) {

                        super.onAnimationEnd(animation);
                        coolBack();
                    }
                });
            }
        });

        hideTitleAnimator.start();
    }
    private Palette.PaletteAsyncListener paletteListener = new Palette.PaletteAsyncListener() {

        @Override
        public void onGenerated(Palette palette) {

            if (palette.getVibrantSwatch() != null) {

                Palette.Swatch vibrantSwatch = palette.getVibrantSwatch();
                mainContaienr.setBackgroundColor(vibrantSwatch.getRgb());

                getWindow().setStatusBarColor(vibrantSwatch.getRgb());
                getWindow().setNavigationBarColor(vibrantSwatch.getRgb());

                String content = pojo.getHotel_location();

                if (content != null)
                    contentTextView.setText(content);

                titleTextView.setText("Day "+pojo.getDay());
                subtitleTextView.setTextColor(vibrantSwatch.getTitleTextColor());
                subtitleTextView.setText(pojo.getDay());
                ratingValueTextView.setText(pojo.getHotel_contact_no());

                hotel_name.setTextColor(vibrantSwatch.getRgb());
                hotel_name.setText("Hotel Name: "+pojo.getHotel_name());
                titleTextView.setTextColor(vibrantSwatch.getTitleTextColor());
                subtitleTextView.setTextColor(vibrantSwatch.getTitleTextColor());
                ratingTextView.setTextColor(vibrantSwatch.getTitleTextColor());
                ratingTextView.setTextColor(vibrantSwatch.getRgb());
            }
        }
    };

    private void coolBack() {

        try {
            super.onBackPressed();

        } catch (NullPointerException e) {

            // TODO: workaround
        }

    }
}
