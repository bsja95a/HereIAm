package com.example.bsja9.hereiam.Reminders;

import android.provider.BaseColumns;

/**
 * Created by bsja9 on 23/03/2018.
 */

public class reminders_db {

    public static final String DB_NAME = "reminders_DB";
    public static final int DB_VERSION = 1;

    public class entry implements BaseColumns{
        public static final String TABLE = "reminders";
        public static final String TASK_TITLE = "title";
    }
}
