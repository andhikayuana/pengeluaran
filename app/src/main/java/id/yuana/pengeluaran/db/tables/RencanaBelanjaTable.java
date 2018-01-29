package id.yuana.pengeluaran.db.tables;

import android.content.ContentValues;
import android.database.Cursor;

import id.yuana.pengeluaran.models.RencanaBelanja;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 4/7/17
 */

public class RencanaBelanjaTable {

    public static final String TABLE_NAME = "rencana_belanja";

    public static final String FIELD_ID = "rencana_belanja_id";
    public static final String FIELD_NAME = "rencana_belanja_rencana";
    public static final String FIELD_NOMINAL = "rencana_belanja_nominal";

    public static String createTable() {
        return "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + FIELD_ID + " INT PRIMARY KEY, "
                + FIELD_NAME + " TEXT NOT NULL, "
                + FIELD_NOMINAL + " INT NOT NULL);";
    }

    public static ContentValues toContentValues(RencanaBelanja rencanaBelanja) {
        ContentValues values = new ContentValues();
        values.put(FIELD_ID, rencanaBelanja.getRencanaBelanjaId());
        values.put(FIELD_NAME, rencanaBelanja.getNamaBarang());
        values.put(FIELD_NOMINAL, rencanaBelanja.getHarga());
        return values;
    }

    public static RencanaBelanja parseCursor(Cursor cursor) {
        return new RencanaBelanja(
                cursor.getInt(cursor.getColumnIndexOrThrow(FIELD_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(FIELD_NAME)),
                cursor.getLong(cursor.getColumnIndexOrThrow(FIELD_NOMINAL)),
                false
        );
    }
}
