package com.example.graduationproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class WinScreen extends AppCompatActivity {
    ImageView img;
    MediaPlayer win;
    TestAdapter db;
    int ct=0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.win_screen);
        img=(ImageView)findViewById(R.id.img);
        db=new TestAdapter(this);
        db.createDatabase();
        db.open();
        Bitmap bmp=db.ViewImage(7);
        img.setImageBitmap(bmp);
        try {
            win=db.geteffects(7);
        } catch (IOException e) {
            e.printStackTrace();
        }
        win.start();
        new CountDownTimer(3000,1000){
            @Override
            public void onTick(long millisUntilFinished)
            {
                ct++;
            }
            @Override
            public void onFinish() {
                Intent intent = new Intent(WinScreen.this,ScoreScreen.class);
                startActivity(intent);
                finish();
            }
        }.start();
    }
}
