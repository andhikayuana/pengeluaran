package id.yuana.pengeluaran.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import id.yuana.pengeluaran.db.DbHelper;
import id.yuana.pengeluaran.db.tables.AkunTable;
import id.yuana.pengeluaran.models.Akun;

import java.util.ArrayList;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 4/10/17
 */

public class AkunDao extends DbHelper {

    public AkunDao(Context context) {
        super(context);
    }

    public boolean insert(Akun akun) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = AkunTable.toContentValues(akun);
        db.insert(AkunTable.TABLE_NAME, null, values);
        db.close();
        return true;
    }

    public ArrayList<Akun> all() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Akun> akuns = new ArrayList<>();
        String sql = "SELECT * FROM " + AkunTable.TABLE_NAME +
                " ORDER BY " + AkunTable.FIELD_ID + " DESC";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            Akun akun = AkunTable.parseCursor(cursor);
            akuns.add(akun);
        }
        cursor.close();
        return akuns;
    }

    public ArrayList<String> allAkunString(boolean isAdapter) {
        ArrayList<Akun> akuns = all();
        ArrayList<String> akunStrings = new ArrayList<>();
        if (isAdapter) akunStrings.add("Pilih Akun");
        for (int i = 0; i < akuns.size(); i++) {
            akunStrings.add(akuns.get(i).getAkunNama());
        }
        return akunStrings;
    }

    public void delete(Akun akun) {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = AkunTable.FIELD_ID + "=?";
        String[] whereArgs = {String.valueOf(akun.getAkunId())};
        db.delete(AkunTable.TABLE_NAME, where, whereArgs);
        db.close();
    }
}
