package com.paz.happymiles.Adapter;

import android.content.Context;
import android.graphics.Color;
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
import com.paz.happymiles.Student_Pojo.Main_Pojo;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Admin on 5/19/2017.
 */

public class Main_Adapter extends RecyclerView.Adapter<Main_Adapter.viewholder> implements RecyclerView.OnItemTouchListener {
    List<Main_Pojo> list;
    Context ctx;
    private OnItemClickListner mListner;
    GestureDetector mGestureDetector;

    public interface OnItemClickListner {
        public void onItemclick(View view, int position);
    }

    public Main_Adapter(List<Main_Pojo> list, Context ctx) {
        this.list = list;
        this.ctx = ctx;
        //this.mListner=mListner;
        mGestureDetector = new GestureDetector(ctx, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }

    public void setOnItemClickListner(OnItemClickListner onclick) {
        this.mListner = onclick;

    }

    @Override
    public viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home, parent, false);

        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(viewholder holder, int position) {
        Main_Pojo pojo = list.get(position);
        Picasso.with(ctx).load(pojo.getImg()).fit().into(holder.img);
        holder.name.setText(pojo.getName());
        holder.rel.setBackgroundColor(Color.parseColor(pojo.getColor_code()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    public class viewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView img;
        TextView name;
        RelativeLayout rel;

        public viewholder(View itemView) {
            super(itemView);
            rel = (RelativeLayout) itemView.findViewById(R.id.rel);
            img = (ImageView) itemView.findViewById(R.id.img);
            name = (TextView) itemView.findViewById(R.id.personal_info_txt);
            rel.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListner.onItemclick(v, getPosition());
        }
    }
}
