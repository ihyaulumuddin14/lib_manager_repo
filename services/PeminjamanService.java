package services;

import java.util.Set;
import models.Buku;
import models.Mahasiswa;
import models.Peminjaman;

public abstract class PeminjamanService {
    public abstract void tambahPeminjaman(Peminjaman peminjaman);
    public abstract void hapusPeminjaman(int kodePeminjaman);
    public abstract Peminjaman cariPeminjaman(int kodePeminjaman);
    public abstract boolean prosesPeminjaman(Mahasiswa mhs, Set<Buku> bukuYangDipinjam, int durasiPinjamHari);
    public abstract boolean prosesPengembalian(int kodePeminjaman);
    public abstract void hapusSeluruhRiwayat();
    public abstract void periksaKeterlambatan(Integer kode);
}