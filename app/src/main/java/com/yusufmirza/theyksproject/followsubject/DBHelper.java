package com.yusufmirza.theyksproject.followsubject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "lesson.database";
    public static final String TABLE_NAME = "Lesson_table";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_NAME= "NAME";
    public static final String COLUMN_NOTE= "NOTE";
    public static final String COLUMN_BOX = "BOX";

    Context context;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " TEXT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_NOTE + " TEXT, "+
                COLUMN_BOX + " INTEGER );";

        db.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String SQL_DELETE = "DROP TABLE IF EXISTS " + TABLE_NAME;

        db.execSQL(SQL_DELETE);
        onCreate(db);
    }

    public void insertData(String id, String name, String note, int num){

        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID,id);
        contentValues.put(COLUMN_NAME,name);
        contentValues.put(COLUMN_NOTE,note);
        contentValues.put(COLUMN_BOX,num);

       long result = database.insert(TABLE_NAME,null,contentValues);




    }

    public void updateData(String id, String name, String note, int num){
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_NAME,name);
        contentValues.put(COLUMN_NOTE,note);
        contentValues.put(COLUMN_BOX,num);

        long result = database.update(TABLE_NAME,contentValues,"ID = ?",new String[]{id});



    }

    public Cursor findData(String name){
        SQLiteDatabase database = getWritableDatabase();

        String[] columns = {COLUMN_ID,COLUMN_NAME,COLUMN_NOTE,COLUMN_BOX};

        Cursor cursor = database.query(TABLE_NAME,columns,"NAME = ?",new String[]{name},null,null,null);

        return  cursor;
    }

    public Cursor readAllData(){
        SQLiteDatabase database = getWritableDatabase();

        String query= "SELECT * FROM "+TABLE_NAME;

        Cursor cursor = database.rawQuery(query,null);

        return cursor;
    }



}
