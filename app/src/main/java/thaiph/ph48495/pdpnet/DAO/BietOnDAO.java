package thaiph.ph48495.pdpnet.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import thaiph.ph48495.pdpnet.DBhelper.DBhelper;
import thaiph.ph48495.pdpnet.models.Gratitude;

public class BietOnDAO {
    private DBhelper dBhelper;
    private SQLiteDatabase db;
    public BietOnDAO(Context context){
        dBhelper = new DBhelper(context);

    }

    public long insertGratitude(Gratitude entry) {
        SQLiteDatabase db = dBhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ENTRY", entry.getEntry());
        values.put("DATE", entry.getDate());
        long check = db.insert("BietOn",null,values);
        if(check<=0){
            return -1;
        }
        return 1;
    }

    public ArrayList<Gratitude> getAllGratitude() {
        ArrayList<Gratitude> list = new ArrayList<>();
        db = dBhelper.getReadableDatabase();
        try{
            Cursor c =  db.rawQuery("SELECT * FROM BietOn", null);
        if(c!= null && c.getCount()>0){
            c.moveToFirst();
            do {
                int id = c.getInt(0);
                String entry = c.getString(1);
                String date = c.getString(2);
                list.add(new Gratitude(id,entry,date));
            }while (c.moveToNext());
        }}catch (Exception e){
            Log.e("Loi",e.getMessage());
        }
        return list;
    }
    public long delete(int id){
        db = dBhelper.getWritableDatabase();
        long check = db.delete("BietOn","ID=?",new String[]{String.valueOf(id)});
        if(check<=0){
            return -1;
        }
        return 1;
    }

    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }
}
