package com.example.graduationproject;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.database.sqlite.SQLiteProgram;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class AddPage extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText edtwrd, edtcat;
    Spinner spincat, spinwrd;
    TestAdapter db;
    Button img, sound, sfx, data;
    String editword, editcategory;
    ImageView tst;
    public static final int PICK_IMAGE = 1;
    public static final int PICK_SOUND = 2;
    public static final int PICK_SFX = 3;
    Bitmap bmp;
    ContentValues cvim = new ContentValues();
    ContentValues cvsund = new ContentValues();
    ContentValues cvsfx = new ContentValues();
    byte[] btimg,btsund,btsfx;
    int flagword, flagimg, flagsound, flagsfx, flagcategory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_page);
        edtwrd=(EditText) findViewById(R.id.edtwrd);
        edtcat=(EditText) findViewById(R.id.edtcat);
        spinwrd = (Spinner) findViewById(R.id.spinword);
        spincat = (Spinner) findViewById(R.id.spincat);
        spincat.setOnItemSelectedListener(this);
        spinwrd.setOnItemSelectedListener(this);
        data = (Button) findViewById(R.id.data);
        img = (Button) findViewById(R.id.img);
        sound = (Button) findViewById(R.id.sound);
        sfx = (Button) findViewById(R.id.sfx);
        tst=(ImageView)findViewById(R.id.tst);
        db = new TestAdapter(this);
        db.createDatabase();
        db.open();
        loadSpinnerData();
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void AddImage(View view) {
        int permissionCheck = ContextCompat.checkSelfPermission(AddPage.this,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Intent gallery =
                    new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            startActivityForResult(gallery, PICK_IMAGE);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 23);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void AddSound(View view)
    {
        int permissionCheck = ContextCompat.checkSelfPermission(AddPage.this,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permissionCheck == PackageManager.PERMISSION_GRANTED)
        {
            Intent mediaplayer = new Intent(Intent.ACTION_PICK,MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(mediaplayer, PICK_SOUND);
        }
        else
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 23);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void AddSFX(View view)
    {
        int permissionCheck = ContextCompat.checkSelfPermission(AddPage.this,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permissionCheck == PackageManager.PERMISSION_GRANTED)
        {
            Intent mediaplayer = new Intent(Intent.ACTION_PICK,MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(mediaplayer, PICK_SFX);
        }
        else
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 23);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            Uri imageUri = data.getData();
            try {
                bmp = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            tst.setImageBitmap(bmp);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG,100,bos);
            btimg=bos.toByteArray();
        }
        if(btimg == null)
        {
            flagimg = 0;
        }
        else {
            flagimg = 1;
        }
        if (resultCode == RESULT_OK && requestCode == PICK_SOUND)
        {
            Uri SoundUri = data.getData();
            InputStream is = null;
            try {
                is = getContentResolver().openInputStream(SoundUri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                btsund=readByte(is);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(btsund == null)
        {
            flagsound = 0;
        }
        else
        {
            flagsound = 1;
        }
        if (resultCode == RESULT_OK && requestCode == PICK_SFX)
        {
            Uri SfxUri = data.getData();
            InputStream is = null;
            try {
                is = getContentResolver().openInputStream(SfxUri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                btsfx=readByte(is);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(btsfx == null)
        {
            flagsfx = 0;
        }
        else
        {
            flagsfx = 1;
        }
    }

    public byte[] readByte(InputStream inputStream) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] buffer= new byte[0xFFFF];
        for (int len = inputStream.read(buffer); len != -1; len = inputStream.read(buffer)) {
            os.write(buffer, 0, len);
        }
        return os.toByteArray();
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getId()==R.id.spinword)
        {
            editword=parent.getItemAtPosition(position).toString();
            if(editword.equals("Add new word"))
            {
                flagword=1;
                spinwrd.setVisibility(spinwrd.INVISIBLE);
                edtwrd.setVisibility(edtwrd.VISIBLE);
            }
            else
            {
                flagword = 0;
            }
        }

        if(parent.getId() == R.id.spincat) {
            editcategory = parent.getItemAtPosition(position).toString();

            if (editcategory.equals("Add new category")) {
                flagcategory = 1;
                spincat.setVisibility(spincat.INVISIBLE);
                edtcat.setVisibility(edtcat.VISIBLE);
            }
            else
            {
                flagcategory = 0;
            }
        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void AddWord(View view) {

        if(flagword==1)
        {
            editword = edtwrd.getText().toString();
            db.insertword(editword, editcategory);
        }

        if (flagcategory == 1)
        {
            editcategory = edtcat.getText().toString();
            db.insertCategory(editcategory);
        }
        cvim.put("Images", btimg);
        cvim.put("Name", editword);
        cvim.put("Category", editcategory);

        cvsund.put("Sound",btsund);
        cvsund.put("Name",editword);
        cvsund.put("Category",editcategory);

        cvsfx.put("SoundEffects",btsfx);
        cvsfx.put("Name",editword);
        cvsfx.put("Category",editcategory);

        if(flagsound == 1)
        {
            db.insertSound(cvsund);
        }
        if (flagsfx == 1)
        {
            db.insertSFX(cvsfx);
        }
        if(flagimg == 1)
        {
            db.insertImage(cvim);
        }
        Toast.makeText(getApplicationContext(), "Data is added successfully", Toast.LENGTH_LONG).show();
        Intent intent =new Intent(AddPage.this,MainPage.class);
        startActivity(intent);
        finish();
    }

    public void loadSpinnerData(){
        List<String> Word = db.getallwords();
        List<String> Categories = db.getallcategories();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, Word);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, Categories);
        adapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinwrd.setAdapter(dataAdapter);
        spincat.setAdapter(adapter);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(event.getAction()==KeyEvent.ACTION_DOWN)
        {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    Intent intent = new Intent(AddPage.this,MainPage.class);
                    startActivity(intent);
                    finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}