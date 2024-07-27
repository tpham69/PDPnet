package thaiph.ph48495.pdpnet.DBhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBhelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "running.db";
    private static final int DATABASE_VERSION = 1;

    public DBhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createStepsTable = "CREATE TABLE STEPS (" +
                "DATE TEXT PRIMARY KEY, " +
                "STEPS INTEGER)";
        db.execSQL(createStepsTable);

        String createStreakTable = "CREATE TABLE STREAK (" +
                "DATE TEXT PRIMARY KEY, " +
                "STREAK INTEGER)";
        db.execSQL(createStreakTable);

        String createUserTable = "CREATE TABLE USER (" +
                "EMAIL TEXT PRIMARY KEY, " +
                "STREAK INTEGER, " +
                "TODAY_DISTANCE REAL, " +
                "TOTAL_DISTANCE REAL)";
        db.execSQL(createUserTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS STEPS");
        db.execSQL("DROP TABLE IF EXISTS STREAK");
        db.execSQL("DROP TABLE IF EXISTS USER");
        onCreate(db);
    }
}