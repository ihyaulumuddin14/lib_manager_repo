package filehandler;

import models.Buku;
import models.Mahasiswa;
import models.Peminjaman;
import services.ManajemenBuku;
import services.ManajemenMahasiswa;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate; 
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;

public class FIleHandlerPeminjaman { 
    private String NAMA_FILE = "database/tbl_peminjaman.txt";
    private String DELIMITER = "|";
    private String DAFTAR_BUKU_DELIMITER = ",";
    private ManajemenBuku manajemenBuku;
    private ManajemenMahasiswa manajemenMahasiswa;

    public FIleHandlerPeminjaman(ManajemenBuku manajemenBuku, ManajemenMahasiswa manajemenMahasiswa) {
        this.manajemenBuku = manajemenBuku;
        this.manajemenMahasiswa = manajemenMahasiswa;
    }

    public void simpanData(Map<Integer, Peminjaman> daftarPeminjaman) {
        File tbl_peminjaman = new File(NAMA_FILE);

        try {
            if (!tbl_peminjaman.exists()) {
                tbl_peminjaman.createNewFile();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tbl_peminjaman))) {
            for (Integer key : daftarPeminjaman.keySet()) {
                Peminjaman peminjaman = daftarPeminjaman.get(key);

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
                writer.write(peminjaman.getStatus());
                writer.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<Integer, Peminjaman> bacaData() {
        Map<Integer, Peminjaman> daftarPeminjaman = new HashMap<>();

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

                Mahasiswa mhs = manajemenMahasiswa.cariMhs(nimMahasiswa);
                if (mhs == null) {
                    System.err.println("Error: Mahasiswa dengan NIM " + nimMahasiswa + " tidak ditemukan.");
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
                            System.err.println("Error: Buku dengan kode " + kode + " tidak ditemukan.");
                        }
                    }
                }
                
                Peminjaman peminjaman = new Peminjaman(kodePeminjaman, mhs, daftarBuku, tanggalPinjam, batasTanggalKembali, status);
                peminjaman.setTanggalKembali(tanggalKembali); 
                daftarPeminjaman.put(kodePeminjaman, peminjaman);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return daftarPeminjaman;
    }
}

