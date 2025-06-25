package filehandler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;
import models.Buku;
import models.Mahasiswa;
import models.Peminjaman;
import services.ManajemenBuku;
import services.ManajemenMahasiswa;

public class FileHandlerRiwayat {
    private String NAMA_FILE = "database/tbl_riwayat_peminjaman.txt";
    private String DELIMITER = "|";
    private String DAFTAR_BUKU_DELIMITER = ",";

    private ManajemenBuku manajemenBuku;
    private ManajemenMahasiswa manajemenMahasiswa;

    public FileHandlerRiwayat(ManajemenBuku manajemenBuku, ManajemenMahasiswa manajemenMahasiswa) {
        this.manajemenBuku = manajemenBuku;
        this.manajemenMahasiswa = manajemenMahasiswa;
    }

    public void tambahRiwayat(Peminjaman peminjaman) {
        File tbl_riwayat_peminjaman = new File(NAMA_FILE);

        try {
            if (!tbl_riwayat_peminjaman.exists()) {
                tbl_riwayat_peminjaman.createNewFile();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tbl_riwayat_peminjaman, true))) { 
            StringJoiner daftarKodeBuku = new StringJoiner(DAFTAR_BUKU_DELIMITER);
            for (Buku buku : peminjaman.getDaftarBukuDipinjam()) {
                daftarKodeBuku.add(buku.getKodeBuku());
            }
            writer.write(peminjaman.getKodePeminjaman() + DELIMITER);
            writer.write(peminjaman.getMhs().getNim() + DELIMITER);
            writer.write(daftarKodeBuku.toString() + DELIMITER);
            writer.write(peminjaman.getTanggalPinjam().toString() + DELIMITER);
            writer.write(peminjaman.getBatasTanggalKembali().toString() + DELIMITER);
            writer.write((peminjaman.getTanggalKembali() != null ? peminjaman.getTanggalKembali().toString() : "null") + DELIMITER);
            writer.write(peminjaman.getStatus() + DELIMITER);
            writer.write(peminjaman.isDikembalikan() + ""); 
            writer.newLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Peminjaman> bacaRiwayat() {
        List<Peminjaman> riwayatPeminjaman = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(NAMA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\\|");
                int kodePeminjaman = Integer.parseInt(data[0]);
                String nimMahasiswa = data[1];
                String daftarKodeBukuStr = data[2];
                LocalDate tanggalPinjam = LocalDate.parse(data[3]);
                LocalDate batasTanggalKembali = LocalDate.parse(data[4]);
                LocalDate tanggalKembali = data[5].equals("null") ? null : LocalDate.parse(data[5]);
                String status = data[6];
                boolean isDikembalikan = Boolean.parseBoolean(data[7]);

                Mahasiswa mhs = manajemenMahasiswa.cariMhs(nimMahasiswa);
                if (mhs == null) {
                    System.err.println("Error: Mahasiswa dengan NIM " + nimMahasiswa + " tidak ditemukan dalam riwayat.");
                    continue;
                }

                Set<Buku> daftarBuku = new HashSet<>();
                String[] kodeBukuArray = daftarKodeBukuStr.split(DAFTAR_BUKU_DELIMITER);
                for (String kode : kodeBukuArray) {
                    if (!kode.isEmpty()) {
                        Buku buku = manajemenBuku.cariBuku(kode);
                        if (buku != null) {
                            daftarBuku.add(buku);
                        } else {
                            System.err.println("Error: Buku dengan kode " + kode + " tidak ditemukan dalam riwayat.");
                        }
                    }
                }
                
                Peminjaman peminjaman = new Peminjaman(kodePeminjaman, mhs, daftarBuku, tanggalPinjam, batasTanggalKembali, status);
                peminjaman.setTanggalKembali(tanggalKembali);
                peminjaman.setDikembalikan(isDikembalikan);
                riwayatPeminjaman.add(peminjaman);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return riwayatPeminjaman;
    }

    public void hapusRiwayat() {
        File tbl_riwayat_peminjaman = new File(NAMA_FILE);
        if (tbl_riwayat_peminjaman.exists()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(tbl_riwayat_peminjaman))) {
                writer.write("");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("File riwayat peminjaman tidak ditemukan.");
        }
    }
}

