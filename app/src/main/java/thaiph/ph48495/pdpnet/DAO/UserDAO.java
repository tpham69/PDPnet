package thaiph.ph48495.pdpnet.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import thaiph.ph48495.pdpnet.DBhelper.DBhelper;

public class UserDAO {
    private final DBhelper dBhelper;

    public UserDAO(Context context) {
        dBhelper = new DBhelper(context);
    }

    public int getIDbyEmail(String email) {
        SQLiteDatabase db = dBhelper.getReadableDatabase();
        Cursor cursor = db.query("USER", new String[]{"ID"}, "EMAIL=?", new String[]{email}, null, null, null);

        int userId = -1;
        if (cursor != null && cursor.moveToFirst()) {
            userId = cursor.getInt(cursor.getColumnIndexOrThrow("ID"));
            cursor.close();
        }
        db.close();
        return userId;
    }
}
