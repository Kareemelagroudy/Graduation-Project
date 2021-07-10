package com.example.graduationproject;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.util.ArrayList;

public class SpeakScreen extends AppCompatActivity implements View.OnClickListener {
    ImageView lion;
    TestAdapter db;
    TextView textView;
    MediaPlayer mp;
    Button record;
    int colcount=0;
    Cursor NameCursor;
    String str, Comparedstr;
    int counter = 0;
    Bitmap bmp;
    private static final String TAG = "MyStt3Activity";
    private SpeechRecognizer sr;
    int mistake;
    EditDistanceProblem ed =new EditDistanceProblem();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.speak_screen);
        lion=(ImageView)findViewById(R.id.lion);
        textView=(TextView)findViewById(R.id.text);
        record = (Button) findViewById(R.id.recbut);
        record.setOnClickListener(this);
        db=new TestAdapter(this);
        db.createDatabase();
        db.open();
        colcount=db.getRowCount();
        NameCursor=db.getCursorName();
        Comparedstr=NameCursor.getString(0);
        bmp=db.getImage(Comparedstr);
        lion.setImageBitmap(bmp);

        try {
            mp=db.getSound(Comparedstr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mp.start();
    }

    public void onClick(View v) {
        int permissionCheck = ContextCompat.checkSelfPermission(SpeakScreen.this,
                Manifest.permission.RECORD_AUDIO);

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            // you have the permission, proceed to record audio

            sr = SpeechRecognizer.createSpeechRecognizer(this);
            sr.setRecognitionListener(new listener());

            Intent speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            speechIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, "voice.recognition.test");
            speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"ar-JO");
            sr.startListening(speechIntent);
            Log.i("SpeechActivity", "Speech Recognizer is listening");

        } else {
            ActivityCompat.requestPermissions(SpeakScreen.this,
                    new String[]{Manifest.permission.RECORD_AUDIO},
                    1);
        }
    }

    class listener implements RecognitionListener
    {
        public void onReadyForSpeech(Bundle params)
        {
            Log.d(TAG, "onReadyForSpeech");
        }
        public void onBeginningOfSpeech()
        {
            Log.d(TAG, "onBeginningOfSpeech");
        }
        public void onRmsChanged(float rmsdB)
        {
            Log.d(TAG, "onRmsChanged");
        }
        public void onBufferReceived(byte[] buffer)
        {
            Log.d(TAG, "onBufferReceived");
        }
        public void onEndOfSpeech()
        {
            Log.d(TAG, "onEndofSpeech");
        }
        public void onError(int error)
        {
            Log.d(TAG,  "error " +  error);
            textView.setText("error " + error);
        }
        public void onResults(Bundle results)
        {
            str = new String();
            Log.d(TAG, "onResults " + results);
            ArrayList data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            for (int i = 0; i < data.size(); i++)
            {
                Log.d(TAG, "result " + data.get(i));
                str += data.get(i);
            }


            if(str.equals(Comparedstr))
            {
                textView.setText(str);
                textView.setVisibility(textView.VISIBLE);
                new CountDownTimer(3000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        counter++;
                    }

                    @Override
                    public void onFinish() {
                        counter = 0;
                        finish();
                    }
                }.start();

            }
            if(!str.equals(Comparedstr))
            {
                textView.setVisibility(textView.VISIBLE);
                textView.setText("Oops");
                new CountDownTimer(2000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        counter++;
                    }

                    @Override
                    public void onFinish() {
                        counter = 0;
                        textView.setVisibility(textView.INVISIBLE);
                        ShowError();
                    }
                }.start();

                new CountDownTimer(6000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        counter++;
                    }

                    @Override
                    public void onFinish() {
                        counter = 0;
                        Evaluate();
                    }
                }.start();


            }
        }

        public void ShowError()
        {
            textView.setVisibility(textView.VISIBLE);
            mistake=ed.editDistanceDP(str,Comparedstr);
            textView.setText("Number of mistakes: "+mistake);

            try {
                mp=db.getSound(Comparedstr);
            } catch (IOException e) {
                e.printStackTrace();
            }
            mp.start();
        }
        public void finish(){
            NameCursor.moveToNext();
            Comparedstr=NameCursor.getString(0);
            bmp=db.getImage(Comparedstr);
            try {
                mp=db.getSound(Comparedstr);
            } catch (IOException e) {
                e.printStackTrace();
            }
            lion.setImageBitmap(bmp);
            textView.setVisibility(textView.INVISIBLE);
            mp.start();
        }

        public void Evaluate()
        {
            textView.setVisibility(textView.VISIBLE);
            int pos;
            String[] strArr = new String[Comparedstr.length()];
            String[] strAr = new String[str.length()];
            ArrayList<String> mistake = new ArrayList<>();
            for (int i = 0; i<Comparedstr.length(); i++) {
                strArr[i] = String.valueOf(Comparedstr.charAt(i));
                strAr[i] = String.valueOf(str.charAt(i));
            }

            for (int i = 0, j = 0; i<strArr.length && j<strAr.length; i++, j++) {
                if(!strArr[i].equals(strAr[j]))
                {
                    pos = j;
                    mistake.add(String.valueOf(str.charAt(pos)));
                }
            }
            textView.setText(mistake.toString());

        }
        public void onPartialResults(Bundle partialResults)
        {
            Log.d(TAG, "onPartialResults");
        }
        public void onEvent(int eventType, Bundle params)
        {
            Log.d(TAG, "onEvent " + eventType);
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(event.getAction()==KeyEvent.ACTION_DOWN)
        {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    Intent intent = new Intent(SpeakScreen.this,MainPage.class);
                    startActivity(intent);
                    finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
