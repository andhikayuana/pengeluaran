package id.yuana.pengeluaran.db.tables;

import android.content.ContentValues;
import android.database.Cursor;

import id.yuana.pengeluaran.models.Akun;
import id.yuana.pengeluaran.models.CatatanKeuangan;
import id.yuana.pengeluaran.models.Kategori;
import id.yuana.pengeluaran.models.Transfer;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 4/19/17
 */

public class CatatanKeuanganTable {

    public static final String TABLE_NAME = "catatan_keuangan";

    public static final String FIELD_ID = "catatan_keuangan_id";
    public static final String FIELD_AKUN_ID = "catatan_keuangan_akun_id";
    public static final String FIELD_KATEGORI_TIPE = "catatan_keuangan_kategori_tipe";
    public static final String FIELD_DETAIL = "catatan_keuangan_detail";
    public static final String FIELD_TGL_CATATAN = "catatan_keuangan_tgl_catatan";
    public static final String FIELD_NOMINAL = "catatan_keuangan_nominal";


    public static String createTable() {
        return "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + FIELD_ID + " INT PRIMARY KEY, "
                + FIELD_AKUN_ID + " INT NOT NULL, "
                + FIELD_KATEGORI_TIPE + " INT NOT NULL, "
                + FIELD_DETAIL + " TEXT, "
                + FIELD_TGL_CATATAN + " TEXT, "
                + FIELD_NOMINAL + " INT,"
                + "FOREIGN KEY(" + FIELD_AKUN_ID + ") "
                + "REFERENCES " + AkunTable.TABLE_NAME + "(" + AkunTable.FIELD_ID + ") );";
    }

    public static ContentValues toContentValues(CatatanKeuangan catatanKeuangan) {
        ContentValues values = new ContentValues();
        values.put(FIELD_ID, catatanKeuangan.getCatatanId());
        values.put(FIELD_AKUN_ID, catatanKeuangan.getAkunId());
        values.put(FIELD_KATEGORI_TIPE, catatanKeuangan.getKategoriTipe());
        values.put(FIELD_DETAIL, catatanKeuangan.getDetail());
        values.put(FIELD_TGL_CATATAN, catatanKeuangan.getTglCatatan());
        values.put(FIELD_NOMINAL, catatanKeuangan.getNominal());
        return values;
    }

    public static CatatanKeuangan parseCursor(Cursor cursor) {
        return new CatatanKeuangan(
                cursor.getInt(cursor.getColumnIndexOrThrow(FIELD_ID)),
                cursor.getInt(cursor.getColumnIndexOrThrow(FIELD_AKUN_ID)),
                cursor.getInt(cursor.getColumnIndexOrThrow(FIELD_KATEGORI_TIPE)),
                cursor.getString(cursor.getColumnIndexOrThrow(FIELD_DETAIL)),
                cursor.getString(cursor.getColumnIndexOrThrow(FIELD_TGL_CATATAN)),
                cursor.getLong(cursor.getColumnIndexOrThrow(FIELD_NOMINAL))
        );
    }

    public static Transfer parseCursorTransfer(Cursor cursor) {
        return new Transfer(
                new Akun(
                        cursor.getInt(cursor.getColumnIndexOrThrow(AkunTable.FIELD_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(AkunTable.FIELD_NAME))
                ),
                getNominal(cursor, Kategori.PEMASUKKAN),
                getNominal(cursor, Kategori.PENGELUARAN)
        );
    }

    private static Long getNominal(Cursor cursor, int kategoriTipe) {
        return cursor.getInt(cursor.getColumnIndexOrThrow(FIELD_KATEGORI_TIPE)) ==
                kategoriTipe ? cursor.getLong(cursor.getColumnIndexOrThrow("nominal")) : 0L;
    }
}
