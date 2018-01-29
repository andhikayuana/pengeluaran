package id.yuana.pengeluaran.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import id.yuana.pengeluaran.db.DbHelper;
import id.yuana.pengeluaran.db.tables.KategoriTable;
import id.yuana.pengeluaran.models.Kategori;

import java.util.ArrayList;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 4/12/17
 */

public class KategoriDao extends DbHelper {

    public KategoriDao(Context context) {
        super(context);
    }

    public boolean insert(Kategori kategori) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = KategoriTable.toContentValues(kategori);
        db.insert(KategoriTable.TABLE_NAME, null, values);
        db.close();
        return true;
    }

    public ArrayList<Kategori> all() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Kategori> kategoris = new ArrayList<>();
        String sql = "SELECT * FROM " + KategoriTable.TABLE_NAME +
                " ORDER BY " + KategoriTable.FIELD_ID + " DESC";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            Kategori kategori = KategoriTable.parseCursor(cursor);
            kategoris.add(kategori);
        }
        cursor.close();
        return kategoris;
    }

    public void delete(Kategori kategori) {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = KategoriTable.FIELD_ID + "=?";
        String[] whereArgs = {String.valueOf(kategori.getKategoriId())};
        db.delete(KategoriTable.TABLE_NAME, where, whereArgs);
        db.close();
    }

    public ArrayList<String> allKategoriString(boolean isAdapter) {
        ArrayList<Kategori> kategoris = all();
        ArrayList<String> kategoriStrings = new ArrayList<>();
        if (isAdapter) kategoriStrings.add("Pilih Kategori");
        for (int i = 0; i < kategoris.size(); i++) {
            kategoriStrings.add(kategoris.get(i).getKategoriNama());
        }
        return kategoriStrings;
    }
}
