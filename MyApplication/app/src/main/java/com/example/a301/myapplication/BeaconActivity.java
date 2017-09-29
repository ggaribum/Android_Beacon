package com.example.a301.myapplication;

import android.content.Context;
import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.SystemRequirementsChecker;
import com.example.a301.myapplication.Controller.Constants;
import com.example.a301.myapplication.Model.Model_Lecture;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class BeaconActivity extends AppCompatActivity {

    private BeaconManager beaconManager;
    public int soundSet=-1;
    private Region region;
    public ArrayList<Model_Lecture> tempList;
    public ArrayList<Model_Lecture> adapterList;
    TextView tvID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beacon);
        Log.d("TestING","비콘 액티비티 실행됨");
        beaconManager=new BeaconManager(this);

        tvID=(TextView)findViewById(R.id.tvID);


        //오늘 강의 리스트에 저장.
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









        beaconManager.setRangingListener(new BeaconManager.RangingListener(){
            Context context = getApplicationContext();
            AudioManager mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

            @Override
            public void onBeaconsDiscovered(Region region, List<Beacon> list) {
                tempList=new ArrayList<Model_Lecture>();
                if(!list.isEmpty()){
                    Beacon nearestBeacon=list.get(0);

                    Log.d("Airport","Nearest place: "+nearestBeacon.getRssi());
                    tvID.setText(nearestBeacon.getRssi()+""); //수신강도 나타내기

                    //301 기준 수신강도가 95보다 크면 실내 무음모드 전환
                    if(nearestBeacon.getRssi()>-93) {
                        String roomNum=nearestBeacon.getProximityUUID().toString();
                        int last=roomNum.length();
                        roomNum=roomNum.substring(last-3,last);
                        Log.v("Hello",roomNum);

                        for(int i=0;i<BaseActivity.lectureList.size();i++) {
                            //roomNum랑 같은 강의 리스트 가져오기
                            if(BaseActivity.lectureList.get(i).getLectureRoom().equals(roomNum))
                            {
                                String lecture = BaseActivity.lectureList.get(i).getLecture();
                                String lectureRoom = BaseActivity.lectureList.get(i).getLectureRoom();
                                String lectureStartTime = BaseActivity.lectureList.get(i).getLectureStartTime();
                                String lectureFinishTime = BaseActivity.lectureList.get(i).getLectureFinishTime();
                                String lectureDay = BaseActivity.lectureList.get(i).getLectureDay();
                                tempList.add(new Model_Lecture(lecture,lectureRoom,lectureStartTime,lectureFinishTime,lectureDay));
                            }
                        }
                        for(int i= 0;i<tempList.size();i++)
                        {
                            for(int j=0;j<adapterList.size();j++) {
                                //roomNum에서 열리는 강의중 오늘 듣는 강의랑 같은이름이 있다면
                                if(tempList.get(i).getLecture().equals(adapterList.get(j).getLecture()))
                                {
                                    try {
                                        String str=switchStringminus(tempList.get(i).getLectureStartTime());//수업시작-10분
                                        String str2=switchStringplus(tempList.get(i).getLectureStartTime());//수업시작+10분
                                        Log.v("START",str);
                                        Log.v("START2",str2);
                                        int startTime=Integer.parseInt(str);
                                        Log.d("what","is1"+startTime);
                                        int startTime2=Integer.parseInt(str2);
                                        Log.d("what","is2"+startTime2);

                                        //현재시간
                                        long now = System.currentTimeMillis();
                                        java.util.Date nowDate = new Date(now);
                                        SimpleDateFormat sdfNow = new SimpleDateFormat("HHmm");
                                        Calendar cal = Calendar.getInstance();
                                        cal.setTime(nowDate);
                                        String nowTime = sdfNow.format(cal.getTime());
                                        int nowIntTIme=Integer.parseInt(nowTime);
                                        Log.d("FINISH3",nowTime);
                                        Log.d("what","is3"+nowIntTIme);

                                        if(startTime < nowIntTIme && nowIntTIme<startTime2 )
                                        {
                                            Log.d("LEACTURE",tempList.get(i).getLecture());
                                            Log.d("GREAT","출석");
                                        }


                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }

                        if (mAudioManager.getRingerMode() == AudioManager.RINGER_MODE_NORMAL) //2
                        {
                            mAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                        }
                        else if(mAudioManager.getRingerMode()==AudioManager.RINGER_MODE_SILENT) //0
                        {
                            mAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                        }
                        else if(mAudioManager.getRingerMode()==AudioManager.RINGER_MODE_VIBRATE) //1
                        {
                            mAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                        }

                    }

                    else {
                        if(soundSet==0)
                        {
                            mAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                        }
                        else if(soundSet==1)
                        {
                            mAudioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                        }
                        else if(soundSet==2)
                        {
                            mAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                        }
                    }

                }
                else {
                    tvID.setText("연결이 없습니다");
                    soundSet=mAudioManager.getRingerMode();
                }


            }
        });
        region = new Region("ranged region", UUID.fromString("11111111-1111-1111-1111-111111111301"),4660,64001);

    }

    public String switchStringplus(String time) throws ParseException {
        String oldstring = time;
        Date date = new SimpleDateFormat("HHmm").parse(oldstring);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, 10);
        String formatDate = new SimpleDateFormat("HHmm").format(cal.getTime());
       return formatDate;
    }

    public String switchStringminus(String time) throws ParseException {
        String oldstring = time;
        Date date = new SimpleDateFormat("HHmm").parse(oldstring);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, -10);
        String formatDate = new SimpleDateFormat("HHmm").format(cal.getTime());
        return formatDate;
    }


    @Override
    protected void onResume() {
        super.onResume();
        SystemRequirementsChecker.checkWithDefaultDialogs(this);
        beaconManager.connect(new BeaconManager.ServiceReadyCallback(){

            @Override
            public void onServiceReady() {
                beaconManager.startRanging(region);
            }
        });
    }
    @Override
    protected void onPause(){
        super.onPause();
    }

}
