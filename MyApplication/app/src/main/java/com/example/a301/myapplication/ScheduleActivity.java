package com.example.a301.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.a301.myapplication.Controller.Adapter_Lecture;
import com.example.a301.myapplication.Controller.Constants;
import com.example.a301.myapplication.Model.Model_Lecture;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ScheduleActivity extends AppCompatActivity {

    TextView scheduleTv;
    RecyclerView scheduleRv;

    LinearLayoutManager manager;
    Adapter_Lecture adapter_schedule;
    ArrayList<Model_Lecture> tempList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        tempList=new ArrayList<>();

        scheduleTv=(TextView)findViewById(R.id.schedule_TextView);
        scheduleRv=(RecyclerView)findViewById(R.id.scheduleRecycler);

        scheduleTv.setText(BaseActivity.studentList.get(0).getStudentNum());
        //위에 시간과 이름도 같이 붙여서 띄울거임

        manager = new LinearLayoutManager(getApplicationContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        for (int i = 0; i < BaseActivity.lectureList.size(); i++) {

            if (BaseActivity.studentList.get(0).getLecture1().equals(BaseActivity.lectureList.get(i).getLecture())
                    || BaseActivity.studentList.get(0).getLecture2().equals(BaseActivity.lectureList.get(i).getLecture())
                    || BaseActivity.studentList.get(0).getLecture3().equals(BaseActivity.lectureList.get(i).getLecture())
                    )
            {

                    String lecture = BaseActivity.lectureList.get(i).getLecture();
                    String lectureRoom = BaseActivity.lectureList.get(i).getLectureRoom();
                    String lectureStartTime = BaseActivity.lectureList.get(i).getLectureStartTime();
                    String lectureFinishTime = BaseActivity.lectureList.get(i).getLectureFinishTime();
                    String lectureDay = BaseActivity.lectureList.get(i).getLectureDay();
                    tempList.add(new Model_Lecture(lecture, lectureRoom, lectureStartTime, lectureFinishTime, lectureDay));

            }
        }

        adapter_schedule = new Adapter_Lecture(getApplicationContext(),tempList);
        scheduleRv.setLayoutManager(manager);
        scheduleRv.setAdapter(adapter_schedule);



    }
}
