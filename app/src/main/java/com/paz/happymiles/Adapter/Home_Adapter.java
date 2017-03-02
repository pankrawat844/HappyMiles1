package com.paz.happymiles.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.paz.happymiles.R;
import com.paz.happymiles.Student_Pojo.About_tour_pojo;
import com.squareup.picasso.Picasso;

import java.util.List;

import custom_font.MyTextView;

/**
 * Created by Admin on 2/27/2017.
 */

public class Home_Adapter extends RecyclerView.Adapter<Home_Adapter.view_holder> implements RecyclerView.OnItemTouchListener {
        List<About_tour_pojo> list;
        Context ctx;
    private OnItemClickListener mListener;
    GestureDetector mGestureDetector;

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }
       public Home_Adapter(List<About_tour_pojo> list,Context ctx){
            this.list=list;
            this.ctx=ctx;

        }
    public Home_Adapter(Context ctx,OnItemClickListener listener){

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
        return new view_holder(view);
    }

    @Override
    public void onBindViewHolder(view_holder holder, int position) {
            About_tour_pojo pojo= list.get(position);
        Picasso.with(ctx).load(pojo.getTour_img()).into(holder.imgview);
        holder.days.setText(pojo.getDay());
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

    class view_holder extends RecyclerView.ViewHolder{
        MyTextView days;
        ImageView imgview;
        public view_holder(View itemView) {
            super(itemView);
            imgview=(ImageView) itemView.findViewById(R.id.bg_img);
            days= (MyTextView) itemView.findViewById(R.id.days);
        }
    }
}
