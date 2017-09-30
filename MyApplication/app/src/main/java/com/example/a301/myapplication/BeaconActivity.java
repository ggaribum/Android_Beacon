package com.example.a301.myapplication;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.SystemRequirementsChecker;
import com.example.a301.myapplication.Controller.Constants;
import com.example.a301.myapplication.Model.Model_Lecture;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class BeaconActivity extends AppCompatActivity {

    private BeaconManager beaconManager;
    public int soundSet = -1;
    private Region region;
    public ArrayList<Model_Lecture> tempList;
    public ArrayList<Model_Lecture> todayList;
    private boolean FLAG = false;
    private boolean silentFLAG=false;
    public String finishThisTime="2359";
    TextView tvID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beacon);
        Log.d("TestING", "!!!!!!!!!!!!!!!!!!!!!!비콘 액티비티 실행됨");

        Intent intent = new Intent(this.getIntent());
        final int soundSet1 = intent.getIntExtra("soundSet", 1);

        tvID = (TextView) findViewById(R.id.tvID);

        //오늘 강의 리스트에 저장.
        todayList = new ArrayList<>();
        todayList=MainActivity.adapterList;



        beaconManager = new BeaconManager(this);

        beaconManager.setRangingListener(new BeaconManager.RangingListener() {

            Context context = getApplicationContext();
            AudioManager mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

            @Override
            public void onBeaconsDiscovered(Region region, List<Beacon> list) {
                tempList = new ArrayList<Model_Lecture>();
                if (!list.isEmpty()) {
                    Beacon nearestBeacon = list.get(0);

                    Log.d("Beacon", "Nearest place: " + nearestBeacon.getRssi());
                    tvID.setText(nearestBeacon.getRssi() + ""); //수신강도 나타내기

                    //301 기준 수신강도가 95보다 크면 실내 무음모드 전환
                    if (nearestBeacon.getRssi() > -93) {

                        Log.d("THISFLAG","IS:  "+FLAG);

                        //현재시간을 구해서 nowIntTime 변수에 담기
                        String tempStr[]=switchNowTime().split(" ");
                        String nowDate=tempStr[0];
                        String nowTime=tempStr[1];
                        int nowIntTime = Integer.parseInt(nowTime);
                        int finishTisTime=Integer.parseInt(finishThisTime);

                        // 지금수업중이던 수업이 끝나고 나면 다시 수업 탐색, 원래 소리로 복귀
                        if(finishTisTime<=nowIntTime)
                        {
                            FLAG=false;
                            silentFLAG=false;
                            if (soundSet1 == 0) {
                                mAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                            } else if (soundSet1 == 1) {
                                mAudioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                            } else if (soundSet1 == 2) {
                                mAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                            }
                        }

                        //출결처리 된 상태 && 비콘구역이라면
                        if(silentFLAG)
                        {
                            setSilent(mAudioManager);
                        }


                        //수업 출석비교부분
                        if (!FLAG) {
                            Log.d("THISFLAG","in the IF IS:  "+FLAG);

                            String roomNum = nearestBeacon.getProximityUUID().toString();
                            int last = roomNum.length();
                            roomNum = roomNum.substring(last - 3, last);

                            Log.d("THIS roomNum","in the IF IS  "+FLAG);

                            for (int i = 0; i < BaseActivity.lectureList.size(); i++) {
                                //roomNum랑 같은 강의 리스트 가져오기
                                if (BaseActivity.lectureList.get(i).getLectureRoom().equals(roomNum)) {
                                    String lecture = BaseActivity.lectureList.get(i).getLecture();
                                    String lectureRoom = BaseActivity.lectureList.get(i).getLectureRoom();
                                    String lectureStartTime = BaseActivity.lectureList.get(i).getLectureStartTime();
                                    String lectureFinishTime = BaseActivity.lectureList.get(i).getLectureFinishTime();
                                    String lectureDay = BaseActivity.lectureList.get(i).getLectureDay();

                                    //roomNum에서 열리는 강의리스트를 tempList에 담기
                                    tempList.add(new Model_Lecture(lecture, lectureRoom, lectureStartTime, lectureFinishTime, lectureDay));
                                }
                            }

                            for (int i = 0; i < tempList.size(); i++) {
                                for (int j = 0; j < todayList.size(); j++) {
                                    //roomNum에서 열리는 강의중 오늘 듣는 강의랑 같은이름이 있다면
                                    if (tempList.get(i).getLecture().equals(todayList.get(j).getLecture())) {
                                        try {

                                            Log.v("Hi","여기는 됬다.");
                                            String str = switchStringminus(tempList.get(i).getLectureStartTime(),-1);//수업시작-10분
                                            String str2 = switchStringplus(tempList.get(i).getLectureStartTime(),+1);//수업시작+10분
                                            String str3 = switchStringplus(tempList.get(i).getLectureStartTime(),+3);//수업시작+30분

                                            int startTimeMinus10 = Integer.parseInt(str);
                                            int startTimePlus10 = Integer.parseInt(str2);
                                            int startTimePlus30 = Integer.parseInt(str3);
                                            int finishTime=Integer.parseInt(tempList.get(i).getLectureFinishTime());

                                            Log.d("This startTimeMinus10", "is  " + startTimeMinus10);
                                            Log.d("This startTimePlus10", "is  " + startTimePlus10);

                                            Log.d("This nowIntTime ", "is   " + nowIntTime);
                                            Log.d("This nowDate ", "is   " + nowDate);

                                            //만약 출결테이블에 본인 학번, 본인 수강과목이 출석||지각 이 아니라면

                                            // 수업시작-10분 < 현재시간 < 수업시작+10 인 경우 ==출석
                                            if (startTimeMinus10 < nowIntTime && nowIntTime < startTimePlus10) {
                                                postAttendance(nowTime,tempList.get(i).getLecture(),"출석",nowDate,mAudioManager,tempList.get(i).getLectureFinishTime());
                                            }
                                            // 수업시작+10 < 현재시간 < 수업시작 +30 인 경우 ==지각
                                            else if (startTimeMinus10 < nowIntTime && nowIntTime < startTimePlus30) {
                                                postAttendance(nowTime,tempList.get(i).getLecture(),"지각",nowDate,mAudioManager,tempList.get(i).getLectureFinishTime());
                                            }
                                            // 수업시작+30 < 현재시간 < 종료시간 ==결석
                                            else if (startTimePlus30 < nowIntTime && nowIntTime < finishTime) {
                                                postAttendance(nowTime,tempList.get(i).getLecture(),"결석",nowDate,mAudioManager,tempList.get(i).getLectureFinishTime());
                                            }

                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }

                    }
                    //비콘구역에 있다가 벗어났을 시
                    else {
                        silentFLAG=false;
                        if (soundSet == 0) {
                            mAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                        } else if (soundSet == 1) {
                            mAudioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                        } else if (soundSet == 2) {
                            mAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                        }
                    }

                }

                //연결이 없다면.
                else {
                    tvID.setText("연결이 없습니다");
                    soundSet = mAudioManager.getRingerMode();
                }
            }
        });
        region = new Region("ranged region", UUID.fromString("11111111-1111-1111-1111-111111111301"), 4660, 64001);

    }



    public String switchStringplus(String time,int plus) throws ParseException {
        String oldstring = time;
        Date date = new SimpleDateFormat("HHmm").parse(oldstring);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, plus);
        String formatDate = new SimpleDateFormat("HHmm").format(cal.getTime());
        return formatDate;
    }

    public String switchStringminus(String time, int minus) throws ParseException {
        String oldstring = time;
        Date date = new SimpleDateFormat("HHmm").parse(oldstring);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, minus);
        String formatDate = new SimpleDateFormat("HHmm").format(cal.getTime());
        return formatDate;
    }


    public String switchNowTime()
    {
        long now = System.currentTimeMillis();
        java.util.Date nowDate = new Date(now);
        SimpleDateFormat sdfNow = new SimpleDateFormat("MMdd HHmm");
        Calendar cal = Calendar.getInstance();
        cal.setTime(nowDate);
        String nowTime = sdfNow.format(cal.getTime());

        return nowTime;
    }


    public void postAttendance(String nowTime, String thisLecture, String state, String todayDate, final AudioManager thisAudioManager, final String str)
    {
        //POST 하는 부분. 서버-DB 쪽이랑 최대한 변수 맞춰서 정리하자.
        String studentNum = BaseActivity.studentList.get(0).getStudentNum();
        String attendanceTime = nowTime;
        String lecture = thisLecture;
        String attendanceState = state;
        String day=todayDate;
        Log.d("HI","포스트부분");

        AndroidNetworking.post("http://13.124.87.34:5000/check")
                .addBodyParameter("studentNum", studentNum)
                .addBodyParameter("attendanceTime", attendanceTime)
                .addBodyParameter("lecture", lecture)
                .addBodyParameter("attendanceState", attendanceState)
                .addBodyParameter("day", day)
                .addHeaders("Content-Type", "multipart/form-data")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        FLAG = true;
                        setSilent(thisAudioManager);
                        Log.d("지금시간확인",": "+str);
                        finishThisTime=str;
                        Log.d("지금시간확인2",": "+finishThisTime);
                    }

                    @Override
                    public void onError(ANError anError) {
                    }
                });
    }


    public void setSilent(AudioManager mAudioManager)
    {
        if (mAudioManager.getRingerMode() == AudioManager.RINGER_MODE_NORMAL) //2
        {
            mAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        } else if (mAudioManager.getRingerMode() == AudioManager.RINGER_MODE_SILENT) //0
        {
            mAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        } else if (mAudioManager.getRingerMode() == AudioManager.RINGER_MODE_VIBRATE) //1
        {
            mAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        SystemRequirementsChecker.checkWithDefaultDialogs(this);
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {

            @Override
            public void onServiceReady() {
                beaconManager.startRanging(region);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


}

