package id.yuana.pengeluaran.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import id.yuana.pengeluaran.db.DbHelper;
import id.yuana.pengeluaran.db.tables.CatatanKeuanganTable;
import id.yuana.pengeluaran.models.CatatanKeuangan;
import id.yuana.pengeluaran.models.Transfer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 4/19/17
 */

public class CatatanKeuanganDao extends DbHelper {

    public CatatanKeuanganDao(Context context) {
        super(context);
    }

    public boolean insert(CatatanKeuangan catatanKeuangan) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = CatatanKeuanganTable
                    .toContentValues(catatanKeuangan);
            db.insert(CatatanKeuanganTable.TABLE_NAME, null, values);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    public ArrayList<CatatanKeuangan> all() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<CatatanKeuangan> catatanKeuanganList = new ArrayList<>();
        String sql = "SELECT * FROM " + CatatanKeuanganTable.TABLE_NAME +
                " ORDER BY " + CatatanKeuanganTable.FIELD_ID + " DESC";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            CatatanKeuangan catatanKeuangan = CatatanKeuanganTable
                    .parseCursor(cursor);
            catatanKeuanganList.add(catatanKeuangan);
        }
        cursor.close();
        return catatanKeuanganList;
    }

    public void delete(CatatanKeuangan catatanKeuangan) {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = CatatanKeuanganTable.FIELD_ID + "=?";
        String[] whereArgs = {String.valueOf(catatanKeuangan.getCatatanId())};
        db.delete(CatatanKeuanganTable.TABLE_NAME, where, whereArgs);
        db.close();
    }

    public ArrayList<Transfer> allTransfer() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Transfer> transferListTemp = new ArrayList<>();
        ArrayList<Transfer> transferList = new ArrayList<>();

        String sql = "SELECT " +
                "catatan_keuangan.catatan_keuangan_id, " +
                "catatan_keuangan.catatan_keuangan_kategori_tipe, " +
                "catatan_keuangan.catatan_keuangan_detail, " +
                "SUM(catatan_keuangan.catatan_keuangan_nominal) AS nominal, " +
                "akun.* " +
                " FROM catatan_keuangan " +
                " JOIN akun ON catatan_keuangan.catatan_keuangan_akun_id = akun.akun_id " +
                " GROUP BY catatan_keuangan.catatan_keuangan_akun_id, " +
                " catatan_keuangan.catatan_keuangan_kategori_tipe; ";

        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            Transfer transfer = CatatanKeuanganTable
                    .parseCursorTransfer(cursor);
            transferListTemp.add(transfer);
        }
        cursor.close();

        for (int i = 0; i < transferListTemp.size(); i++) {
            Transfer transferTemp = transferListTemp.get(i);
            if (transferList.isEmpty()) {
                transferList.add(transferTemp);
            } else {
                Transfer transfer = transferList.get(transferList.size() - 1);
                if (transfer.getAkun().equals(transferTemp.getAkun())) {
                    transfer.setPengeluaran(
                            transfer.getPengeluaran() + transferTemp.getPengeluaran()
                    );
                    transfer.setPemasukkan(
                            transfer.getPemasukkan() + transferTemp.getPemasukkan()
                    );
                } else {
                    transferList.add(transferTemp);
                }
            }
        }

        return transferList;
    }

    public ArrayList<String> getDates() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> datesTemp = new ArrayList<>();
        ArrayList<String> dates = new ArrayList<>();

        String sql = "select catatan_keuangan.catatan_keuangan_tgl_catatan "
                + "from catatan_keuangan "
                + "group by catatan_keuangan.catatan_keuangan_tgl_catatan";

        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            String dateString = cursor.getString(cursor.getColumnIndexOrThrow(CatatanKeuanganTable.FIELD_TGL_CATATAN));
            try {
                String year = new SimpleDateFormat("yyyy")
                        .format(new SimpleDateFormat("dd-MM-yyyy")
                                .parse(dateString));

                datesTemp.add(year);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        cursor.close();

        for (int i = 0; i < datesTemp.size(); i++) {
            if (!dates.contains(datesTemp.get(i))) dates.add(datesTemp.get(i));
        }

        return dates;
    }

    public ArrayList<CatatanKeuangan> allFilter(int bulan, String tahun, int akunId) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<CatatanKeuangan> catatanKeuanganList = new ArrayList<>();

        String sql = "select * " +
                "from catatan_keuangan " +
                "where catatan_keuangan.catatan_keuangan_tgl_catatan " +
                "like '%"+bulan+"-"+tahun+"'";

        if (akunId != 0) sql+= " and catatan_keuangan.catatan_keuangan_akun_id="+akunId;

        sql += " order by catatan_keuangan.catatan_keuangan_id desc;";

        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            CatatanKeuangan catatanKeuangan = CatatanKeuanganTable
                    .parseCursor(cursor);
            catatanKeuanganList.add(catatanKeuangan);
        }
        cursor.close();

        return catatanKeuanganList;
    }

    public ArrayList<CatatanKeuangan> allFilter(int bulan, String tahun) {
        return allFilter(bulan, tahun, 0);
    }
}
