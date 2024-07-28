package thaiph.ph48495.pdpnet.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import thaiph.ph48495.pdpnet.DBhelper.DBhelper;
import thaiph.ph48495.pdpnet.models.Running;

public class RunningDAO {

    private final DBhelper dBhelper;

    public RunningDAO(Context context) {
        dBhelper = new DBhelper(context);
    }

    public void insertRunning(Running running) {
        SQLiteDatabase db = dBhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("USER_ID", running.getUserID());
        values.put("DATE", running.getDate());
        values.put("STEP", running.getSteps());
        db.insert("STEPS", null, values);
        db.close();
    }

    public List<Running> getAllRunning() {
        List<Running> runningList = new ArrayList<>();
        SQLiteDatabase db = dBhelper.getReadableDatabase();
        Cursor cursor = db.query("STEPS", null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("ID"));
                int userId = cursor.getInt(cursor.getColumnIndexOrThrow("USER_ID"));
                String date = cursor.getString(cursor.getColumnIndexOrThrow("DATE"));
                int steps = cursor.getInt(cursor.getColumnIndexOrThrow("STEP"));
                runningList.add(new Running(id, userId, date, steps));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return runningList;
    }

    public Running getRunningById(int id) {
        SQLiteDatabase db = dBhelper.getReadableDatabase();
        Cursor cursor = db.query("STEPS", null, "ID=?", new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            int userId = cursor.getInt(cursor.getColumnIndexOrThrow("USER_ID"));
            String date = cursor.getString(cursor.getColumnIndexOrThrow("DATE"));
            int steps = cursor.getInt(cursor.getColumnIndexOrThrow("STEP"));
            cursor.close();
            db.close();
            return new Running(id, userId, date, steps);
        } else {
            db.close();
            return null;
        }
    }

    public void updateRunning(Running running) {
        SQLiteDatabase db = dBhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("USER_ID", running.getUserID());
        values.put("DATE", running.getDate());
        values.put("STEP", running.getSteps());
        db.update("STEPS", values, "ID=?", new String[]{String.valueOf(running.getId())});
        db.close();
    }

    public void deleteRunning(int id) {
        SQLiteDatabase db = dBhelper.getWritableDatabase();
        db.delete("STEPS", "ID=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public List<Running> getRunningByDate(String date) {
        List<Running> runningList = new ArrayList<>();
        SQLiteDatabase db = dBhelper.getReadableDatabase();
        Cursor cursor = db.query("STEPS", null, "DATE=?", new String[]{date}, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("ID"));
                int userId = cursor.getInt(cursor.getColumnIndexOrThrow("USER_ID"));
                int steps = cursor.getInt(cursor.getColumnIndexOrThrow("STEP"));
                runningList.add(new Running(id, userId, date, steps));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return runningList;
    }

    public int getStreakChayBo() {
        SQLiteDatabase db = dBhelper.getReadableDatabase();
        Cursor cursor = db.query("STEPS", new String[]{"DATE"}, null, null, null, null, "DATE DESC");

        int streak = 0;
        String previousDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        if (cursor.moveToFirst()) {
            do {
                String currentDate = cursor.getString(cursor.getColumnIndexOrThrow("DATE"));
                if (previousDate == null) {
                    streak++;
                } else {
                    try {
                        Date prevDate = sdf.parse(previousDate);
                        Date currDate = sdf.parse(currentDate);
                        long diff = prevDate.getTime() - currDate.getTime();
                        long daysDiff = diff / (1000 * 60 * 60 * 24);
                        if (daysDiff == 1) {
                            streak++;
                        } else {
                            break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                previousDate = currentDate;
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return streak;
    }
}