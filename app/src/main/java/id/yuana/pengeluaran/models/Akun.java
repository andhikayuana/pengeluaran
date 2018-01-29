package id.yuana.pengeluaran.models;

import java.util.ArrayList;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 3/31/17
 */

public class Akun {

    private int akunId;
    private String akunNama;

    public Akun() {
    }

    public Akun(int akunId, String akunNama) {
        this.akunId = akunId;
        this.akunNama = akunNama;
    }

    public static ArrayList<Akun> generateData() {
        ArrayList<Akun> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            data.add(new Akun(i, "akun " + i));
        }
        return data;
    }

    public int getAkunId() {
        return akunId;
    }

    public void setAkunId(int akunId) {
        this.akunId = akunId;
    }

    public String getAkunNama() {
        return akunNama;
    }

    public void setAkunNama(String akunNama) {
        this.akunNama = akunNama;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Akun akun = (Akun) o;

        if (akunId != akun.akunId) return false;
        return akunNama != null ? akunNama.equals(akun.akunNama) : akun.akunNama == null;

    }

    @Override
    public int hashCode() {
        int result = akunId;
        result = 31 * result + (akunNama != null ? akunNama.hashCode() : 0);
        return result;
    }
}
