package com.paz.happymiles.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.paz.happymiles.R;
import com.paz.happymiles.Recylevie_animation.*;
import com.paz.happymiles.Student.Hotel_Detail;
import com.paz.happymiles.Student_Pojo.Hotel_Detail_Pojo;
import com.squareup.picasso.Picasso;

import java.util.List;

import custom_font.MyTextView;

/**
 * Created by Admin on 5/16/2017.
 */

public class Hotel_Detail_Adapter extends RecyclerView.Adapter<Hotel_Detail_Adapter.viewholder>{
        List<Hotel_Detail_Pojo> list;
        Context ctx;
    private int defaultBackgroundcolor;

    private com.paz.happymiles.Recylevie_animation.OnItemClickListener mListener;
    GestureDetector mGestureDetector;
    private static final int SCALE_DELAY = 10;
    public Hotel_Detail_Adapter(List<Hotel_Detail_Pojo> list, Context ctx){
         this.list=list;
        this.ctx=ctx;
     }


    public void setOnItemClickListener(com.paz.happymiles.Recylevie_animation.OnItemClickListener onItemClickListener) {
        this.mListener = onItemClickListener;
    }
    @Override
    public viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.hotel_row,parent,false);
        this.ctx = parent.getContext();
        defaultBackgroundcolor = ctx.getResources().getColor(R.color.appthemecolor);
        return new viewholder(view,mListener);
    }

    @Override
    public void onBindViewHolder(viewholder holder, int position) {
        final  Hotel_Detail_Pojo pojo= list.get(position);
        Picasso.with(ctx).load(pojo.getHotel_img()).error(R.drawable.bg).into(holder.imgview);
        holder.imgview.setDrawingCacheEnabled(true);
        holder.days.setText("Day " + pojo.getDay());
        Bitmap bookCoverBitmap = BitmapFactory.decodeResource(ctx.getResources(),R.drawable.arrow);
        setCellColors(bookCoverBitmap, holder, position);
        amimateCell(holder);
    }

    public Hotel_Detail_Adapter(Context ctx, com.paz.happymiles.Recylevie_animation.OnItemClickListener listener){

        mListener = listener;

        mGestureDetector = new GestureDetector(ctx, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }
    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    class viewholder extends RecyclerView.ViewHolder implements View.OnClickListener{
        MyTextView days;
        ImageView imgview;
        RelativeLayout bookContainer;
        protected boolean animated = false;
        private com.paz.happymiles.Recylevie_animation.OnItemClickListener onItemClickListener;

        public viewholder(View itemView,OnItemClickListener onItemClickListener) {
            super(itemView);

            this.onItemClickListener = onItemClickListener;
            bookContainer=(RelativeLayout)itemView.findViewById(R.id.cointainer);
            imgview=(ImageView) itemView.findViewById(R.id.bg_img);
            days= (MyTextView) itemView.findViewById(R.id.days);
            imgview.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onClick(v, getPosition());
        }
    }


    private void amimateCell(viewholder booksViewHolder) {

        int cellPosition = booksViewHolder.getPosition();

        if (!booksViewHolder.animated) {

            booksViewHolder.animated = true;
            booksViewHolder.bookContainer.setScaleY(0);
            booksViewHolder.bookContainer.setScaleX(0);
            booksViewHolder.bookContainer.animate()
                    .scaleY(1).scaleX(1)
                    .setDuration(50)
                    .setStartDelay(SCALE_DELAY * cellPosition)
                    .start();
        }

    }

    public void setCellColors (Bitmap b, final viewholder viewHolder, final int position) {

        if (b != null) {
            Palette.generateAsync(b, new Palette.PaletteAsyncListener() {

                @Override
                public void onGenerated(Palette palette) {

                    Palette.Swatch vibrantSwatch = palette.getVibrantSwatch();

                    if (vibrantSwatch != null) {

                        //  viewHolder.imgview.setTextColor(vibrantSwatch.getTitleTextColor());
                        viewHolder.days.setTextColor(vibrantSwatch.getTitleTextColor());
                        viewHolder.imgview.setTransitionName("cover" + position);
                        viewHolder.bookContainer.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mListener.onClick(v, position);
                            }
                        });

                        com.paz.happymiles.Recylevie_animation.Utils.animateViewColor(viewHolder.imgview, defaultBackgroundcolor,
                                vibrantSwatch.getRgb());

                    } else {

                        Log.e("[ERROR]", "BookAdapter onGenerated - The VibrantSwatch were null at: " + position);
                    }
                }
            });
        }
    }
}
