package id.yuana.pengeluaran.models;

import java.util.ArrayList;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 4/3/17
 */

public class Kategori {

    public static final int PENGELUARAN = 1;
    public static final int PEMASUKKAN = 2;

    private int kategoriId;
    private String kategoriNama;
    private int kategoriTipe;

    public Kategori(int kategoriId, String kategoriNama, int kategoriTipe) {
        this.kategoriId = kategoriId;
        this.kategoriNama = kategoriNama;
        this.kategoriTipe = kategoriTipe;
    }

    public static ArrayList<Kategori> generateData() {
        ArrayList<Kategori> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            data.add(new Kategori(i, "kategori " + i, PEMASUKKAN));
        }
        return data;
    }

    public int getKategoriTipe() {
        return kategoriTipe;
    }

    public void setKategoriTipe(int kategoriTipe) {
        this.kategoriTipe = kategoriTipe;
    }

    public int getKategoriId() {
        return kategoriId;
    }

    public void setKategoriId(int kategoriId) {
        this.kategoriId = kategoriId;
    }

    public String getKategoriNama() {
        return kategoriNama;
    }

    public void setKategoriNama(String kategoriNama) {
        this.kategoriNama = kategoriNama;
    }
}
