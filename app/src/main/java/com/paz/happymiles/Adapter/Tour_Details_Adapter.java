package com.paz.happymiles.Adapter;

import android.content.Context;
import android.content.pm.LabeledIntent;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.paz.happymiles.R;
import com.paz.happymiles.Student.Tour_Details;
import com.paz.happymiles.Student_Pojo.Tour_Details_Pojo;
import com.squareup.picasso.Picasso;

import java.util.List;

import custom_font.MyTextView;

/**
 * Created by Admin on 2/27/2017.
 */

public class Tour_Details_Adapter extends RecyclerView.Adapter<Tour_Details_Adapter.viewholder> implements RecyclerView.OnItemTouchListener{
List<Tour_Details_Pojo> list;
Context ctx;
    private OnItemClickListener mListener;


    GestureDetector mGestureDetector;


    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }
        public Tour_Details_Adapter( List<Tour_Details_Pojo> list,Context ctx){
            this.list=list;
            this.ctx=ctx;
        }
    public Tour_Details_Adapter(Context ctx,OnItemClickListener listener){

        mListener = listener;

        mGestureDetector = new GestureDetector(ctx, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }

    @Override
    public viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.tour_details_item,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(viewholder holder, int position) {
            Tour_Details_Pojo pojo = list.get(position);
            holder.transport.setText(pojo.getTransport());
        Picasso.with(ctx).load(pojo.getBg_img()).into(holder.imgview);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        View childView = view.findChildViewUnder(e.getX(), e.getY());
        if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
            mListener.onItemClick(childView, view.getChildAdapterPosition(childView));
            return true;
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    class viewholder extends RecyclerView.ViewHolder
    {
        MyTextView transport;
        ImageView imgview;
        public viewholder(View itemView) {
            super(itemView);
            imgview=(ImageView) itemView.findViewById(R.id.bg_img);
            transport= (MyTextView) itemView.findViewById(R.id.transport);
        }
    }

}
