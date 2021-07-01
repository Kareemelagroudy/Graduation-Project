package com.example.graduationproject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AddPage extends AppCompatActivity {
    EditText edtwrd, edtcat;
    TestAdapter db;
    Button img, sound, sfx;
    String editword, editcategory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_page);
        edtwrd=(EditText)findViewById(R.id.edtword);
        edtcat=(EditText)findViewById(R.id.edtcat);
        img=(Button)findViewById(R.id.img);
        sound=(Button)findViewById(R.id.sound);
        sfx=(Button)findViewById(R.id.sfx);
    }
    public void AddWord (View view)
    {
        editword=edtwrd.getText().toString();
        editcategory=edtcat.getText().toString();
        db=new TestAdapter(this);
        db.createDatabase();
        db.open();
        db.insertword(editword, editcategory);
        db.close();
        Toast.makeText(getApplicationContext(),"Data is added successfully",Toast.LENGTH_LONG).show();
    }
}
