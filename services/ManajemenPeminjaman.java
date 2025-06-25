package services;

import filehandler.FileHandlerBuku;
import filehandler.FileHandlerPeminjaman;
import filehandler.FileHandlerRiwayat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.StringJoiner;
import javax.swing.JOptionPane;
import models.Buku;
import models.Mahasiswa;
import models.Peminjaman;

public class ManajemenPeminjaman extends PeminjamanService {
    private Map<Integer, Peminjaman> daftarPeminjaman;
    private List<Peminjaman> riwayat;
    private ManajemenMahasiswa manajemenMahasiswa;
    private ManajemenBuku manajemenBuku;
    public FileHandlerPeminjaman fhPeminjaman;
    public FileHandlerRiwayat fhRiwayat;
    public FileHandlerBuku fhBuku;

    public ManajemenPeminjaman(ManajemenBuku manajemenBuku, ManajemenMahasiswa manajemenMahasiswa) {
        this.manajemenBuku = manajemenBuku;
        this.manajemenMahasiswa = manajemenMahasiswa;
        this.fhPeminjaman = new FileHandlerPeminjaman(manajemenBuku, manajemenMahasiswa);
        this.fhRiwayat = new FileHandlerRiwayat(manajemenBuku, manajemenMahasiswa);
        this.daftarPeminjaman = fhPeminjaman.bacaData();
        this.riwayat = fhRiwayat.bacaRiwayat();
    }

    public void tambahPeminjaman(Peminjaman peminjamanBaru) {
        this.daftarPeminjaman = fhPeminjaman.bacaData();

        this.daftarPeminjaman.put(peminjamanBaru.getKodePeminjaman(), peminjamanBaru);
        manajemenMahasiswa.editMhs(peminjamanBaru.getMhs());
        fhPeminjaman.simpanData(this.daftarPeminjaman);
    }

    public void hapusPeminjaman(int kodePeminjaman) {
        this.daftarPeminjaman = fhPeminjaman.bacaData();

        if (this.daftarPeminjaman.containsKey(kodePeminjaman)) {
            this.daftarPeminjaman.remove(kodePeminjaman);
            fhPeminjaman.simpanData(this.daftarPeminjaman);
        } else {
            System.out.println("Peminjaman dengan kode " + kodePeminjaman + " tidak ditemukan dalam daftar aktif.");
        }
    }

