package com.tenpa_mf.newtimer;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    FloatingActionButton floatingActionButton;
    Spinner spinner;
    MyCountDownTimer mTimer;
    SoundPool mSoundPool;
    int SoundResId;

    public class MyCountDownTimer extends CountDownTimer{
        public boolean isRunning = false;
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            long minute = millisUntilFinished/1000/60;
            long second = millisUntilFinished/1000%60;
            textView.setText(String.format("%1d:%2$02d",minute,second));
        }

        @Override
        public void onFinish() {
            textView.setText("0:00");
            mSoundPool.play(SoundResId,1.0f,1.0f,0,0,1.0f);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        textView = (TextView)findViewById(R.id.timeView);

        //スピナー実装
        spinner = (Spinner)findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //スピナーで項目選択されたとき
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner = (Spinner)parent;
                String item = (String)spinner.getSelectedItem();
                if(!item.isEmpty()){
                    textView.setText(item);
                    if(textView.getText().toString().equals("3:00")){
                        mTimer = new MyCountDownTimer(3*60*1000,100);
                    }else if(textView.getText().toString().equals("4:00")){
                        mTimer = new MyCountDownTimer(4*60*1000,100);
                    }else if(textView.getText().toString().equals("5:00")){
                        mTimer = new MyCountDownTimer(5*60*1000,100);
                    }else if(textView.getText().toString().equals("10:00")){
                        mTimer = new MyCountDownTimer(10*60*1000,100);
                    }else if(textView.getText().toString().equals("15:00")){
                        mTimer = new MyCountDownTimer(15*60*1000,100);
                    }else{
                        //何もない。
                    }

                }
            }
            //スピナーで項目選択されなかったとき。
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //ここにスピナーから得た時間情報をセット

        floatingActionButton = (FloatingActionButton)findViewById(R.id.play_stop);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mTimer.isRunning){
                    mTimer.isRunning = false;
                    mTimer.cancel();
                    floatingActionButton.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                }else{
                    mTimer.isRunning = true;
                    mTimer.start();
                    floatingActionButton.setImageResource(R.drawable.ic_stop_black_24dp);
                }
            }
        });
    }

    //アクティビティが画面に表示された場合だけ、呼び出される
    @Override
    public void onResume(){
        super.onResume();
        mSoundPool = new SoundPool(2, AudioManager.STREAM_ALARM,0);
        SoundResId = mSoundPool.load(this,R.raw.bellsound,1);
    }
    //アクティビティが非表示になった場合に呼ばれる。
    @Override
    public void onPause(){
        super.onPause();
        mSoundPool.release();
    }
}
