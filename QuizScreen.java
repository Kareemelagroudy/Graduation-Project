package com.example.graduationproject;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

@RequiresApi(api = Build.VERSION_CODES.N)
public class QuizScreen extends AppCompatActivity {
    ImageView img;
    RadioButton btn1,btn2,btn3;
    Button btn;
    MediaPlayer win;
    TestAdapter db;
    String str2, str3, str;
    static int ct=0, rnd;
    Cursor NameCursor;
    static int score=0;
    static int colcount = 0;
    Bitmap bmp;
    Random random = new Random();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_screen);
        btn1=(RadioButton)findViewById(R.id.answer1);
        btn2=(RadioButton)findViewById(R.id.answer2);
        btn3=(RadioButton)findViewById(R.id.answer3);
        btn=(Button)findViewById(R.id.go);
        img=(ImageView)findViewById(R.id.img);
        db=new TestAdapter(this);
        db.createDatabase();
        db.open();
        colcount=db.getRowCount();
        NameCursor=db.getCursorName();
        str=NameCursor.getString(0);
        bmp=db.getImage(str);
        img.setImageBitmap(bmp);
        str2=db.getQuizText(str);
        str3=db.getQuizText(str);
        Generate();
        try {
            win = db.geteffects(7);
        } catch (IOException e) {
            e.printStackTrace();
        }
        colcount = db.getRowCount();
        score = 0;
        ct++;
    }

    public void check(View view)
    {

        if(btn1.isChecked())
        {
            btn2.setChecked(false);
            btn3.setChecked(false);
            if(((String) btn1.getText()).equals(str)) {
                if (ct < colcount) {
                    score += 5;
                    db.insertResponse(ct, (String) btn1.getText());
                    win.start();
                    NameCursor.moveToNext();
                    str = NameCursor.getString(0);
                    bmp = db.getImage(str);
                    img.setImageBitmap(bmp);
                    str2 = db.getQuizText(str);
                    str3 = db.getQuizText(str);
                    ct++;
                } else {
                    score += 5;
                    db.insertResponse(ct, (String) btn1.getText());
                    db.insertScore(score);
                    Intent intent = new Intent(QuizScreen.this, WinScreen.class);
                    startActivity(intent);
                    finish();
                }
            }
            else
            {
                db.insertResponse(ct, (String) btn1.getText());
                db.insertScore(score);
                Intent intent=new Intent(QuizScreen.this,LoseScreen.class);
                startActivity(intent);
                finish();
            }
        }

        if(btn2.isChecked())
        {
            btn1.setChecked(false);
            btn3.setChecked(false);
            if(((String) btn2.getText()).equals(str)) {
                if (ct < colcount) {
                    score += 5;
                    db.insertResponse(ct, (String) btn2.getText());
                    win.start();
                    NameCursor.moveToNext();
                    str = NameCursor.getString(0);
                    bmp = db.getImage(str);
                    img.setImageBitmap(bmp);
                    str2 = db.getQuizText(str);
                    str3 = db.getQuizText(str);
                    ct++;
                } else {
                    score += 5;
                    db.insertResponse(ct, (String) btn2.getText());
                    db.insertScore(score);
                    Intent intent = new Intent(QuizScreen.this, WinScreen.class);
                    startActivity(intent);
                    finish();
                }
            }
            else
            {
                db.insertResponse(ct, (String) btn2.getText());
                db.insertScore(score);
                Intent intent=new Intent(QuizScreen.this,LoseScreen.class);
                startActivity(intent);
                finish();
            }
        }
        if(btn3.isChecked())
        {
            btn1.setChecked(false);
            btn2.setChecked(false);
            if(((String) btn3.getText()).equals(str)) {
                if (ct < colcount) {
                    score += 5;
                    db.insertResponse(ct, (String) btn3.getText());
                    win.start();
                    NameCursor.moveToNext();
                    str = NameCursor.getString(0);
                    bmp = db.getImage(str);
                    img.setImageBitmap(bmp);
                    str2 = db.getQuizText(str);
                    str3 = db.getQuizText(str);
                    ct++;
                } else {
                    score += 5;
                    db.insertResponse(ct, (String) btn3.getText());
                    db.insertScore(score);
                    Intent intent = new Intent(QuizScreen.this, WinScreen.class);
                    startActivity(intent);
                    finish();
                }
            }
            else
            {
                db.insertResponse(ct, (String) btn3.getText());
                db.insertScore(score);
                Intent intent=new Intent(QuizScreen.this,LoseScreen.class);
                startActivity(intent);
                finish();
            }
        }
        Generate();
        btn1.setChecked(false);
        btn2.setChecked(false);
        btn3.setChecked(false);
    }
    public void Generate()
    {
        rnd=random.nextInt(3);
        if(rnd == 0)
        {
            btn1.setText(str);
            btn2.setText(str2);
            btn3.setText(str3);
        }
        if(rnd == 1)
        {
            btn1.setText(str2);
            btn2.setText(str);
            btn3.setText(str3);
        }
        if(rnd == 2)
        {
            btn1.setText(str3);
            btn2.setText(str2);
            btn3.setText(str);
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(event.getAction()==KeyEvent.ACTION_DOWN)
        {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    Intent intent = new Intent(QuizScreen.this,MainPage.class);
                    startActivity(intent);
                    finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
