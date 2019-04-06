package com.example.bsja9.hereiam.Medicine;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by bsja9 on 10/04/2018.
 */

public class MedicineDataSource {
    private SQLiteDatabase mDatabase;
    private final dbNewHelper mDBHelper;
    private final String[] MEDICINE_ALL_COLUMS = {
            dbNewContract.medicineEntry.COLUMN_NAME_ID,
            dbNewContract.medicineEntry.COLUMN_NAME_NAME,
            dbNewContract.medicineEntry.COLUMN_NAME_DOSE,
            dbNewContract.medicineEntry.COLUMN_NAME_TIMES,
            dbNewContract.medicineEntry.COLUMN_NAME_COMMENTS};

    public  void open(){
        mDatabase = mDBHelper.getWritableDatabase();
    }
    public void close(){mDBHelper.close();}

    public MedicineDataSource(Context context) {
        mDBHelper = new dbNewHelper(context);
    }

    public void save(dbNew medicine){
        ContentValues values = new ContentValues();
        values.put(dbNewContract.medicineEntry.COLUMN_NAME_NAME, medicine.getName());
        values.put(dbNewContract.medicineEntry.COLUMN_NAME_DOSE, medicine.getDose());
        values.put(dbNewContract.medicineEntry.COLUMN_NAME_TIMES, medicine.getTimesPerDay());
        values.put(dbNewContract.medicineEntry.COLUMN_NAME_COMMENTS, medicine.getComments());
        mDatabase.insert(dbNewContract.medicineEntry.TABLE_NAME,null,values);
        mDatabase.close();
    }
    public void update(int id, dbNew medicine){
        ContentValues values = new ContentValues();
        values.put(dbNewContract.medicineEntry.COLUMN_NAME_NAME, medicine.getName());
        values.put(dbNewContract.medicineEntry.COLUMN_NAME_DOSE, medicine.getDose());
        values.put(dbNewContract.medicineEntry.COLUMN_NAME_TIMES, medicine.getTimesPerDay());
        values.put(dbNewContract.medicineEntry.COLUMN_NAME_COMMENTS, medicine.getComments());

        mDatabase.update(dbNewContract.medicineEntry.TABLE_NAME,values,dbNewContract.medicineEntry.COLUMN_NAME_ID + "= ?",new String[]{String.valueOf(id)});
        mDatabase.close();
    }
    public Cursor findAll(){
        return mDatabase.query(dbNewContract.medicineEntry.TABLE_NAME,MEDICINE_ALL_COLUMS,null,null,null,null,null );
    }
    public void delete(int id){
        mDatabase.delete(dbNewContract.medicineEntry.TABLE_NAME,dbNewContract.medicineEntry.COLUMN_NAME_ID + " =?",
                new String[]{Integer.toString(id)});
    }
}
