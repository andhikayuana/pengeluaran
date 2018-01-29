package id.yuana.pengeluaran.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import id.yuana.pengeluaran.db.DbHelper;
import id.yuana.pengeluaran.db.tables.RencanaBelanjaTable;
import id.yuana.pengeluaran.models.RencanaBelanja;

import java.util.ArrayList;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 4/12/17
 */

public class RencanaBelanjaDao extends DbHelper {
    
    public RencanaBelanjaDao(Context context) {
        super(context);
    }

    public boolean insert(RencanaBelanja rencanaBelanja) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = RencanaBelanjaTable.toContentValues(rencanaBelanja);
        db.insert(RencanaBelanjaTable.TABLE_NAME, null, values);
        db.close();
        return true;
    }

    public ArrayList<RencanaBelanja> all() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<RencanaBelanja> RencanaBelanjas = new ArrayList<>();
        String sql = "SELECT * FROM " + RencanaBelanjaTable.TABLE_NAME +
                " ORDER BY " + RencanaBelanjaTable.FIELD_ID + " DESC";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            RencanaBelanja RencanaBelanja = RencanaBelanjaTable.parseCursor(cursor);
            RencanaBelanjas.add(RencanaBelanja);
        }
        cursor.close();
        return RencanaBelanjas;
    }

    public void delete(RencanaBelanja RencanaBelanja) {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = RencanaBelanjaTable.FIELD_ID + "=?";
        String[] whereArgs = {String.valueOf(RencanaBelanja.getRencanaBelanjaId())};
        db.delete(RencanaBelanjaTable.TABLE_NAME, where, whereArgs);
        db.close();
    }
}
