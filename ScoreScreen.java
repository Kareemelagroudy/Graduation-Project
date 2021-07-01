package com.example.graduationproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ScoreScreen extends AppCompatActivity {
    TextView txt;
    TestAdapter db;
    int score,ct=0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_screen);
        txt=(TextView)findViewById(R.id.scr);
        db=new TestAdapter(this);
        db.createDatabase();
        db.open();
        score=db.getScore();
        txt.setText(String.valueOf(score));
        new CountDownTimer(3000,1000){
            @Override
            public void onTick(long millisUntilFinished)
            {
                ct++;
            }
            @Override
            public void onFinish() {
                Intent intent = new Intent(ScoreScreen.this,MainPage.class);
                startActivity(intent);
                finish();
            }
        }.start();
    }
}
