package com.example.plantor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "Plants.db";
    public static final String TABLE_NAME = "myPlants";
    public static final String COL_PLANTS_NO = "plants_no";
    public static final String COL_PLANTS_NAME = "plants_name";
    public static final String COL_PLANTS_DATE = "plants_date";
    public static final String COL_PLANTSWATER_REPEAT = "plantswater_repeat";
    public static final String COL_PLANTSWATER_TIMES = "plantswater_times";
    public static final String COL_PLANTSFERTILIZER_REPEAT = "plantsfertilizer_repeat";
    public static final String COL_PLANTSFERTILIZER_TIMES = "plantsfertilizer_times";
    public static final String COL_PLANTS_LOCATION = "plants_location";
    public static final String COL_PLANTS_DISCRIPTION = "plants_discription";
    public static final String COL_PLANTS_PIC = "plants_pic";
    public static final String COL_WATERPROGRESS = "waterprogress";
    public static final String COL_FERTILIZERPROGRESS = "fertilezerprogress";
    public static final String COL_WATERSWITCH = "waterswitch";
    public static final String COL_FERTILIZERSWITCH = "fertilezerswitch";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +"(plants_no INTEGER PRIMARY KEY AUTOINCREMENT, plants_name TEXT, plants_date TEXT, plantswater_repeat TEXT, plantswater_times TEXT, plantsfertilizer_repeat TEXT, plantsfertilizer_times TEXT, plants_location TEXT, plants_discription TEXT, plants_pic BLOB, waterprogress INTEGER, fertilezerprogress INTEGER, waterswitch TEXT, fertilezerswitch TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop TABLE if exists " + TABLE_NAME);
    }

    public Boolean insertplants(String plants_name, String plants_date, String plantswater_repeat, String plantswater_times, String plantsfertilizer_repeat, String plantsfertilizer_times, String plants_location, String plants_discription, byte[] plants_pic, int waterprogress, int fertilezerprogress, String waterswitch, String fertilezerswitch){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_PLANTS_NAME, plants_name);
        contentValues.put(COL_PLANTS_DATE, plants_date);
        contentValues.put(COL_PLANTSWATER_REPEAT, plantswater_repeat);
        contentValues.put(COL_PLANTSWATER_TIMES, plantswater_times);
        contentValues.put(COL_PLANTSFERTILIZER_REPEAT, plantsfertilizer_repeat);
        contentValues.put(COL_PLANTSFERTILIZER_TIMES, plantsfertilizer_times);
        contentValues.put(COL_PLANTS_LOCATION, plants_location);
        contentValues.put(COL_PLANTS_DISCRIPTION, plants_discription);
        contentValues.put(COL_PLANTS_PIC, plants_pic);
        contentValues.put(COL_WATERPROGRESS, waterprogress);
        contentValues.put(COL_FERTILIZERPROGRESS, fertilezerprogress);
        contentValues.put(COL_WATERSWITCH, waterswitch);
        contentValues.put(COL_FERTILIZERSWITCH, fertilezerswitch);
        long result = db.insert(TABLE_NAME,null, contentValues);
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }

    public Boolean updatePlants(String plants_no, String plants_name, String plants_date, String plantswater_repeat, String plantswater_times, String plantsfertilizer_repeat, String plantsfertilizer_times, String plants_location, String plants_discription, byte[] plants_pic){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_PLANTS_NAME, plants_name);
        contentValues.put(COL_PLANTS_DATE, plants_date);
        contentValues.put(COL_PLANTSWATER_REPEAT, plantswater_repeat);
        contentValues.put(COL_PLANTSWATER_TIMES, plantswater_times);
        contentValues.put(COL_PLANTSFERTILIZER_REPEAT, plantsfertilizer_repeat);
        contentValues.put(COL_PLANTSFERTILIZER_TIMES, plantsfertilizer_times);
        contentValues.put(COL_PLANTS_LOCATION, plants_location);
        contentValues.put(COL_PLANTS_DISCRIPTION, plants_discription);
        contentValues.put(COL_PLANTS_PIC, plants_pic);
        db.update(TABLE_NAME, contentValues, "plants_no =?", new String[]{plants_no});
        return true;
    }

    public Boolean updatewaterprogress(String plants_no, int waterprogress){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_WATERPROGRESS, waterprogress);
        db.update(TABLE_NAME, contentValues, "plants_no =?", new String[]{plants_no});
        return true;
    }

    public Boolean updatefertilizerprogress(String plants_no, int fertilezerprogress){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_FERTILIZERPROGRESS, fertilezerprogress);
        db.update(TABLE_NAME, contentValues, "plants_no =?", new String[]{plants_no});
        return true;
    }

    public Boolean updatewaterswitch(String plants_no, String waterswitch){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_WATERSWITCH, waterswitch);
        db.update(TABLE_NAME, contentValues, "plants_no =?", new String[]{plants_no});
        return true;
    }

    public Boolean updatefertilizerswitch(String plants_no, String fertilizerswitch){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_FERTILIZERSWITCH, fertilizerswitch);
        db.update(TABLE_NAME, contentValues, "plants_no =?", new String[]{plants_no});
        return true;
    }

    public Cursor showPlants(){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        return cursor;
    }

    public ArrayList<Plant> getPlantsData(){

        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Plant> arrayList= new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        while(cursor.moveToNext()){

            int plants_no = cursor.getInt(0);
            String plants_name = cursor.getString(1);
            String plants_date = cursor.getString(2);
            String plantswater_repeat = cursor.getString(3);
            String plantswater_times = cursor.getString(4);
            String plantsfertilizer_repeat = cursor.getString(5);
            String plantsfertilizer_times = cursor.getString(6);
            String plants_location = cursor.getString(7);
            String plants_discription = cursor.getString(8);
            byte[] plants_pic = cursor.getBlob(9);
            int waterprogress = cursor.getInt(10);
            int fertilizerprogress = cursor.getInt(11);
            String waterswitch = cursor.getString(12);
            String fertilizerswitch = cursor.getString(13);
            Plant plants = new Plant(plants_no ,plants_name, plants_date, plantswater_repeat, plantswater_times, plantsfertilizer_repeat, plantsfertilizer_times, plants_location, plants_discription, plants_pic, waterprogress, fertilizerprogress, waterswitch, fertilizerswitch);
            arrayList.add(plants);

        }
        return arrayList;
    }

    public int deletePlants(String plants_no){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete( TABLE_NAME, "plants_no =?", new String[]{plants_no});
    }

}
