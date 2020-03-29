package com.jay.musicpro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.database.sqlite.SQLiteDatabase.CONFLICT_REPLACE;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "MUSIC_PRO.DB";

    private static final int DB_VERSION = 1;

    public static final String TABLE_NAME = "VENUES";

    public static final String ID = "_id";
    public static final String NAME = "name";
    public static final String ADDRESS = "address";
    public static final String OPENING_TIME = "opening_time";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " TEXT , " + ADDRESS + " TEXT, " + OPENING_TIME + " TEXT);";

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public int addOrUpdateVenue(Venue venue) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValue = new ContentValues();
        if (venue.getId() > 0)
            contentValue.put(DatabaseHelper.ID, venue.getId());
        contentValue.put(DatabaseHelper.NAME, venue.getName());
        contentValue.put(DatabaseHelper.ADDRESS, venue.getAddress());
        contentValue.put(DatabaseHelper.OPENING_TIME, venue.getOpeningTime());
        int id = (int) db.insertWithOnConflict(DatabaseHelper.TABLE_NAME, null, contentValue, CONFLICT_REPLACE);
        db.close();
        return id;
    }

    public Cursor fetchAllVenues() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME, null);
    }

    public Venue fetchVenueById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{ID, NAME, ADDRESS, OPENING_TIME}, ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        Venue venue = null;
        if (cursor != null) {
            cursor.moveToFirst();
            venue = new Venue(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
            cursor.close();
        }
        db.close();
        return venue;
    }

    public void delete(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper.ID + "=" + id, null);
        db.close();
    }
}