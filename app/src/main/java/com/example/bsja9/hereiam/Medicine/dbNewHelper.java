package com.example.bsja9.hereiam.Medicine;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by bsja9 on 10/04/2018.
 */

public class dbNewHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "medicine.db";
    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_CREATE = "CREATE TABLE "+

            dbNewContract.medicineEntry.TABLE_NAME +"(" +
            dbNewContract.medicineEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"+
            dbNewContract.medicineEntry.COLUMN_NAME_NAME + " TEXT, "+
            dbNewContract.medicineEntry.COLUMN_NAME_DOSE + " TEXT,"+
            dbNewContract.medicineEntry.COLUMN_NAME_TIMES + " TEXT,"+
            dbNewContract.medicineEntry.COLUMN_NAME_COMMENTS + " TEXT)";

    public dbNewHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + dbNewContract.medicineEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
