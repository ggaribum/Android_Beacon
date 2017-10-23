package com.example.a301.myapplication;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.example.a301.myapplication.Model.Model_Lecture;
import com.example.a301.myapplication.Model.Model_Student;

import java.util.ArrayList;


public class BaseActivity extends AppCompatActivity {


    public static ArrayList<Model_Lecture> lectureList;
    public static ArrayList<Model_Student> studentList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);


        lectureList = new ArrayList<>();
        studentList= new ArrayList<>();

        //Test_DataManager에 있는 loadData메서드 실행 -학생정보,강의정보 로드
        DataManager dataManager = new DataManager();
        dataManager.loadData();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);

    }

}
