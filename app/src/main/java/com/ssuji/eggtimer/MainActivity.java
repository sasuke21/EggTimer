package com.ssuji.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    SeekBar seekBar;
    Button button;
    TextView textView;
    MediaPlayer mediaPlayer;
    CountDownTimer countDownTimer;

    boolean isTimerActive = false;

    int max = 900, idle = 30, timerValue = 0;

    public void setSeekBar() {

        seekBar.setMax(max);
        seekBar.setProgress(idle);
        setText(idle);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                setText(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void setText (int time) {

        int minutes = (int) TimeUnit.SECONDS.toMinutes(time);
        int seconds = time - (int) TimeUnit.MINUTES.toSeconds(minutes);

        String timeText = String.format("%02d", minutes % 60) + ":" + String.format("%02d", seconds % 60);
        textView.setText(timeText);
    }

    public void startStop (View view) {

        if (isTimerActive) {
            button.setText("Start");
            isTimerActive = false;
            seekBar.setEnabled(true);
            countDownTimer.cancel();
            seekBar.setProgress(idle);
            setText(idle);
        } else {
            isTimerActive = true;
            mediaPlayer = MediaPlayer.create(this, R.raw.horn);

            countDownTimer = new CountDownTimer(seekBar.getProgress() * 1000 + 100, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    setText((int) millisUntilFinished / 1000);
                }
                @Override
                public void onFinish() {
                    mediaPlayer.start();
                    seekBar.setEnabled(true);
                    isTimerActive = false;
                    button.setText("Start");
                }
            };
            button.setText("Stop!");
            seekBar.setEnabled(false);
            countDownTimer.start();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
        button = (Button) findViewById(R.id.button);
        seekBar = (SeekBar) findViewById(R.id.seekBar);

        setSeekBar();
    }
}
