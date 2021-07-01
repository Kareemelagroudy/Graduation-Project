package com.example.graduationproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class SelectScreen extends AppCompatActivity {
    ImageButton animals;
    ImageButton fruits;
    ImageButton vegetables;
    ImageButton numbers;
    ImageButton letters;
    TestAdapter db;
    static int flag=0;
    int flag1 = MainPage.flag;
    int flag2 = MinigameScreen.flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_screen);
        animals=(ImageButton)findViewById(R.id.animals);
        fruits=(ImageButton)findViewById(R.id.fruits);
        vegetables=(ImageButton)findViewById(R.id.vegetables);
        numbers=(ImageButton)findViewById(R.id.numbers);
        letters=(ImageButton)findViewById(R.id.letters);
        db=new TestAdapter(this);
        db.createDatabase();
        db.open();
        Bitmap bmp=db.ViewImage(2);
        animals.setImageBitmap(bmp);
        bmp=db.ViewImage(3);
        vegetables.setImageBitmap(bmp);
        bmp=db.ViewImage(4);
        fruits.setImageBitmap(bmp);
        bmp=db.ViewImage(5);
        numbers.setImageBitmap(bmp);
        bmp=db.ViewImage(6);
        letters.setImageBitmap(bmp);
    }
    public void animals(View view)
    {
        flag=1;
        if(flag1 == 0)
        {
            Intent intent =new Intent(SelectScreen.this, ListenScreen.class);
            startActivity(intent);
            finish();
        }
        if(flag1==1 && flag2 == 0)
        {
            Intent intent =new Intent(SelectScreen.this, SpeakScreen.class);
            startActivity(intent);
            finish();
        }
        if (flag1==1 && flag2 == 1)
        {
            Intent intent =new Intent(SelectScreen.this, QuizScreen.class);
            startActivity(intent);
            finish();
        }
    }
}