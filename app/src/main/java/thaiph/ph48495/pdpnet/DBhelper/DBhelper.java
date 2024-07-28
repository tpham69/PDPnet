package thaiph.ph48495.pdpnet.DBhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBhelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "PDPnet.db";
    private static final int DATABASE_VERSION = 10;

    public DBhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createStepsTable = "CREATE TABLE STEPS (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "USER_ID INTEGER," +
                "DATE TEXT, " +
                "STEP INTEGER," +
                "FOREIGN KEY (USER_ID) REFERENCES USER(ID))";
        db.execSQL(createStepsTable);

        String createUserTable = "CREATE TABLE USER (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "EMAIL TEXT, " +
                "STREAKBIETON INTEGER DEFAULT 0," +
                "STREAKCHATBO INTEGER DEFAULT 0," +
                "TODAY_DISTANCE REAL, " +
                "TOTAL_DISTANCE REAL)";
        db.execSQL(createUserTable);

        String createBietOnTable = "CREATE TABLE BietOn (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "USER_ID INTEGER," +
                "CONTENTS TEXT, " +
                "DATE TEXT, " +
                "FOREIGN KEY (USER_ID) REFERENCES USER(ID))";
        db.execSQL(createBietOnTable);


        //Fake data
        db.execSQL("INSERT INTO STEPS (USER_ID, DATE, STEP) VALUES (1, '2023-10-01', 5000)");
        db.execSQL("INSERT INTO STEPS (USER_ID, DATE, STEP) VALUES (2, '2023-10-02', 7000)");
        db.execSQL("INSERT INTO STEPS (USER_ID, DATE, STEP) VALUES (3, '2023-10-03', 8000)");
        db.execSQL("INSERT INTO STEPS (USER_ID, DATE, STEP) VALUES (4, '2023-10-04', 6000)");
        db.execSQL("INSERT INTO STEPS (USER_ID, DATE, STEP) VALUES (5, '2023-10-05', 7500)");
        db.execSQL("INSERT INTO STEPS (USER_ID, DATE, STEP) VALUES (6, '2023-10-06', 8500)");
        db.execSQL("INSERT INTO STEPS (USER_ID, DATE, STEP) VALUES (7, '2023-10-07', 4000)");
        db.execSQL("INSERT INTO STEPS (USER_ID, DATE, STEP) VALUES (8, '2023-10-08', 9000)");
        db.execSQL("INSERT INTO STEPS (USER_ID, DATE, STEP) VALUES (9, '2023-10-09', 10000)");
        db.execSQL("INSERT INTO STEPS (USER_ID, DATE, STEP) VALUES (10, '2023-10-10', 7000)");

        //Fake dataa
        db.execSQL("INSERT INTO BietOn (USER_ID, CONTENTS, DATE) VALUES (1, '\"Tôi biết ơn gia đình\", \"Tôi biết ơn bạn bè\", \"Tôi biết ơn sức khỏe\", \"Tôi biết ơn công việc\", \"Tôi biết ơn cuộc sống\"', '2023-10-01')");
        db.execSQL("INSERT INTO BietOn (USER_ID, CONTENTS, DATE) VALUES (2, '\"Tôi biết ơn thiên nhiên\", \"Tôi biết ơn giáo dục\", \"Tôi biết ơn công nghệ\", \"Tôi biết ơn âm nhạc\", \"Tôi biết ơn nghệ thuật\"', '2023-10-02')");
        db.execSQL("INSERT INTO BietOn (USER_ID, CONTENTS, DATE) VALUES (3, '\"Tôi biết ơn gia đình\", \"Tôi biết ơn bạn bè\", \"Tôi biết ơn sức khỏe\", \"Tôi biết ơn công việc\", \"Tôi biết ơn cuộc sống\"', '2023-10-03')");
        db.execSQL("INSERT INTO BietOn (USER_ID, CONTENTS, DATE) VALUES (4, '\"Tôi biết ơn sức khỏe\", \"Tôi biết ơn gia đình\", \"Tôi biết ơn bạn bè\", \"Tôi biết ơn công việc\", \"Tôi biết ơn cuộc sống\"', '2023-10-04')");
        db.execSQL("INSERT INTO BietOn (USER_ID, CONTENTS, DATE) VALUES (5, '\"Tôi biết ơn thiên nhiên\", \"Tôi biết ơn giáo dục\", \"Tôi biết ơn công nghệ\", \"Tôi biết ơn âm nhạc\", \"Tôi biết ơn nghệ thuật\"', '2023-10-05')");
        db.execSQL("INSERT INTO BietOn (USER_ID, CONTENTS, DATE) VALUES (6, '\"Tôi biết ơn sức khỏe\", \"Tôi biết ơn gia đình\", \"Tôi biết ơn bạn bè\", \"Tôi biết ơn công việc\", \"Tôi biết ơn cuộc sống\"', '2023-10-06')");
        db.execSQL("INSERT INTO BietOn (USER_ID, CONTENTS, DATE) VALUES (7, '\"Tôi biết ơn gia đình\", \"Tôi biết ơn bạn bè\", \"Tôi biết ơn sức khỏe\", \"Tôi biết ơn công việc\", \"Tôi biết ơn cuộc sống\"', '2023-10-07')");
        db.execSQL("INSERT INTO BietOn (USER_ID, CONTENTS, DATE) VALUES (8, '\"Tôi biết ơn thiên nhiên\", \"Tôi biết ơn giáo dục\", \"Tôi biết ơn công nghệ\", \"Tôi biết ơn âm nhạc\", \"Tôi biết ơn nghệ thuật\"', '2023-10-08')");
        db.execSQL("INSERT INTO BietOn (USER_ID, CONTENTS, DATE) VALUES (9, '\"Tôi biết ơn sức khỏe\", \"Tôi biết ơn gia đình\", \"Tôi biết ơn bạn bè\", \"Tôi biết ơn công việc\", \"Tôi biết ơn cuộc sống\"', '2023-10-09')");
        db.execSQL("INSERT INTO BietOn (USER_ID, CONTENTS, DATE) VALUES (10, '\"Tôi biết ơn thiên nhiên\", \"Tôi biết ơn giáo dục\", \"Tôi biết ơn công nghệ\", \"Tôi biết ơn âm nhạc\", \"Tôi biết ơn nghệ thuật\"', '2023-10-10')");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS STEPS");
        db.execSQL("DROP TABLE IF EXISTS STREAK");
        db.execSQL("DROP TABLE IF EXISTS USER");
        db.execSQL("DROP TABLE IF EXISTS BietOn");
        onCreate(db);
    }
}