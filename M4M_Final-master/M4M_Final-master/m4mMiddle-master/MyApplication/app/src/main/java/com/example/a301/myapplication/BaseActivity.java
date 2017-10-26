package com.example.a301.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.example.a301.myapplication.Data.DataManager_Student;
import com.example.a301.myapplication.Model.Model_Lecture;
import com.example.a301.myapplication.Model.Model_Student;

import java.util.ArrayList;


public class BaseActivity extends AppCompatActivity {

    public static String currentStudent;

    public static ArrayList<Model_Lecture> lectureList;
    public static ArrayList<Model_Student> studentList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        lectureList = new ArrayList<>();
        studentList= new ArrayList<>();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                DataManager_Student dmS = new DataManager_Student();
                dmS.loadData(BaseActivity.this);

            }
        }, 2000);

    }

}
