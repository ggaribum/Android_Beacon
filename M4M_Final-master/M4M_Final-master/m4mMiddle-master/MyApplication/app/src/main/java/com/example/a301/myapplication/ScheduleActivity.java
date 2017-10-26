package com.example.a301.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.a301.myapplication.Controller.Adapter_Lecture;
import com.example.a301.myapplication.Controller.BottomNavigationViewHelper;
import com.example.a301.myapplication.Controller.TimeManager;
import com.example.a301.myapplication.Model.Model_Lecture;

import java.util.ArrayList;

public class ScheduleActivity extends AppCompatActivity {

    TextView tv_data;
    TextView tv_date;

    RecyclerView scheduleRv;

    LinearLayoutManager manager;
    Adapter_Lecture adapter_schedule;
    ArrayList<Model_Lecture> tempList;

    private BottomNavigationView navigation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent intent1 = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent1);
                    return true;
                case R.id.navigation_schedule:
                    return true;
                case R.id.navigation_notification:
                    Intent intent2 = new Intent(getApplicationContext(),NotificationActivity.class);
                    startActivity(intent2);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        tempList=new ArrayList<>();

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_schedule);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        scheduleRv=(RecyclerView)findViewById(R.id.scheduleRecycler);

        tv_data = (TextView) findViewById(R.id.tv_data_sche);
        tv_date = (TextView) findViewById(R.id.tv_date_sche);

        tv_data.setText("[name]" + " (" + BaseActivity.currentStudent + ") ");
        tv_date.setText(new TimeManager().getCurrentDate());

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


    @Override
    protected void onResume() {
        super.onResume();

        navigation.setSelectedItemId(R.id.navigation_schedule);

    }

}
