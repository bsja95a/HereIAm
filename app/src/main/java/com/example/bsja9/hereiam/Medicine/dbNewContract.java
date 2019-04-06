package com.example.bsja9.hereiam.Medicine;

import android.provider.BaseColumns;

/**
 * Created by bsja9 on 10/04/2018.
 */

public final class dbNewContract {
    private dbNewContract(){}

    public static class medicineEntry implements BaseColumns{
        public static final String TABLE_NAME = "medicineDb";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_DOSE = "dose";
        public static final String COLUMN_NAME_TIMES = "times";
        public static final String COLUMN_NAME_COMMENTS = "comments";
    }
}
