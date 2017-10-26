package com.example.a301.myapplication.Retrofit;

import com.example.a301.myapplication.Data.CheckVO;
import com.example.a301.myapplication.Data.LectureVO;
import com.example.a301.myapplication.Data.StudentVO;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by 301 on 2017-09-29.
 */

public interface RetrofitService {
    @GET("/class")
    Call<LectureVO> getLectureData();
    @GET("/student")
    Call<StudentVO> getStudentData();
    @GET("/allCheck")
    Call<CheckVO> getCheckData();
}
