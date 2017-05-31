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
import android.widget.TextView;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.ImageViewBitmapInfo;
import com.koushikdutta.ion.Ion;
import com.paz.happymiles.R;
import com.paz.happymiles.Recylevie_animation.OnItemClickListener;
import com.paz.happymiles.Recylevie_animation.Utils;
import com.paz.happymiles.Student_Pojo.About_tour_pojo;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import custom_font.MyTextView;

/**
 * Created by Admin on 2/27/2017.
 */

public class Home_Adapter extends RecyclerView.Adapter<Home_Adapter.view_holder>  {
        List<About_tour_pojo> list;
        Context ctx;
    private com.paz.happymiles.Recylevie_animation.OnItemClickListener mListener;
    private int defaultBackgroundcolor;

    GestureDetector mGestureDetector;
    private static final int SCALE_DELAY = 10;

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }
       public Home_Adapter(List<About_tour_pojo> list,Context ctx){
            this.list=list;
            this.ctx=ctx;

        }
    public void setOnItemClickListener(com.paz.happymiles.Recylevie_animation.OnItemClickListener onItemClickListener) {
        this.mListener = onItemClickListener;
    }
    public Home_Adapter(Context ctx, com.paz.happymiles.Recylevie_animation.OnItemClickListener listener){

        mListener = listener;

        mGestureDetector = new GestureDetector(ctx, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }

    @Override
    public view_holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row,parent,false);
        this.ctx = parent.getContext();
        defaultBackgroundcolor = ctx.getResources().getColor(R.color.appthemecolor);
        return new view_holder(view,mListener );
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
    public void onBindViewHolder(final view_holder holder, final int position) {
         final  About_tour_pojo pojo= list.get(position);
       Picasso.with(ctx).load(pojo.getTour_img()).placeholder(R.drawable.bg).into(holder.imgview);
        holder.days.setText("Day "+pojo.getDay());
        holder.imgview.setDrawingCacheEnabled(true);
//        Ion.with(ctx)
//                .load(pojo.getTour_img())
//                .intoImageView(holder.imgview)
//                .withBitmapInfo()
//                .setCallback(new FutureCallback<ImageViewBitmapInfo>() {
//                    @Override
//                    public void onCompleted(Exception e, ImageViewBitmapInfo result) {
//
//                        if (e == null && result != null) {
//
//                            setCellColors(result.getBitmapInfo().bitmap, holder, position);
//                           amimateCell(holder);
//                        }
//                    }
//                });
        Bitmap bookCoverBitmap = BitmapFactory.decodeResource(ctx.getResources(),R.drawable.arrow);
        setCellColors(bookCoverBitmap, holder, position);
        amimateCell(holder);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }




    class view_holder extends RecyclerView.ViewHolder implements View.OnClickListener{
        MyTextView days;
        ImageView imgview;
        RelativeLayout bookContainer;
        protected boolean animated = false;
        private com.paz.happymiles.Recylevie_animation.OnItemClickListener onItemClickListener;

        public view_holder(View itemView, com.paz.happymiles.Recylevie_animation.OnItemClickListener onItemClickListener) {
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

    private void amimateCell(view_holder booksViewHolder) {

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

    public void setCellColors (Bitmap b, final view_holder viewHolder, final int position) {

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

                        Utils.animateViewColor(viewHolder.imgview, defaultBackgroundcolor,
                                vibrantSwatch.getRgb());

                    } else {

                        Log.e("[ERROR]", "BookAdapter onGenerated - The VibrantSwatch were null at: " + position);
                    }
                }
            });
        }
    }
}
