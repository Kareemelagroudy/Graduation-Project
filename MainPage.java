package com.example.graduationproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainPage extends AppCompatActivity {

    ImageButton play, minigame, admin;
    TestAdapter db;
    static int flag = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        play = (ImageButton) findViewById(R.id.play);
        minigame=(ImageButton) findViewById(R.id.play2);
        admin = (ImageButton) findViewById(R.id.admin);
        db=new TestAdapter(this);
        db.createDatabase();
        db.open();
        Bitmap bmp=db.ViewImage(1);
        play.setImageBitmap(bmp);
        bmp=db.ViewImage(9);
        minigame.setImageBitmap(bmp);
        bmp = db.ViewImage(10);
        admin.setImageBitmap(bmp);
    }

    public void start(View view) {
        flag = 0;
        Intent intent = new Intent(MainPage.this, SelectScreen.class);
        startActivity(intent);
        finish();
    }

    public void minigame(View view)
    {
        flag = 1;
        Intent intent = new Intent(MainPage.this, MinigameScreen.class);
        startActivity(intent);
        finish();
    }

    public void admin(View view)
    {
        Intent intent = new Intent(MainPage.this, AdminCheckPage.class);
        startActivity(intent);
        finish();
    }

}
