package com.example.a301.myapplication.Controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a301.myapplication.Model.Model_Lecture;
import com.example.a301.myapplication.R;

import java.util.List;

/**
 * Created by 301 on 2017-09-29.
 */

public class Adapter_Lecture extends RecyclerView.Adapter<Adapter_Lecture.ViewHolder>{

    List<Model_Lecture> tempArr;
    Context adapterContext;

    public Adapter_Lecture(Context context, List<Model_Lecture> tempArr) {
        this.adapterContext = context;
        this.tempArr = tempArr;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lecture, parent, false));
    }

    @Override
    public void onBindViewHolder(Adapter_Lecture.ViewHolder holder, int position) {

        holder.lectureTv.setText(tempArr.get(position).getLecture());
        holder.lectureRoomTv.setText(tempArr.get(position).getLectureRoom());
        holder.lectureTimeTv.setText(tempArr.get(position).getLectureStartTime()+"~"+tempArr.get(position).getLectureFinishTime());
        holder.lectureDateTv.setText(tempArr.get(position).getLectureDay());

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
