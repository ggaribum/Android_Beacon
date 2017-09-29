package com.example.a301.myapplication;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.SystemRequirementsChecker;
import com.example.a301.myapplication.Controller.Adapter_Lecture;
import com.example.a301.myapplication.Controller.Constants;
import com.example.a301.myapplication.Model.Model_Lecture;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    RecyclerView rv_lecuture;
    LinearLayoutManager manager;
    Adapter_Lecture adapter_lecture;
    TextView todayTv;
    public ArrayList<Model_Lecture> adapterList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        DataManager dataManager = new DataManager();
        dataManager.loadData();

        todayTv=(TextView)findViewById(R.id.toay_TextView);

        adapterList = new ArrayList<>();

        for (int i = 0; i < BaseActivity.lectureList.size(); i++) {
            if (BaseActivity.studentList.get(0).getLecture1().equals(BaseActivity.lectureList.get(i).getLecture())
                    || BaseActivity.studentList.get(0).getLecture2().equals(BaseActivity.lectureList.get(i).getLecture())
                    || BaseActivity.studentList.get(0).getLecture3().equals(BaseActivity.lectureList.get(i).getLecture())
                    ) {
                //현재요일 구하기
                String today;
                long now = System.currentTimeMillis();
                Date date = new Date(now);
                SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

                Calendar cal = Calendar.getInstance();
                today = Constants.switchDAY(cal.get(Calendar.DAY_OF_WEEK));
                Log.v("TodayIs", today);
                if (BaseActivity.lectureList.get(i).getLectureDay().contains(today)) {
                    String lecture = BaseActivity.lectureList.get(i).getLecture();
                    String lectureRoom = BaseActivity.lectureList.get(i).getLectureRoom();
                    String lectureStartTime = BaseActivity.lectureList.get(i).getLectureStartTime();
                    String lectureFinishTime = BaseActivity.lectureList.get(i).getLectureFinishTime();
                    String lectureDay = BaseActivity.lectureList.get(i).getLectureDay();
                    adapterList.add(new Model_Lecture(lecture, lectureRoom, lectureStartTime, lectureFinishTime, lectureDay));
                }
            }
        }

        //이부분 에러각
        rv_lecuture = (RecyclerView) findViewById(R.id.lectureRecycler);
        manager = new LinearLayoutManager(getApplicationContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        adapter_lecture = new Adapter_Lecture(getApplicationContext(), adapterList);
        rv_lecuture.setLayoutManager(manager);
        rv_lecuture.setAdapter(adapter_lecture);


        try {
            test();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //비콘엑티비티 실행
        Intent intent = new Intent(getApplicationContext(),BeaconActivity.class);
        startActivity(intent);

    }


    void test() throws ParseException {

        //String to Date 메서드 !

        String oldstring = "1200";
        Date date = new SimpleDateFormat("HHmm").parse(oldstring);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, -10);
        String formatDate = new SimpleDateFormat("HHmm").format(cal.getTime());
        todayTv.setText(formatDate);

    }



}
