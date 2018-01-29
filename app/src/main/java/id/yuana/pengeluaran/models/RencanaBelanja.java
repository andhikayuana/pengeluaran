package id.yuana.pengeluaran.models;

import java.util.ArrayList;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 4/3/17
 */

public class RencanaBelanja {

    private int rencanaBelanjaId;
    private String namaBarang;
    private long harga;
    private boolean isChecked = false;

    public RencanaBelanja(int rencanaBelanjaId, String namaBarang, long harga, boolean isChecked) {
        this.rencanaBelanjaId = rencanaBelanjaId;
        this.namaBarang = namaBarang;
        this.harga = harga;
        this.isChecked = isChecked;
    }

    public static ArrayList<RencanaBelanja> generateData() {
        ArrayList<RencanaBelanja> data = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            data.add(new RencanaBelanja(i, "Barang " + i, 2000, true));
        }
        return data;
    }

    public int getRencanaBelanjaId() {
        return rencanaBelanjaId;
    }

    public void setRencanaBelanjaId(int rencanaBelanjaId) {
        this.rencanaBelanjaId = rencanaBelanjaId;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public long getHarga() {
        return harga;
    }

    public void setHarga(long harga) {
        this.harga = harga;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
