package com.example.bsja9.hereiam.Reminders;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by bsja9 on 23/03/2018.
 */

public class remindersDbHelper extends SQLiteOpenHelper {

    public remindersDbHelper(Context context) {
        super(context, reminders_db.DB_NAME, null, reminders_db.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + reminders_db.entry.TABLE + " ( " +
                reminders_db.entry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                reminders_db.entry.TASK_TITLE + " TEXT NOT NULL);";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + reminders_db.entry.TABLE);
        onCreate(db);
    }
}