    public boolean prosesPeminjaman(Mahasiswa mhs, Set<Buku> bukuYangDipinjam, int durasiPinjamHari) {
        int kodePeminjaman = generateKode();
        LocalDate tanggalPinjam = LocalDate.now();
        LocalDate batasTanggalKembali = tanggalPinjam.plusDays(durasiPinjamHari);

        Peminjaman peminjamanBaru = new Peminjaman(kodePeminjaman, mhs, bukuYangDipinjam, tanggalPinjam, batasTanggalKembali, "Dipinjam");
        
        //cek apakah pernah telat
        if (peminjamanBaru.getMhs().getKenaDenda()) {
            JOptionPane.showMessageDialog(null, "Mahasiswa dengan NIM " + peminjamanBaru.getMhs().getNim() + " telah terlambat. Mohon segera membayar denda.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            // return false;
        }
        
        //cek apakah sudah pernah pinjam buku yang sama
        StringJoiner bukuSama = new StringJoiner(", ");
        for (Buku buku : peminjamanBaru.getDaftarBukuDipinjam()) {
            String kodeBuku = buku.getKodeBuku();
            for (Buku b : peminjamanBaru.getMhs().getDaftarBuku()) {
                if (b.getKodeBuku().equals(kodeBuku)) {
                    bukuSama.add(b.getNamaBuku());
                }
            }
        }

        if (bukuSama.length() > 0) {
            JOptionPane.showMessageDialog(null, "Mahasiswa dengan NIM " + peminjamanBaru.getMhs().getNim() + " telah meminjam buku " + bukuSama + " sebelumnya.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        //cek apakah stok buku cukup ketika dikurangi 1
        for (Buku buku : peminjamanBaru.getDaftarBukuDipinjam()) {
            if ((buku.getStok() - 1) < 0) {
                JOptionPane.showMessageDialog(null, "Stok buku " + buku.getNamaBuku() + " tidak cukup.", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        }

        //kurangi buku
        for (Buku buku : bukuYangDipinjam) {
            buku.kurangiStok();
            manajemenBuku.editBuku(buku);
            mhs.tambahPinjaman(buku);
        }

        this.tambahPeminjaman(peminjamanBaru); 

        return true;
    }

    public boolean prosesPengembalian(int kodePeminjaman) {
        this.daftarPeminjaman = fhPeminjaman.bacaData();
        Peminjaman peminjaman = this.daftarPeminjaman.get(kodePeminjaman);

        //terlambat atau tidak, tetap bisa mengembalikan buku
        peminjaman.setTanggalKembali(LocalDate.now());
        peminjaman.setStatus("Dikembalikan");
        peminjaman.setDikembalikan(true);

        Mahasiswa mhs = peminjaman.getMhs();

        for (Buku buku : peminjaman.getDaftarBukuDipinjam()) {
            //perbarui stok buku yang dikembalikan
            buku.tambahStok();
            manajemenBuku.editBuku(buku);
            mhs.kembaliPinjaman(buku.getKodeBuku());
        }
        
        manajemenMahasiswa.editMhs(mhs);
        fhRiwayat.tambahRiwayat(peminjaman);

        this.hapusPeminjaman(kodePeminjaman);
        
        return mhs.getKenaDenda();
    }

    public Peminjaman cariPeminjaman(int kodePeminjaman) {
        this.daftarPeminjaman = fhPeminjaman.bacaData();
        return daftarPeminjaman.get(kodePeminjaman);
    }


    public int generateKode() {
        Random rand = new Random();
        int kode = 0;
        boolean exists;
        do {
            kode = rand.nextInt(9000) + 1000;
            exists = daftarPeminjaman.containsKey(kode);
            if (!exists) {
                for (Peminjaman p : riwayat) {
                    if (p.getKodePeminjaman() == kode) {
                        exists = true;
                        break;
                    }
                }
            }
        } while (exists);
        return kode;
    }
    public Map<Integer, Peminjaman> getDaftarPeminjaman() {
        this.daftarPeminjaman = fhPeminjaman.bacaData();
        return new HashMap<>(daftarPeminjaman);
    }
    public List<Peminjaman> getRiwayatPeminjaman() {
        this.riwayat = fhRiwayat.bacaRiwayat(); 
        return new ArrayList<>(riwayat);
    }

    public void hapusSeluruhRiwayat() {
        fhRiwayat.hapusRiwayat();
        this.riwayat.clear();
    }

    @Override
    public void periksaKeterlambatan(Integer kodePeminjaman) {
        double dendaPerBuku = 10000;
        this.daftarPeminjaman = fhPeminjaman.bacaData();
        Peminjaman peminjaman = this.daftarPeminjaman.get(kodePeminjaman);
        Mahasiswa mhs = peminjaman.getMhs();

        //ketika pertama kali terlambat
        if (LocalDate.now().isAfter(peminjaman.getBatasTanggalKembali()) && !mhs.getKenaDenda()) {
            peminjaman.setStatus("Terlambat");
            mhs.setKenaDenda(true);
            mhs.tambahDenda(dendaPerBuku * peminjaman.getDaftarBukuDipinjam().size());
            manajemenMahasiswa.editMhs(mhs);

        //ketika aman
        } else if (LocalDate.now().isBefore(peminjaman.getBatasTanggalKembali())) {
            peminjaman.setStatus("Dipinjam");
        }

        /*
            Yang sudah terlambat statusnya tetap terlambat dan kena denda tetap true
            perubahan status denda berada di proses pengembalian
        */
        
        fhPeminjaman.simpanData(this.daftarPeminjaman);
    }
}
