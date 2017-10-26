package com.example.a301.myapplication;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a301.myapplication.Model.Model_Student;

public class LoginActivity extends AppCompatActivity {

    EditText edt_id;
    EditText edt_pw;
    Button btn_login;

    String studentNum;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edt_id = (EditText) findViewById(R.id.edt_id);
        edt_pw = (EditText) findViewById(R.id.edt_pw);

/*
        Drawable d = getResources().getDrawable(R.drawable.actionbar);
        getSupportActionBar().setBackgroundDrawable(d);*/


        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean loginFlag = false;

                studentNum = edt_id.getText().toString();
                password=edt_pw.getText().toString();

                for(int i=0; i<BaseActivity.studentList.size(); i++){
                    if(studentNum.equals(BaseActivity.studentList.get(i).getStudentNum())
                            && password.equals(BaseActivity.studentList.get(i).getPassword()) ){ // 여기에 && 사용, 비밀번호 일치 여부도 구현
                        BaseActivity.currentStudent = studentNum;

                        loginFlag = true;
                        Intent intent = new Intent(getApplicationContext(), LoadingActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }


                if(!loginFlag) Toast.makeText(LoginActivity.this, "아이디 또는 비밀번호를 확인하세요.", Toast.LENGTH_SHORT).show();

            }
        });

    }
}
