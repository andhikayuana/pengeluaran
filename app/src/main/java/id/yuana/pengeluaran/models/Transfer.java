package id.yuana.pengeluaran.models;

import java.util.ArrayList;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 4/26/17
 */

public class Transfer {

    private Akun akun;
    private Long pemasukkan = 0L;
    private Long pengeluaran = 0L;

    public Transfer() {
    }

    public Transfer(Akun akun, Long pemasukkan, Long pengeluaran) {
        this.akun = akun;
        this.pemasukkan = pemasukkan;
        this.pengeluaran = pengeluaran;
    }

    public static ArrayList<Transfer> generateData() {
        ArrayList<Transfer> transfers = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            transfers.add(
                    new Transfer(
                            new Akun(i, "Akun " + i),
                            10000L,
                            5500L
                    )
            );
        }
        return transfers;
    }

    public Akun getAkun() {
        return akun;
    }

    public void setAkun(Akun akun) {
        this.akun = akun;
    }

    public Long getPemasukkan() {
        return pemasukkan;
    }

    public void setPemasukkan(Long pemasukkan) {
        this.pemasukkan = pemasukkan;
    }

    public Long getPengeluaran() {
        return pengeluaran;
    }

    public void setPengeluaran(Long pengeluaran) {
        this.pengeluaran = pengeluaran;
    }

    public Long getTotal() {
        return pemasukkan - pengeluaran;
    }
}
