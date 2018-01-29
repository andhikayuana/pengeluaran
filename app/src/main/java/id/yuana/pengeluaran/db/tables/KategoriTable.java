package id.yuana.pengeluaran.db.tables;

import android.content.ContentValues;
import android.database.Cursor;

import id.yuana.pengeluaran.models.Kategori;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 4/7/17
 */

public class KategoriTable {

    public static final String TABLE_NAME = "kategori";

    public static final String FIELD_ID = "kategori_id";
    public static final String FIELD_NAME = "kategori_nama";
    public static final String FIELD_TYPE = "kategori_type";

    public static String createTable() {
        return "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + FIELD_ID + " INT PRIMARY KEY, "
                + FIELD_NAME + " TEXT NOT NULL, "
                + FIELD_TYPE + " INT NOT NULL);";
    }

    public static ContentValues toContentValues(Kategori kategori) {
        ContentValues values = new ContentValues();
        values.put(FIELD_ID, kategori.getKategoriId());
        values.put(FIELD_NAME, kategori.getKategoriNama());
        values.put(FIELD_TYPE, kategori.getKategoriTipe());
        return values;
    }

    public static Kategori parseCursor(Cursor cursor) {
        return new Kategori(
                cursor.getInt(cursor.getColumnIndexOrThrow(FIELD_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(FIELD_NAME)),
                cursor.getInt(cursor.getColumnIndexOrThrow(FIELD_TYPE))
        );
    }
}
