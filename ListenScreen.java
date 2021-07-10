package com.example.graduationproject;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ListenScreen extends AppCompatActivity {
    ImageView lion;
    TestAdapter db;
    TextView textView;
    MediaPlayer mp,se;
    ImageButton next,replay,previous;
    int ct = 1, flag=SelectScreen.flag;
    int colcount=0;
    Cursor NameCursor;
    String str;
    Bitmap bmp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listen_screen);
        lion=(ImageView)findViewById(R.id.lion);
        textView=(TextView)findViewById(R.id.text);
        next=(ImageButton)findViewById(R.id.nextbut);
        previous=(ImageButton)findViewById(R.id.prevbut);
        replay=(ImageButton)findViewById(R.id.repbut);
        db=new TestAdapter(this);
        db.createDatabase();
        db.open();
        colcount=db.getRowCount();
        NameCursor=db.getCursorName();
        str=NameCursor.getString(0);
        textView.setText(str);
        bmp=db.getImage(str);
        lion.setImageBitmap(bmp);
        bmp = db.ViewImage(11);
        next.setImageBitmap(bmp);
        bmp = db.ViewImage(12);
        previous.setImageBitmap(bmp);
        bmp = db.ViewImage(13);
        replay.setImageBitmap(bmp);
        if(flag==1)
        {
            play();
        }
        if(flag==2 || flag==3 || flag==4 || flag ==5)
        {
            ply();
        }
    }
    public void replay(View view)
    {
        if(flag==1)
        {
            play();
        }
        if(flag==2 || flag==3 || flag==4 || flag ==5)
        {
            ply();
        }
    }
    public void next(View view) {
        mp.release();
        if(flag == 1)
        {se.release();}
        if (ct < colcount) {
            ct++;
            NameCursor.moveToNext();
            str = NameCursor.getString(0);
            textView.setText(str);
            bmp = db.getImage(str);
            lion.setImageBitmap(bmp);
            if(flag==1)
            {
                play();
            }
            if(flag==2 || flag==3 || flag==4 || flag ==5)
            {
                ply();
            }

        } else
        {
            Intent intent =new Intent(ListenScreen.this,MainPage.class);
            startActivity(intent);
            finish();
        }
    }
    public void previous(View view)
    {
        mp.release();
        if(flag == 1)
        {se.release();}
        lion.setImageBitmap(bmp);
        if(ct>1) {
            ct--;
            NameCursor.moveToPrevious();
            str=NameCursor.getString(0);
            textView.setText(str);
            bmp=db.getImage(str);
            lion.setImageBitmap(bmp);
            if(flag==1)
            {
                play();
            }
            if(flag==2 || flag==3 || flag==4 || flag ==5)
            {
                ply();
            }
        }
        else
        {
            Intent intent = new Intent (ListenScreen.this, SelectScreen.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(event.getAction()==KeyEvent.ACTION_DOWN)
        {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    mp.release();
                    se.release();
                    Intent intent = new Intent(ListenScreen.this,MainPage.class);
                    startActivity(intent);
                    finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public void play()
    {
        try {
            mp = db.getSound(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            se=db.getEffects(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
                se.start();
            }
        });
        se.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                se.release();
            }
        });
    }
    public void ply()
    {
        try {
            mp = db.getSound(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
    }
}
