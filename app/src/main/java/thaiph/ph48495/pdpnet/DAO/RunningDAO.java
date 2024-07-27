package thaiph.ph48495.pdpnet.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import thaiph.ph48495.pdpnet.DBhelper.DBhelper;

public class RunningDAO {

    private final DBhelper dBhelper;

    public RunningDAO(Context context){
        dBhelper = new DBhelper(context);
    }

    public void insertStepCount(int stepCount) {
        SQLiteDatabase db = dBhelper.getWritableDatabase();
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        ContentValues values = new ContentValues();
        values.put("DATE", currentDate);
        values.put("STEPS", stepCount);

        db.insertWithOnConflict("STEPS", null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public void updateStreak() {
        SQLiteDatabase db = dBhelper.getWritableDatabase();
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        Cursor cursor = db.query("STREAK", null, null, null, null, null, "DATE DESC", "1");
        if (cursor != null && cursor.moveToFirst()) {
            int dateIndex = cursor.getColumnIndex("DATE");
            int streakIndex = cursor.getColumnIndex("STREAK");

            if (dateIndex != -1 && streakIndex != -1) {
                String lastDate = cursor.getString(dateIndex);
                int currentStreak = cursor.getInt(streakIndex);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                try {
                    Date lastStreakDate = sdf.parse(lastDate);
                    Date today = sdf.parse(currentDate);

                    long diff = today.getTime() - lastStreakDate.getTime();
                    long daysDiff = diff / (1000 * 60 * 60 * 24);

                    if (daysDiff == 1) {
                        currentStreak++;
                    } else if (daysDiff > 1) {
                        currentStreak = 1;
                    }

                    ContentValues values = new ContentValues();
                    values.put("DATE", currentDate);
                    values.put("STREAK", currentStreak);

                    db.insertWithOnConflict("STREAK", null, values, SQLiteDatabase.CONFLICT_REPLACE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            ContentValues values = new ContentValues();
            values.put("DATE", currentDate);
            values.put("STREAK", 1);

            db.insertWithOnConflict("STREAK", null, values, SQLiteDatabase.CONFLICT_REPLACE);
        }

        if (cursor != null) {
            cursor.close();
        }
    }

    public int getStreak() {
        SQLiteDatabase db = dBhelper.getReadableDatabase();
        Cursor cursor = db.query("STREAK", null, null, null, null, null, "DATE DESC", "1");
        int streak = 0;
        if (cursor != null && cursor.moveToFirst()) {
            int streakIndex = cursor.getColumnIndex("STREAK");
            if (streakIndex != -1) {
                streak = cursor.getInt(streakIndex);
            }
            cursor.close();
        }
        return streak;
    }

    public float getTodayDistance() {
        SQLiteDatabase db = dBhelper.getReadableDatabase();
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        Cursor cursor = db.query("STEPS", new String[]{"STEPS"}, "DATE = ?", new String[]{currentDate}, null, null, null);
        float todayDistance = 0;
        if (cursor != null && cursor.moveToFirst()) {
            int stepsIndex = cursor.getColumnIndex("STEPS");
            if (stepsIndex != -1) {
                int steps = cursor.getInt(stepsIndex);
                todayDistance = steps * 0.0008f;
            }
            cursor.close();
        }
        return todayDistance;
    }

    public float getTotalDistance() {
        SQLiteDatabase db = dBhelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(STEPS) AS totalSteps FROM STEPS", null);
        float totalDistance = 0;
        if (cursor != null && cursor.moveToFirst()) {
            int totalStepsIndex = cursor.getColumnIndex("totalSteps");
            if(totalStepsIndex != -1){
                int totalSteps = cursor.getInt(totalStepsIndex);
                totalDistance  = totalSteps * 0.0008f;

            }
            cursor.close();
        }
        return totalDistance;
    }
}