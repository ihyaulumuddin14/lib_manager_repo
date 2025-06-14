package services;

import filehandler.FIleHandlerPeminjaman; 
import models.Peminjaman;
import models.Buku;
import models.Mahasiswa;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random; 
import java.util.Set;

public class ManajemenPeminjaman extends PeminjamanService {
    private Map<Integer, Peminjaman> daftarPeminjaman;
    private List<Peminjaman> riwayat; 
    private FIleHandlerPeminjaman fhPeminjaman;
    

    public ManajemenPeminjaman(ManajemenBuku manajemenBuku, ManajemenMahasiswa manajemenMahasiswa) { 
        this.fhPeminjaman = new FIleHandlerPeminjaman(manajemenBuku, manajemenMahasiswa);
        this.daftarPeminjaman = fhPeminjaman.bacaData();
        this.riwayat = new ArrayList<>(); 
    }

    public void tambahDataPeminjaman(Peminjaman p) {
        daftarPeminjaman.put(p.getKodePeminjaman(), p);
        fhPeminjaman.simpanData(daftarPeminjaman);
        // Add to riwayat if needed, e.g., when a borrowing is completed or added
        // riwayat.add(p); 
        // fhRiwayat.simpanData(riwayat);
    }

    public void hapusDataPeminjaman(int kodePeminjaman) {
        if (daftarPeminjaman.containsKey(kodePeminjaman)) {
            daftarPeminjaman.remove(kodePeminjaman);
            fhPeminjaman.simpanData(daftarPeminjaman);
            System.out.println("Peminjaman dengan kode " + kodePeminjaman + " berhasil dihapus.");
        } else {
            System.out.println("Peminjaman dengan kode " + kodePeminjaman + " tidak ditemukan.");
        }
    }

    public Peminjaman cariPeminjaman(int kodePeminjaman) {
        return daftarPeminjaman.get(kodePeminjaman);
    }

    public void updatePeminjaman(Peminjaman peminjaman) {
        if (daftarPeminjaman.containsKey(peminjaman.getKodePeminjaman())) {
            daftarPeminjaman.put(peminjaman.getKodePeminjaman(), peminjaman);
            fhPeminjaman.simpanData(daftarPeminjaman);
        } else {
            System.out.println("Peminjaman dengan kode " + peminjaman.getKodePeminjaman() + " tidak ditemukan untuk diperbarui.");
        }
    }

    public String prosesPeminjaman(Mahasiswa mhs, Set<Buku> bukuYangDipinjam, int durasiPinjamHari) {
        int kodePeminjaman = generateKode();
        LocalDate tanggalPinjam = LocalDate.now(); 
        LocalDate batasTanggalKembali = tanggalPinjam.plusDays(durasiPinjamHari); 
        Peminjaman peminjamanBaru = new Peminjaman(kodePeminjaman, mhs, bukuYangDipinjam, tanggalPinjam, batasTanggalKembali, "Dipinjam");
        tambahDataPeminjaman(peminjamanBaru);
        for (Buku buku : bukuYangDipinjam) {
            buku.kurangiStok();
            mhs.tambahPinjaman(buku);
        }
        System.out.println("Peminjaman berhasil dilakukan dengan kode: " + kodePeminjaman);
        return peminjamanBaru.toString();
    }


    public void prosesPengembalian(int kodePeminjaman) {
        Peminjaman peminjaman = daftarPeminjaman.get(kodePeminjaman);
        if (peminjaman != null && peminjaman.getStatus().equals("Dipinjam")) {
            peminjaman.setTanggalKembali(LocalDate.now()); 
            peminjaman.periksaKeterlambatan();
            peminjaman.setStatus("Dikembalikan");
            Mahasiswa mhs = peminjaman.getMhs();
            for (Buku buku : peminjaman.getDaftarBukuDipinjam()) {
                buku.tambahStok();
                mhs.kembaliPinjaman(buku);
            }
            fhPeminjaman.simpanData(daftarPeminjaman); 
            System.out.println("Pengembalian untuk kode peminjaman " + kodePeminjaman + " berhasil diproses.");

        } else if (peminjaman != null && peminjaman.getStatus().equals("Dikembalikan")) {
            System.out.println("Peminjaman dengan kode " + kodePeminjaman + " sudah dikembalikan.");
        }
        else {
            System.out.println("Peminjaman dengan kode " + kodePeminjaman + " tidak ditemukan atau sudah dikembalikan.");
        }
    }
    public int generateKode() {
        Random rand = new Random(); 
        int kode;
        do {
            kode = rand.nextInt(9000) + 1000; 
        } while (daftarPeminjaman.containsKey(kode));
        return kode;
    }

    public Map<Integer, Peminjaman> getDaftarPeminjaman() {
        this.daftarPeminjaman = fhPeminjaman.bacaData();
        return new HashMap<>(daftarPeminjaman);
    }

    public List<Peminjaman> getRiwayatPeminjaman() {
        return new ArrayList<>(riwayat); 
    }
}
