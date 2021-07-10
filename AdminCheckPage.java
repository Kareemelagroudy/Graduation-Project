package com.example.graduationproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.Random;


public class AdminCheckPage extends AppCompatActivity {
    TextView txt;
    EditText edt;
    Button chck;
    int num1, num2, num3, result;
    Random random = new Random();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_check);
        txt = (TextView) findViewById(R.id.question);
        edt = (EditText) findViewById(R.id.adanswer);
        chck = (Button) findViewById(R.id.check);
        num1 = random.nextInt(10);
        num2 = random.nextInt(10);
        txt.setText(String.valueOf(num1) + "x" + String.valueOf(num2));
    }

    public void checkans(View view)
    {
        num3 = Integer.valueOf(edt.getText().toString());
        result = num1*num2;
        if(result == num3)
        {
            Intent intent = new Intent(AdminCheckPage.this,AddPage.class);
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
                    Intent intent = new Intent(AdminCheckPage.this,MainPage.class);
                    startActivity(intent);
                    finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
