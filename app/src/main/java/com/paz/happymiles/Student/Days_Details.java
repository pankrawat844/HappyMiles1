package com.paz.happymiles.Student;

import android.animation.Animator;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.paz.happymiles.R;
import com.paz.happymiles.Recylevie_animation.CustomAnimatorListener;
import com.paz.happymiles.Recylevie_animation.CustomTransitionListener;
import com.paz.happymiles.Recylevie_animation.ObservableScrollView;
import com.paz.happymiles.Recylevie_animation.Utils;
import com.paz.happymiles.Student_Pojo.About_tour_pojo;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Admin on 3/3/2017.
 */

public class Days_Details extends Activity {


    @InjectView(R.id.card_view) FrameLayout contentCard;
    @InjectView(R.id.scroll)
    ObservableScrollView scrollView;

    @InjectView(R.id.activity_detail_main_container)    View mainContaienr;
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
    @InjectView(R.id.activity_detail_summary_title)     TextView summaryTitle;
    @InjectView(R.id.activity_detail_title)             TextView titleTextView;
    @InjectView(R.id.activity_detail_subtitle)          TextView subtitleTextView;
    private About_tour_pojo selectedBook;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tour_detail);
        ButterKnife.inject(this);
        final int position = getIntent().getIntExtra("position", 0);
        selectedBook = (About_tour_pojo) getIntent().getSerializableExtra("selected_book");
        Bitmap bookCoverBitmap = About_Tour.photoCache.get(position);
      //  Bitmap bookCoverBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.arrow);

        ImageView toolbarBookCover = (ImageView) findViewById(R.id.activity_detail_cover);
        //toolbarBookCover.setImageBitmap(bookCoverBitmap);
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

        toolbar.setOnTouchListener(new View.OnTouchListener() {

            private GestureDetector gestureDetector = new GestureDetector(Days_Details.this, new GestureDetector.SimpleOnGestureListener() {

                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    // Toast.makeText(getApplicationContext(),"single tap",3).show();
                    return super.onSingleTapUp(e);
                }

                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    onBackPressed();
                    // Toast.makeText(getApplicationContext(),"double tap",3).show();
                    return super.onDoubleTap(e);
                }
            });

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);

                return false;
            }
        });

        scrollView.setOnTouchListener(new View.OnTouchListener() {

            private GestureDetector gestureDetector = new GestureDetector(Days_Details.this, new GestureDetector.SimpleOnGestureListener() {

                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    // Toast.makeText(getApplicationContext(),"single tap",3).show();
                    onBackPressed();
                    return super.onSingleTapUp(e);
                }

                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    // Toast.makeText(getApplicationContext(),"double tap",3).show();

                    return super.onDoubleTap(e);
                }
            });

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return false;
            }
        });
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
                    titlesContainer.startAnimation(AnimationUtils.loadAnimation(Days_Details.this, R.anim.alpha_on));
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

        titlesContainer.startAnimation(AnimationUtils.loadAnimation(Days_Details.this, R.anim.alpha_off));
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

                String content = selectedBook.getDay_details();

                if (content != null)
                    contentTextView.setText(content);

                titleTextView.setText("Day "+selectedBook.getDay());
                subtitleTextView.setTextColor(vibrantSwatch.getTitleTextColor());
                subtitleTextView.setText(selectedBook.getDay());
                ratingValueTextView.setText(selectedBook.getDay() + " / 10");

                summaryTitle.setTextColor(vibrantSwatch.getRgb());
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
