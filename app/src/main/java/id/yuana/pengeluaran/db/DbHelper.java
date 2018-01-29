package id.yuana.pengeluaran.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import id.yuana.pengeluaran.db.tables.AkunTable;
import id.yuana.pengeluaran.db.tables.CatatanKeuanganTable;
import id.yuana.pengeluaran.db.tables.KategoriTable;
import id.yuana.pengeluaran.db.tables.RencanaBelanjaTable;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 4/7/17
 */

public class DbHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "db_pengeluaran";
    public static final int DB_VERSION = 1;

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            db.execSQL(AkunTable.createTable());
            db.execSQL(KategoriTable.createTable());
            db.execSQL(RencanaBelanjaTable.createTable());
            db.execSQL(CatatanKeuanganTable.createTable());

            // TODO: 4/7/17

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
