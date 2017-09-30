package com.example.a301.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.example.a301.myapplication.Controller.Adapter_Check;
import com.example.a301.myapplication.Controller.Constants;
import com.example.a301.myapplication.Data.CheckVO;
import com.example.a301.myapplication.Model.Model_Check;
import com.example.a301.myapplication.Retrofit.RetrofitService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchActivity extends AppCompatActivity {

    public ArrayList<Model_Check> tempList;
    public ArrayList<Model_Check> showList;
    public CheckVO repoList;
    Intent getIntent;
    String lecture;
    String studentNm;

    RecyclerView checkRv;
    TextView checkTv;
    LinearLayoutManager manager;
    Adapter_Check adapter_check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        getIntent= new Intent(this.getIntent());
        lecture=getIntent.getStringExtra("lecture");
        studentNm=BaseActivity.studentList.get(0).getStudentNum();

        tempList=new ArrayList<>();
        showList=new ArrayList<>(); //위치 바꿔야할듯

        checkTv=(TextView)findViewById(R.id.check_TextView);
        checkRv =(RecyclerView)findViewById(R.id.checkRecycler);

        manager = new LinearLayoutManager(getApplicationContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        checkTv.setText(BaseActivity.studentList.get(0).getStudentNum());

        Retrofit client = new Retrofit.Builder().baseUrl(Constants.req_URL).addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitService service = client.create(RetrofitService.class);

        Call<CheckVO> call = service.getCheckData();
        call.enqueue(new Callback<CheckVO>() {
            @Override
            public void onResponse(Call<CheckVO> call, Response<CheckVO> response) {
               if(response.isSuccessful()) {
                   repoList = response.body();
                   tempList=repoList.getList();

                   for(int i=0; i< tempList.size();i++)
                   {
                       if(tempList.get(i).getStudentNum().equals(studentNm) && tempList.get(i).getLecture().equals(lecture))
                       {
                           String studentNum=tempList.get(i).getStudentNum();
                           String attendanceDay=tempList.get(i).getAttendanceDay();
                           String attendanceTime=tempList.get(i).getAttendanceTime();
                           String attendanceState=tempList.get(i).getAttendanceState();
                           String lecture=tempList.get(i).getLecture();

                           showList.add(new Model_Check(studentNum,attendanceDay,attendanceTime,attendanceState,lecture));

                       }
                   }
                   Log.v("로그",showList.get(0).getAttendanceState());

                   adapter_check = new Adapter_Check(getApplicationContext(),showList);
                   checkRv.setLayoutManager(manager);
                   checkRv.setAdapter(adapter_check);

               }



            }

            @Override
            public void onFailure(Call<CheckVO> call, Throwable t) {

            }
        });


    }
}
