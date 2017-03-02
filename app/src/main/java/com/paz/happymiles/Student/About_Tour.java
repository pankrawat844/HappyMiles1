package com.paz.happymiles.Student;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kennyc.bottomsheet.BottomSheet;
import com.paz.happymiles.Adapter.FoldingCellListAdapter;
import com.paz.happymiles.Adapter.Home_Adapter;
import com.paz.happymiles.R;
import com.paz.happymiles.Student_Pojo.About_tour_pojo;
import com.ramotion.foldingcell.FoldingCell;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 2/27/2017.
 */

public class About_Tour extends Fragment  {
    RecyclerView recyclerView;
    Home_Adapter home_adapter;
    List<About_tour_pojo> list= new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.about_tour,container,false);
        recyclerView= (RecyclerView) view.findViewById(R.id.recycleview);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        home_adapter = new Home_Adapter(list,getActivity());
        recyclerView.setAdapter(home_adapter);

            recyclerView.addOnItemTouchListener(new Home_Adapter(getActivity(), new Home_Adapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    //((FoldingCell) view).toggle(false);
                //adapter.registerToggle(position);
                    TextView text= (TextView) view.findViewById(R.id.days);
                    new BottomSheet.Builder(getActivity(),R.style.Material_App_Dialog).setView(R.layout.dialog_data).setTitle(text.getText().toString()).setMessage("After arriving at the Kloten airport of Zurich, board a train to Interlaken. It is a beautiful village between the lakes of Thun and Brienz, two major attractions of the region. In the evening, you have an option of having a sumptuous dinner aboard a cruise around the Brienz Lake. Stay overnight in Interlaken.").setPositiveButton("Submit").show();
                }
            }));

        static_data();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
     //   init(view);

    }

//    private void init(View view){
//
//         recyclerView= (RecyclerView)view.findViewById(R.id.recycleview);
//        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
//        recyclerView.setLayoutManager(layoutManager);
//        home_adapter = new Home_Adapter(list,getActivity());
//        recyclerView.setAdapter(home_adapter);
//        static_data();
//    }

    private void static_data(){
    About_tour_pojo pojo= new About_tour_pojo("Day-1",R.drawable.day1,"");
        list.add(pojo);
        About_tour_pojo pojo1= new About_tour_pojo("Day-2",R.drawable.day2,"");
        list.add(pojo1);
        About_tour_pojo pojo2= new About_tour_pojo("Day-3",R.drawable.day3,"");
        list.add(pojo2);
        About_tour_pojo pojo3= new About_tour_pojo("Day-4",R.drawable.day4,"");
        list.add(pojo3);
        About_tour_pojo pojo4= new About_tour_pojo("Day-5",R.drawable.day5,"");
        list.add(pojo4);
        home_adapter.notifyDataSetChanged();
    }
}
