package com.example.a301.myapplication.Controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a301.myapplication.Model.Model_Check;
import com.example.a301.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 301 on 2017-10-01.
 */

public class Adapter_Check extends  RecyclerView.Adapter<Adapter_Check.ViewHolder>{


    List<Model_Check> tempArr;
    Context adapterContext;


    public Adapter_Check(Context context, ArrayList<Model_Check> tempArr) {
        this.adapterContext = context;
        this.tempArr = tempArr;
    }

    @Override
    public Adapter_Check.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lecture, parent, false));
    }

    @Override
    public void onBindViewHolder(Adapter_Check.ViewHolder holder, int position) {

        holder.lectureTv.setText(tempArr.get(position).getLecture());
        holder.lectureRoomTv.setText(tempArr.get(position).getAttendanceDay());
        holder.lectureTimeTv.setText(tempArr.get(position).getAttendanceTime());
        holder.lectureDateTv.setText(tempArr.get(position).getAttendanceState());

    }

    @Override
    public int getItemCount() {
        return tempArr.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView lectureTv;
        public TextView lectureRoomTv;
        public TextView lectureTimeTv;
        public TextView lectureDateTv;



        public ViewHolder(View itemView){
            super(itemView);
            Log.v("AdapterTest","viewHolder클래스");

            lectureTv= (TextView) itemView.findViewById(R.id.lecture_TextView);
            lectureRoomTv=(TextView)itemView.findViewById(R.id.lectureRoom_TextView);
            lectureDateTv=(TextView)itemView.findViewById(R.id.lectureDate_TextView);
            lectureTimeTv=(TextView)itemView.findViewById(R.id.lectureTime_TextView);

        }

    }
}
