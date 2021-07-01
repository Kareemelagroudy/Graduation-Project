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

public class LoseScreen extends AppCompatActivity {
    ImageView img;
    MediaPlayer lose;
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
        Bitmap bmp=db.ViewImage(8);
        img.setImageBitmap(bmp);
        try {
            lose=db.geteffects(8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        lose.start();
        new CountDownTimer(4000,1000){
            @Override
            public void onTick(long millisUntilFinished)
            {
                ct++;
            }
            @Override
            public void onFinish() {
                Intent intent = new Intent(LoseScreen.this,ScoreScreen.class);
                startActivity(intent);
                finish();
            }
        }.start();
    }
}
