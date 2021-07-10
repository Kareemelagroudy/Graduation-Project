package com.example.graduationproject;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

@RequiresApi(api = Build.VERSION_CODES.N)
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
        txt.setText("You got: " + score);
        new CountDownTimer(5000,1000){
            @Override
            public void onTick(long millisUntilFinished)
            {
                ct++;
            }
            @Override
            public void onFinish() {
                ct=0;
                showNextPage();
            }
        }.start();
    }

    public void showNextPage()
    {
        Intent intent = new Intent(ScoreScreen.this,MainPage.class);
        startActivity(intent);
        finish();
    }

}
