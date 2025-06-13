package filehandler;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import models.Buku;
import models.Mahasiswa;
import services.ManajemenBuku;

public class FileHandlerMahasiswa {
    private String NAMA_FILE = "database/tbl_mahasiswa.txt";
    private String DELIMITER = "|";
    private String DAFTAR_BUKU_DELIMITER = ",";

    public void simpanData(Map<String, Mahasiswa> daftarMahasiswa) {
        File tbl_mahasiswa = new File(NAMA_FILE);

        try {
            if (!tbl_mahasiswa.exists()) {
                tbl_mahasiswa.createNewFile();
            }
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tbl_mahasiswa))) {
            for (String key : daftarMahasiswa.keySet()) {
                Mahasiswa mhs = daftarMahasiswa.get(key);

                StringJoiner daftarKodeBuku = new StringJoiner(DAFTAR_BUKU_DELIMITER);
                for (Buku buku : mhs.getDaftarBuku()) {
                    daftarKodeBuku.add(buku.getKodeBuku());
                }

                writer.write(mhs.getNim() + DELIMITER);
                writer.write(mhs.getNama() + DELIMITER);
                writer.write(mhs.getProdi() + DELIMITER);
                writer.write(daftarKodeBuku + DELIMITER);
                writer.write(mhs.getKenaDenda() + DELIMITER);
                writer.write(mhs.getDenda() + "");
                writer.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<String, Mahasiswa> bacaData() {
        Map<String, Mahasiswa> daftarMahasiswa = new HashMap<>();
        ManajemenBuku manajemenBuku = new ManajemenBuku();

        try (BufferedReader reader = new BufferedReader(new FileReader(NAMA_FILE))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\\|");
                System.out.println(Arrays.asList(data));
                String nim = data[0];
                String nama = data[1];
                String prodi = data[2];
                String daftarKodeBuku = data[3];
                boolean kenaDenda = Boolean.parseBoolean(data[4]);
                double nominalDenda = Double.parseDouble(data[5]);
                
                Mahasiswa mhs = new Mahasiswa(nim, nama, prodi);
                mhs.setKenaDenda(kenaDenda);
                mhs.setDenda(nominalDenda);

                String[] daftarKodeBukuArray = daftarKodeBuku.split(DAFTAR_BUKU_DELIMITER);
                Set<Buku> daftarBuku = new HashSet<>();

                
                for (String kode : daftarKodeBukuArray) {
                    if (!kode.isEmpty()) {
                        daftarBuku.add(manajemenBuku.cariBuku(kode));
                    }
                }
                mhs.setDaftarBuku(daftarBuku);
                daftarMahasiswa.put(nim, mhs);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return daftarMahasiswa;
    }
}




