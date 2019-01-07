package edu.davenport.cisp340.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String DB_NAME = "Animal Tracker";
    private static final int DB_VERSION = 8;

    DBHelper db;

    public DBHelper(Context context){

        super(context, DB_NAME, null, DB_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sqlAnimal = "CREATE TABLE animal(id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR);";
        String sqlInfo = "CREATE TABLE info(id INTEGER PRIMARY KEY AUTOINCREMENT, animal_id INTEGER, count INTEGER," +
                "seen TEXT, comments VARCHAR, Loc_latitude REAL, Loc_longitude REAL, FOREIGN KEY(animal_id) REFERENCES animal(id))";
        //String  newSQlInfo = "SELECT animal_id, name FROM info INNER JOIN animal on animal.f = info.f";
        db.execSQL(sqlAnimal);
        db.execSQL(sqlInfo);
        //db.execSQL(newSQlInfo);

    }

    public  boolean addAnimal(String name){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        db.insert("animal", null, contentValues);
        db.close();
        return true;
    }
    public boolean addLocation(double lat, double log){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Loc_latitude", lat);
        contentValues.put("Loc_logitude", log);
        db.insert("info", null, contentValues);
        db.close();
        return true;
    }
    public boolean addData(String item,String count, String seenon, String comments) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("animal_id", item);
        contentValues.put("count", count);
        contentValues.put("seen", seenon);
        contentValues.put("comments", comments);

        Log.d(TAG, "addData: Adding " + item + " to " + "info");

        long result = db.insert("info", null, contentValues);

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + "info";
        Cursor data = db.rawQuery(query, null);
        return data;
    }


    public Cursor getItemID(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + "id" + " FROM " + "info" +
                " WHERE " + "animal_id" + " = '" + name + "'";
        Cursor data = db.rawQuery(query, null);

        return data;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sqlAnimal = "DROP TABLE IF EXISTS animal";
        String sqlInfo = "DROP TABLE IF EXISTS info";

        db.execSQL(sqlInfo);
        db.execSQL(sqlAnimal);

        onCreate(db);
    }

    public void updateName(String newName, int id, String oldName){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + "info" + " SET " + "animal_id" +
                " = '" + newName + "' WHERE " + "id" + " = '" + id + "'" +
                " AND " + "animal_id" + " = '" + oldName + "'";
        Log.d(TAG, "updateName: query: " + query);
        Log.d(TAG, "updateName: Setting name to " + newName);
        db.execSQL(query);
    }
    public  void updateCount(String newCount, int id, String oldCount){
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "UPDATE " + "info" + " SET " + "count" +
                " = '" + newCount + "' WHERE " + "id" + " = '" + id + "'" +
                " AND " + "count" + " = '" + oldCount + "'";
        Log.d(TAG, "updateInfo: query: " + query);
        Log.d(TAG, "updateInfor: Setting info to " + newCount );
        db.execSQL(query);
    }
    public  void updateSeenon(String newSeenon, int id, String oldseenon){
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "UPDATE " + "info" + " SET " + "seen" +
                " = '" + newSeenon + "' WHERE " + "id" + " = '" + id + "'" +
                " AND " + "seen" + " = '" + oldseenon + "'";
        Log.d(TAG, "updateInfo: query: " + query);
        Log.d(TAG, "updateInfor: Setting info to " + newSeenon );
        db.execSQL(query);
    }
    public  void updateComments(String newComments, int id, String oldComments){
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "UPDATE " + "info" + " SET " + "comments" +
                " = '" + newComments + "' WHERE " + "id" + " = '" + id + "'" +
                " AND " + "comments" + " = '" + oldComments + "'";
        Log.d(TAG, "updateInfo: query: " + query);
        Log.d(TAG, "updateInfor: Setting info to " + newComments );
        db.execSQL(query);
    }

    /**
     * Delete from database
     * @param id
     * @param name
     */
    public void deleteName(int id, String name, String count, String seenon, String comments){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + "info" + " WHERE " + "id"+ " = '" + id + "'" + " AND "
                + "animal_id" + " = '" + name + "'" + " AND "
                + "count" + " = '" + count + "'" + " AND "
                + "seen" + " = '" + seenon + "'" + " AND "
                + "comments" + " = '" + comments + "'";
        Log.d(TAG, "deleteName: query: " + query);
        Log.d(TAG, "deleteName: Deleting " + name + " from database.");
        db.execSQL(query);
    }

}
