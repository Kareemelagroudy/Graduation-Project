package com.example.graduationproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.*;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestAdapter {
    protected static final String TAG = "DataAdapter";

    private final Context mContext;
    private SQLiteDatabase mDb;
    private DatabaseHelper mDbHelper;
    int listenflag= SelectScreen.flag;


    public TestAdapter(Context context) {
        this.mContext = context;
        mDbHelper = new DatabaseHelper(mContext);
    }

    public TestAdapter createDatabase() throws SQLException {
        try {
            mDbHelper.createDataBase();
        } catch ( IOException mIOException) {
            Log.e(TAG, mIOException.toString() + "  UnableToCreateDatabase");
            throw new Error("UnableToCreateDatabase");
        }
        return this;
    }

    public TestAdapter open() throws SQLException {
        try {
            mDbHelper.openDataBase();
            //mDbHelper.close();
            mDb = mDbHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            Log.e(TAG, "open >>"+ mSQLException.toString());
            throw mSQLException;
        }
        return this;
    }

    public void close() {
        mDbHelper.close();
    }

    public List<String> getscores()
    {
        List<String> scores = new ArrayList<>();
        String sql = "select Score from User Order By Timestamp DESC Limit 5";
        Cursor cursor = mDb.rawQuery(sql,null);
        if (cursor.moveToFirst()) {
            do {
                scores.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        return scores;
    }

    public List<String> getScoreTime()
    {
        List<String> time = new ArrayList<>();
        String sql = "select Timestamp from User Order By Timestamp DESC Limit 5";
        Cursor cursor = mDb.rawQuery(sql,null);
        if (cursor.moveToFirst()) {
            do {
                time.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        return time;
    }

    public List<String> getallwords()
    {
        List<String> words = new ArrayList<>();

        String sql = "Select Name From Words";
        Cursor cursor = mDb.rawQuery(sql, null);
        words.add("");
        if (cursor.moveToFirst()) {
            do {
                words.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        words.add("Add new word");
        return words;
    }

    public List<String> getallcategories()
    {
        List<String> categories = new ArrayList<>();

        String sql = "Select Category From Categories";
        Cursor cursor = mDb.rawQuery(sql, null);
        categories.add("");
        if (cursor.moveToFirst()) {
            do {
                categories.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        categories.add("Add new category");
        return categories;
    }
    public int getScore()
    {
        try {
            String sql="Select Score From User Order By Timestamp DESC";
            Cursor mCur = mDb.rawQuery(sql, null);
            int score=0;
            if (mCur != null) {
                mCur.moveToNext();
                score=mCur.getInt(0);
            }
            return score;
        } catch (SQLException mSQLException) {
            Log.e(TAG, "getTestData >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }

    public void insertScore(int score)
    {
        String sql = "Insert Into User (Score) Values ("+ score+")";
        mDb.execSQL(sql);
    }

    public void insertImage(ContentValues cv)
    {
        mDb.insert("Images",null,cv);
    }
    public void insertSound(ContentValues cv)
    {
        mDb.insert("Sound",null,cv);
    }
    public void insertSFX(ContentValues cv)
    {
        mDb.insert("SoundEffects",null,cv);
    }

    public void insertResponse(int id, String response)
    {
        String sql = "Insert Into Records (ID, Responses) Values ("+ id+", '"+response+"')";
        mDb.execSQL(sql);
    }


    public void insertword(String name, String category)
    {
        String sql = "Insert Into Words (Name, Category) Values ('"+ name+"', '" +category+"')";
        mDb.execSQL(sql);
    }

    public void insertCategory(String category)
    {
        String sql = "Insert Into Categories (Category) Values ('"+ category+"')";
        mDb.execSQL(sql);
    }

    public int getRowCount() {
        int ct = 0;
        if (listenflag == 1) {
            try {
                String sql = "Select Count(Name) from Words Where Category = 'Animals'";
                Cursor mCur = mDb.rawQuery(sql, null);
                if (mCur != null) {
                    mCur.moveToNext();
                    ct = mCur.getInt(0);
                }
            } catch (SQLException mSQLException) {
                Log.e(TAG, "getTestData >>" + mSQLException.toString());
                throw mSQLException;
            }
        }
        if (listenflag == 2) {
            try {
                String sql = "Select Count(Name) from Words Where Category = 'Vegetables'";
                Cursor mCur = mDb.rawQuery(sql, null);
                if (mCur != null) {
                    mCur.moveToNext();
                    ct = mCur.getInt(0);
                }
            } catch (SQLException mSQLException) {
                Log.e(TAG, "getTestData >>" + mSQLException.toString());
                throw mSQLException;
            }
        }
        if (listenflag == 3) {
            try {
                String sql = "Select Count(Name) from Words Where Category = 'Fruits'";
                Cursor mCur = mDb.rawQuery(sql, null);
                if (mCur != null) {
                    mCur.moveToNext();
                    ct = mCur.getInt(0);
                }
            } catch (SQLException mSQLException) {
                Log.e(TAG, "getTestData >>" + mSQLException.toString());
                throw mSQLException;
            }
        }
        if (listenflag == 4) {
            try {
                String sql = "Select Count(Name) from Words Where Category = 'Numbers'";
                Cursor mCur = mDb.rawQuery(sql, null);
                if (mCur != null) {
                    mCur.moveToNext();
                    ct = mCur.getInt(0);
                }
            } catch (SQLException mSQLException) {
                Log.e(TAG, "getTestData >>" + mSQLException.toString());
                throw mSQLException;
            }
        }
        if (listenflag == 5) {
            try {
                String sql = "Select Count(Name) from Words Where Category = 'Letters'";
                Cursor mCur = mDb.rawQuery(sql, null);
                if (mCur != null) {
                    mCur.moveToNext();
                    ct = mCur.getInt(0);
                }
            } catch (SQLException mSQLException) {
                Log.e(TAG, "getTestData >>" + mSQLException.toString());
                throw mSQLException;
            }
        }
        return ct;
    }

    public Bitmap ViewImage(int id) {
        try {
            String sql ="Select Images From Data Where ID = "+id;
            Cursor mCur = mDb.rawQuery(sql, null);
            Bitmap bmp = null;
            if (mCur != null) {
                mCur.moveToNext();
                byte[] image=mCur.getBlob(0);
                bmp= BitmapFactory.decodeByteArray(image,0,image.length);
            }
            return bmp;
        } catch (SQLException mSQLException) {
            Log.e(TAG, "getTestData >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }
    public Bitmap getImage(String name) {
        Cursor mCur;
        try {
            String sql ="select Images.Images FROM Words INNER JOIN Images WHERE Images.Name = Words.Name AND Images.Name = '" +name+ "' Order by Random()";
            mCur = mDb.rawQuery(sql, null);
            Bitmap bmp = null;
            if (mCur != null) {
                mCur.moveToFirst();
                byte[] image=mCur.getBlob(0);
                bmp= BitmapFactory.decodeByteArray(image,0,image.length);
            }
            return bmp;
        } catch (SQLException mSQLException) {
            Log.e(TAG, "getTestData >>"+ mSQLException.toString());
            throw mSQLException;
        }

    }

    public Cursor getCursorName() {
        Cursor mCur=null;
        if (listenflag == 1) {
            try
            {
                String sql = "select Words.Name From Categories INNER JOIN Words where Words.Category = Categories.Category AND Words.Category = '" +"Animals" +"'";
                mCur = mDb.rawQuery(sql, null);
                    if (mCur != null) {
                        mCur.moveToFirst();
                    }
            }
            catch (SQLException mSQLException)
            {
                Log.e(TAG, "getTestData >>" + mSQLException.toString());
                throw mSQLException;
            }
        }
        if (listenflag == 2) {
            try
            {
                String sql = "select Words.Name From Categories INNER JOIN Words where Words.Category = Categories.Category AND Words.Category = '" +"Vegetables" +"'";
                mCur = mDb.rawQuery(sql, null);
                if (mCur != null) {
                    mCur.moveToFirst();
                }
            }
            catch (SQLException mSQLException)
            {
                Log.e(TAG, "getTestData >>" + mSQLException.toString());
                throw mSQLException;
            }
        }
        if (listenflag == 3) {
            try
            {
                String sql = "select Words.Name From Categories INNER JOIN Words where Words.Category = Categories.Category AND Words.Category = '" +"Fruits" +"'";
                mCur = mDb.rawQuery(sql, null);
                if (mCur != null) {
                    mCur.moveToFirst();
                }
            }
            catch (SQLException mSQLException)
            {
                Log.e(TAG, "getTestData >>" + mSQLException.toString());
                throw mSQLException;
            }
        }
        if (listenflag == 4) {
            try
            {
                String sql = "select Words.Name From Categories INNER JOIN Words where Words.Category = Categories.Category AND Words.Category = '" +"Numbers" +"'";
                mCur = mDb.rawQuery(sql, null);
                if (mCur != null) {
                    mCur.moveToFirst();
                }
            }
            catch (SQLException mSQLException)
            {
                Log.e(TAG, "getTestData >>" + mSQLException.toString());
                throw mSQLException;
            }
        }
        if (listenflag == 5) {
            try
            {
                String sql = "select Words.Name From Categories INNER JOIN Words where Words.Category = Categories.Category AND Words.Category = '" +"Letters" +"'";
                mCur = mDb.rawQuery(sql, null);
                if (mCur != null) {
                    mCur.moveToFirst();
                }
            }
            catch (SQLException mSQLException)
            {
                Log.e(TAG, "getTestData >>" + mSQLException.toString());
                throw mSQLException;
            }
        }
        return mCur;
    }


    public String getQuizText(String name) {
        String text = null;
            try
            {
                String sql = "select Name From Words Where Not Name = '" +name +"' Order by Random()";
                Cursor mCur = mDb.rawQuery(sql, null);
                if (mCur != null)
                {
                    mCur.moveToFirst();
                    text = mCur.getString(0);
                }

            } catch (SQLException mSQLException)
            {
                Log.e(TAG, "getTestData >>" + mSQLException.toString());
                throw mSQLException;
            }
        return text;
    }
    public MediaPlayer getSound(String name) throws IOException {
        try {
            String sql = "Select Sound.Sound from Words INNER JOIN Sound WHERE Words.Name = Sound.Name and Sound.Name = '" +name+"' Order by Random()";
            Cursor mCur = mDb.rawQuery(sql, null);
            File file = null;
            FileOutputStream fos;
            MediaPlayer mp;
            if (mCur != null) {
                mCur.moveToNext();
                byte[] sound = mCur.getBlob(0);
                file = File.createTempFile("sound", "sound");
                fos = new FileOutputStream(file);
                fos.write(sound);
                fos.close();
            }
            mp = MediaPlayer.create(mContext, Uri.fromFile(file));
            return mp;
        } catch (SQLException mSQLException) {
            Log.e(TAG, "getTestData >>" + mSQLException.toString());
            throw mSQLException;
        }
    }
    public MediaPlayer getEffects(String name) throws IOException {
        try {
            String sql = "Select SoundEffects.SoundEffects from Words INNER JOIN SoundEffects WHERE Words.Name = SoundEffects.Name And SoundEffects.Name = '"+name+"' Order by Random()" ;
            Cursor mCur = mDb.rawQuery(sql, null);
            File file = null;
            FileOutputStream fos;
            MediaPlayer mp;
            if (mCur != null) {
                mCur.moveToNext();
                byte[] sound = mCur.getBlob(0);
                file = File.createTempFile("sound", "sound");
                fos = new FileOutputStream(file);
                fos.write(sound);
                fos.close();
            }
            mp = MediaPlayer.create(mContext, Uri.fromFile(file));
            return mp;
        } catch (SQLException mSQLException) {
            Log.e(TAG, "getTestData >>" + mSQLException.toString());
            throw mSQLException;
        }
    }
    public MediaPlayer geteffects(int id) throws IOException {
        try {
            String sql = "Select Sound from Data where ID="+id;
            Cursor mCur = mDb.rawQuery(sql, null);
            File file = null;
            FileOutputStream fos;
            MediaPlayer mp;
            if (mCur != null) {
                mCur.moveToNext();
                byte[] sound = mCur.getBlob(0);
                file = File.createTempFile("sound", "sound");
                fos = new FileOutputStream(file);
                fos.write(sound);
                fos.close();
            }
            mp = MediaPlayer.create(mContext, Uri.fromFile(file));
            return mp;
        } catch (SQLException mSQLException) {
            Log.e(TAG, "getTestData >>" + mSQLException.toString());
            throw mSQLException;
        }
    }
}
