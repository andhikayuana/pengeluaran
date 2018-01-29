package id.yuana.pengeluaran.models;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 4/19/17
 */

public class CatatanKeuangan {

    private int catatanId;
    private int akunId;
    private int kategoriTipe;
    private String detail;
    private String tglCatatan;
    private Long nominal;

    public CatatanKeuangan() {
    }

    public CatatanKeuangan(int catatanId,
                           int akunId,
                           int kategoriTipe,
                           String detail,
                           String tglCatatan,
                           Long nominal) {

        this.catatanId = catatanId;
        this.akunId = akunId;
        this.kategoriTipe = kategoriTipe;
        this.detail = detail;
        this.tglCatatan = tglCatatan;
        this.nominal = nominal;
    }

    public int getCatatanId() {
        return catatanId;
    }

    public void setCatatanId(int catatanId) {
        this.catatanId = catatanId;
    }

    public int getAkunId() {
        return akunId;
    }

    public void setAkunId(int akunId) {
        this.akunId = akunId;
    }

    public int getKategoriTipe() {
        return kategoriTipe;
    }

    public void setKategoriTipe(int kategoriTipe) {
        this.kategoriTipe = kategoriTipe;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getTglCatatan() {
        return tglCatatan;
    }

    public void setTglCatatan(String tglCatatan) {
        this.tglCatatan = tglCatatan;
    }

    public Long getNominal() {
        return nominal;
    }

    public void setNominal(Long nominal) {
        this.nominal = nominal;
    }

    public String getKategoriString() {
        return this.kategoriTipe == 1 ?
                "Pengeluaran" : "Pemasukkan";
    }
}
