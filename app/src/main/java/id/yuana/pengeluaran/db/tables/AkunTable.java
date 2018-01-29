package id.yuana.pengeluaran.db.tables;

import android.content.ContentValues;
import android.database.Cursor;

import id.yuana.pengeluaran.models.Akun;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 4/7/17
 */

public class AkunTable {

    public static final String TABLE_NAME = "akun";

    public static final String FIELD_ID = "akun_id";
    public static final String FIELD_NAME = "akun_nama";

    public static String createTable() {
        return "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +" ("
                + FIELD_ID + " INT PRIMARY KEY, "
                + FIELD_NAME + " TEXT NOT NULL);";
    }

    public static ContentValues toContentValues(Akun akun) {
        ContentValues values = new ContentValues();
        values.put(FIELD_ID, akun.getAkunId());
        values.put(FIELD_NAME, akun.getAkunNama());
        return values;
    }

    public static Akun parseCursor(Cursor cursor) {
        return new Akun(
                cursor.getInt(cursor.getColumnIndexOrThrow(FIELD_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(FIELD_NAME))
        );
    }
}
