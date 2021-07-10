package com.example.graduationproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

public class MinigameScreen extends AppCompatActivity {
    ImageButton speak, quiz;
    TestAdapter db;
    static int flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.minigame_select_screen);
        speak=(ImageButton)findViewById(R.id.speak);
        quiz=(ImageButton)findViewById(R.id.quiz);
        db=new TestAdapter(this);
        db.createDatabase();
        db.open();
        Bitmap bmp=db.ViewImage(14);
        speak.setImageBitmap(bmp);
        bmp=db.ViewImage(15);
        quiz.setImageBitmap(bmp);
    }
    public void speak(View view)
    {
        flag = 0;
        Intent intent = new Intent(MinigameScreen.this, SelectScreen.class);
        startActivity(intent);
    }
    public void quiz(View view)
    {
        flag = 1;
        Intent intent = new Intent(MinigameScreen.this, SelectScreen.class);
        startActivity(intent);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(event.getAction()==KeyEvent.ACTION_DOWN)
        {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    Intent intent = new Intent(MinigameScreen.this,MainPage.class);
                    startActivity(intent);
                    finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
