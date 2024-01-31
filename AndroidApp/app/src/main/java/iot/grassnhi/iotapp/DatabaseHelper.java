package iot.grassnhi.iotapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "IOT_DATABASE";
    private static final String TABLE_LIGHT_DATA = "LIGHT_DATA";
    private static final String TABLE_HUMIDITY_DATA = "HUMIDITY_DATA";
    private static final String TABLE_TEMPERATURE_DATA = "TEMPERATURE_DATA";
    private static final String TABLE_AI_CAMERA_DATA = "AI_CAMERA_DATA";
    private static final String COL_ID = "ID";
    private static final String COL_DATE_TIME = "DATE_TIME";
    private static final String COL_VALUE = "VALUE";
    private static final String COL_CONFIDENCE_SCORE = "CONFIDENCE_SCORE";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 2); // Increment version number if needed
    }

    public void resetAutoIncrement(String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + tableName + "'");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create light data table
        String createLightDataTable = "CREATE TABLE " + TABLE_LIGHT_DATA + "(" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_DATE_TIME + " TEXT, " +
                COL_VALUE + " REAL)";
        db.execSQL(createLightDataTable);
        Log.d("DatabaseHelper", "Light data table created");

        // Create humidity data table
        String createHumidityDataTable = "CREATE TABLE " + TABLE_HUMIDITY_DATA + "(" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_DATE_TIME + " TEXT, " +
                COL_VALUE + " REAL)";
        db.execSQL(createHumidityDataTable);
        Log.d("DatabaseHelper", "Humidity data table created");

        // Create temperature data table
        String createTemperatureDataTable = "CREATE TABLE " + TABLE_TEMPERATURE_DATA + "(" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_DATE_TIME + " TEXT, " +
                COL_VALUE + " REAL)";
        db.execSQL(createTemperatureDataTable);
        Log.d("DatabaseHelper", "Temperature data table created");

        // Create AI camera data table
        String createAICameraDataTable = "CREATE TABLE " + TABLE_AI_CAMERA_DATA + "(" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_DATE_TIME + " TEXT, " +
                COL_VALUE + " REAL, " +
                COL_CONFIDENCE_SCORE + " REAL)";
        db.execSQL(createAICameraDataTable);
        Log.d("DatabaseHelper", "AI camera data table created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LIGHT_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HUMIDITY_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEMPERATURE_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AI_CAMERA_DATA);
        onCreate(db);
        Log.d("DatabaseHelper", "Tables upgraded from version " + oldVersion + " to " + newVersion);
    }

    // Insert light data
    public boolean insertLightData(double value) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_DATE_TIME, getDateTime());
        values.put(COL_VALUE, value);
        long result = db.insert(TABLE_LIGHT_DATA, null, values);
        Log.d("DatabaseHelper", "Light data inserted");
        return result != -1;
    }

    // Insert humidity data
    public boolean insertHumidityData(double value) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_DATE_TIME, getDateTime());
        values.put(COL_VALUE, value);
        long result = db.insert(TABLE_HUMIDITY_DATA, null, values);
        Log.d("DatabaseHelper", "Humidity data inserted");
        return result != -1;
    }

    // Insert temperature data
    public boolean insertTemperatureData(double value) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_DATE_TIME, getDateTime());
        values.put(COL_VALUE, value);
        long result = db.insert(TABLE_TEMPERATURE_DATA, null, values);
        Log.d("DatabaseHelper", "Temperature data inserted");
        return result != -1;
    }

    // Insert AI camera data
    public boolean insertAICameraData(double value, double confidenceScore) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_DATE_TIME, getDateTime());
        values.put(COL_VALUE, value);
        values.put(COL_CONFIDENCE_SCORE, confidenceScore);
        long result = db.insert(TABLE_AI_CAMERA_DATA, null, values);
        Log.d("DatabaseHelper", "AI camera data inserted");
        return result != -1;
    }

    // Get all light data
    public Cursor getAllLightData() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_LIGHT_DATA, null);
    }

    // Get all humidity data
    public Cursor getAllHumidityData() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_HUMIDITY_DATA, null);
    }

    // Get all temperature data
    public Cursor getAllTemperatureData() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_TEMPERATURE_DATA, null);
    }

    // Get all AI camera data
    public Cursor getAllAICameraData() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_AI_CAMERA_DATA, null);
    }

    // Delete light data by ID
    public Integer deleteLightData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_LIGHT_DATA, "ID=?", new String[]{id});
    }



    // Delete humidity data by ID
    public Integer deleteHumidityData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_HUMIDITY_DATA, "ID=?", new String[]{id});
    }



    // Delete temperature data by ID
    public Integer deleteTemperatureData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_TEMPERATURE_DATA, "ID=?", new String[]{id});
    }



    // Delete AI camera data by ID
    public Integer deleteAICameraData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_AI_CAMERA_DATA, "ID=?", new String[]{id});
    }



    // Get latest light data
    public Cursor getLatestLightData() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_LIGHT_DATA + " ORDER BY " + COL_DATE_TIME + " DESC LIMIT 1", null);
    }

    // Get latest humidity data
    public Cursor getLatestHumidityData() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_HUMIDITY_DATA + " ORDER BY " + COL_DATE_TIME + " DESC LIMIT 1", null);
    }

    // Get latest temperature data
    public Cursor getLatestTemperatureData() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_TEMPERATURE_DATA + " ORDER BY " + COL_DATE_TIME + " DESC LIMIT 1", null);
    }

    // Get latest AI camera data
    public Cursor getLatestAICameraData() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_AI_CAMERA_DATA + " ORDER BY " + COL_DATE_TIME + " DESC LIMIT 1", null);
    }

    // Delete all light data and reset auto-increment
    public boolean deleteAllLightData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LIGHT_DATA, null, null);
        resetAutoIncrement(TABLE_LIGHT_DATA);
        return true;
    }

    // Delete all humidity data and reset auto-increment
    public boolean deleteAllHumidityData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_HUMIDITY_DATA, null, null);
        resetAutoIncrement(TABLE_HUMIDITY_DATA);
        return true;
    }

    // Delete all temperature data and reset auto-increment
    public boolean deleteAllTemperatureData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TEMPERATURE_DATA, null, null);
        resetAutoIncrement(TABLE_TEMPERATURE_DATA);
        return true;
    }

    // Delete all AI camera data and reset auto-increment
    public boolean deleteAllAICameraData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_AI_CAMERA_DATA, null, null);
        resetAutoIncrement(TABLE_AI_CAMERA_DATA);
        return true;
    }



    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}