package models;

import java.time.LocalDate;
import java.util.Set;

public class Peminjaman {
    private int kodePeminjaman;
    private Mahasiswa mhs;
    private Set<Buku> daftarBukuDipinjam;
    private LocalDate tanggalPinjam;
    private LocalDate tanggalKembali;
    private LocalDate batasTanggalKembali;
    private String status;
    private boolean isDikembalikan;

    public Peminjaman(int kodePeminjaman, Mahasiswa mhs, Set<Buku> daftarBukuDipinjam, LocalDate tanggalPinjam, LocalDate batasTanggalKembali, String status) {
        this.kodePeminjaman = kodePeminjaman;
        this.mhs = mhs;
        this.daftarBukuDipinjam = daftarBukuDipinjam;
        this.tanggalPinjam = tanggalPinjam;
        this.batasTanggalKembali = batasTanggalKembali;
        this.status = status;
        this.isDikembalikan = false;
    }

    public int getKodePeminjaman() {
        return kodePeminjaman;
    }

    public Mahasiswa getMhs() {
        return mhs;
    }

    public Set<Buku> getDaftarBukuDipinjam() {
        return daftarBukuDipinjam;
    }

    public LocalDate getTanggalPinjam() {
        return tanggalPinjam;
    }

    public LocalDate getTanggalKembali() {
        return tanggalKembali;
    }

    public void setTanggalKembali(LocalDate tanggalKembali) {
        this.tanggalKembali = tanggalKembali;
    }

    public LocalDate getBatasTanggalKembali() {
        return batasTanggalKembali;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isDikembalikan() {
        return isDikembalikan;
    }

    public void setDikembalikan(boolean dikembalikan) {
        isDikembalikan = dikembalikan;
    }
}
