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

    public boolean tambahPeminjaman(Peminjaman p) {
        this.daftarPeminjaman = fhPeminjaman.bacaData();
        
        //cek apakah sudah pernah pinjam buku yang sama
        StringJoiner bukuSama = new StringJoiner(", ");
        for (Buku buku : p.getDaftarBukuDipinjam()) {
            String kodeBuku = buku.getKodeBuku();
            for (Buku b : p.getMhs().getDaftarBuku()) {
                if (b.getKodeBuku().equals(kodeBuku)) {
                    bukuSama.add(b.getNamaBuku());
                }
            }
        }

        System.out.println(bukuSama.toString());
        if (bukuSama.length() > 0) {
            JOptionPane.showMessageDialog(null, "Mahasiswa dengan NIM " + p.getMhs().getNim() + " telah meminjam buku " + bukuSama + " sebelumnya.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        //cek apakah stok buku cukup
        for (Buku buku : p.getDaftarBukuDipinjam()) {
            if ((buku.getStok() - 1) < 0) {
                JOptionPane.showMessageDialog(null, "Stok buku " + buku.getNamaBuku() + " tidak cukup.", "Peringatan", JOptionPane.WARNING_MESSAGE);
                
                return false;
            }
            buku.kurangiStok();
            manajemenBuku.editBuku(buku);
        }
        
        //tambah semua buku jika aman
        p.getMhs().getDaftarBuku().addAll(p.getDaftarBukuDipinjam());
        daftarPeminjaman.put(p.getKodePeminjaman(), p);
        manajemenMahasiswa.editMhs(p.getMhs());
        fhPeminjaman.simpanData(daftarPeminjaman);
        return true;
    }

    public void hapusPeminjaman(int kodePeminjaman) {
        if (daftarPeminjaman.containsKey(kodePeminjaman)) {
            Peminjaman removedPeminjaman = daftarPeminjaman.remove(kodePeminjaman);
            fhPeminjaman.simpanData(daftarPeminjaman);
        } else {
            System.out.println("Peminjaman dengan kode " + kodePeminjaman + " tidak ditemukan dalam daftar aktif.");
        }
    }

    public Peminjaman cariPeminjaman(int kodePeminjaman) {
        this.daftarPeminjaman = fhPeminjaman.bacaData();
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
        
        tambahPeminjaman(peminjamanBaru); 

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
            peminjaman.setDikembalikan(true);

            Mahasiswa mhs = peminjaman.getMhs();
            for (Buku buku : peminjaman.getDaftarBukuDipinjam()) {
                buku.tambahStok();
                //x
                manajemenBuku.editBuku(buku);
                //x
                mhs.kembaliPinjaman(buku);
            }

            fhRiwayat.tambahRiwayat(peminjaman);
            hapusPeminjaman(kodePeminjaman);

            System.out.println("Pengembalian untuk kode peminjaman " + kodePeminjaman + " berhasil diproses dan dipindahkan ke riwayat.");

        } else if (peminjaman != null && peminjaman.getStatus().equals("Dikembalikan")) {
            System.out.println("Peminjaman dengan kode " + kodePeminjaman + " sudah dikembalikan sebelumnya.");
        } else {
            System.out.println("Peminjaman dengan kode " + kodePeminjaman + " tidak ditemukan atau sudah dikembalikan.");
        }
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

    public void setTanggalKembali() {

    }

    @Override
    public void periksaKeterlambatan(Integer kodePeminjaman) {
        this.daftarPeminjaman = fhPeminjaman.bacaData();

        if (LocalDate.now().isAfter(this.daftarPeminjaman.get(kodePeminjaman).getBatasTanggalKembali())) {
            this.daftarPeminjaman.get(kodePeminjaman).setStatus("Terlambat");

            Peminjaman pem = this.daftarPeminjaman.get(kodePeminjaman);
            pem.getMhs().setKenaDenda(true);
            pem.getMhs().setDenda(10000 * pem.getDaftarBukuDipinjam().size());
            manajemenMahasiswa.editMhs(pem.getMhs());
        } else {
            daftarPeminjaman.get(kodePeminjaman).setStatus("Dipinjam");
        }
        
        fhPeminjaman.simpanData(this.daftarPeminjaman);
    }
}
