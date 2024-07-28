package thaiph.ph48495.pdpnet.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import thaiph.ph48495.pdpnet.DBhelper.DBhelper;
import thaiph.ph48495.pdpnet.models.BietOn;

public class BietOnDAO {

    private final DBhelper dBhelper;

    public BietOnDAO(Context context) {
        dBhelper = new DBhelper(context);
    }

    public void insertBietOn(BietOn bietOn) {
        SQLiteDatabase db = dBhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("USER_ID", bietOn.getUserID());
        values.put("CONTENTS", String.join(",", bietOn.getContents()));
        values.put("DATE", bietOn.getDate());
        db.insert("BietOn", null, values);
        db.close();
    }

    public void updateBietOn(BietOn bietOn) {
        SQLiteDatabase db = dBhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("USER_ID", bietOn.getUserID());
        values.put("CONTENTS", String.join(",", bietOn.getContents()));
        values.put("DATE", bietOn.getDate());
        db.update("BietOn", values, "ID = ?", new String[]{String.valueOf(bietOn.getId())});
        db.close();
    }

    public List<BietOn> getAllBietOn() {
        List<BietOn> bietOnList = new ArrayList<>();
        SQLiteDatabase db = dBhelper.getReadableDatabase();
        Cursor cursor = db.query("BietOn", null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("ID"));
                int userId = cursor.getInt(cursor.getColumnIndexOrThrow("USER_ID"));
                String contents = cursor.getString(cursor.getColumnIndexOrThrow("CONTENTS"));
                String date = cursor.getString(cursor.getColumnIndexOrThrow("DATE"));

                List<String> contentList = Arrays.asList(contents.split(","));

                BietOn bietOn = new BietOn(id, contentList, date, userId);
                bietOnList.add(bietOn);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return bietOnList;
    }

    public int getStreakBietOn() {
        SQLiteDatabase db = dBhelper.getReadableDatabase();
        Cursor cursor = db.query("BietOn", new String[]{"DATE"}, null, null, null, null, "DATE DESC");

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