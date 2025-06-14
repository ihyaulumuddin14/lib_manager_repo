package models;

import java.time.LocalDate;
import java.util.Set;
import java.util.HashSet;
import java.time.temporal.ChronoUnit;

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

    public void periksaKeterlambatan() {
        if (this.tanggalKembali != null && this.tanggalKembali.isAfter(this.batasTanggalKembali)) {
            long selisihHari = ChronoUnit.DAYS.between(this.batasTanggalKembali, this.tanggalKembali);
            double dendaPerHari = 1000.0; 
            double nominalDenda = selisihHari * dendaPerHari;
            mhs.setKenaDenda(true);
            mhs.setDenda(nominalDenda);
            System.out.println("Mahasiswa " + mhs.getNama() + " dikenakan denda sebesar Rp" + nominalDenda + " karena terlambat mengembalikan buku selama " + selisihHari + " hari.");
        } else {
            mhs.setKenaDenda(false);
            mhs.setDenda(0);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Kode Peminjaman: ").append(kodePeminjaman).append("\n");
        sb.append("Mahasiswa: ").append(mhs.getNim()).append(" - ").append(mhs.getNama()).append("\n");
        sb.append("Buku Dipinjam:\n");
        for (Buku buku : daftarBukuDipinjam) {
            sb.append("  - ").append(buku.getNamaBuku()).append(" (").append(buku.getKodeBuku()).append(")\n");
        }
        sb.append("Tanggal Pinjam: ").append(tanggalPinjam).append("\n");
        sb.append("Batas Tanggal Kembali: ").append(batasTanggalKembali).append("\n");
        sb.append("Tanggal Kembali: ").append(tanggalKembali != null ? tanggalKembali : "Belum Kembali").append("\n");
        sb.append("Status: ").append(status);
        sb.append(" (Dikembalikan: ").append(isDikembalikan).append(")");
        return sb.toString();
    }
}
