package com.example.a301.myapplication;

import android.util.Log;

import com.example.a301.myapplication.Controller.Constants;
import com.example.a301.myapplication.Data.LectureVO;
import com.example.a301.myapplication.Data.StudentVO;
import com.example.a301.myapplication.Model.Model_Lecture;
import com.example.a301.myapplication.Model.Model_Student;
import com.example.a301.myapplication.Retrofit.RetrofitService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 301 on 2017-09-30.
 */

public class DataManager {

    public ArrayList<Model_Student> tempList2;
    public StudentVO repoList2;

    public LectureVO repoList;
    public ArrayList<Model_Lecture> tempList;

    public void loadData()
    {
        Retrofit client = new Retrofit.Builder().baseUrl(Constants.req_URL).addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitService service = client.create(RetrofitService.class);

        Call<StudentVO> call = service.getStudentData();
        call.enqueue(new Callback<StudentVO>() {
            @Override
            public void onResponse(Call<StudentVO> call, Response<StudentVO> response) {
                if (response.isSuccessful()) {
                    repoList2 = response.body();
                    tempList2 = repoList2.getList();

                    for (int i = 0; i < tempList2.size(); i++) {
                        String studentNum = tempList2.get(i).getStudentNum();
                        String lecture1 = tempList2.get(i).getLecture1();
                        String lecture2 = tempList2.get(i).getLecture2();
                        String lecture3 = tempList2.get(i).getLecture3();
                        BaseActivity.studentList.add(new Model_Student(studentNum, lecture1, lecture2, lecture3));
                    }

                }
            }

            @Override
            public void onFailure(Call<StudentVO> call, Throwable t) {
                Log.v("fuck","fucking실패");
            }
        });

        Call<LectureVO> call2 = service.getLectureData();
        call2.enqueue(new Callback<LectureVO>() {
            @Override
            public void onResponse(Call<LectureVO> call, Response<LectureVO> response) {
                if (response.isSuccessful()) {
                    repoList = response.body();
                    tempList = repoList.getList();

                    for (int i = 0; i < tempList.size(); i++) {
                        String lecture = tempList.get(i).getLecture();
                        String lectureRoom = tempList.get(i).getLectureRoom();
                        String lectureStartTime = tempList.get(i).getLectureStartTime();
                        String lectureFinishTime = tempList.get(i).getLectureFinishTime();
                        String lectureDay = tempList.get(i).getLectureDay();
                        BaseActivity.lectureList.add(new Model_Lecture(lecture, lectureRoom, lectureStartTime, lectureFinishTime, lectureDay));
                    }
                }
            }

            @Override
            public void onFailure(Call<LectureVO> call, Throwable t) {
                Log.v("fuck","fucking실패");
            }
        });

    }
}
