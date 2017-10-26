package com.example.a301.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.a301.myapplication.Controller.BottomNavigationViewHelper;

public class NotificationActivity extends AppCompatActivity {

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
                    Intent intent2 = new Intent(getApplicationContext(),ScheduleActivity.class);
                    startActivity(intent2);
                    return true;
                case R.id.navigation_notification:
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_notification);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    protected void onResume() {
        super.onResume();

        navigation.setSelectedItemId(R.id.navigation_notification);

    }

}
