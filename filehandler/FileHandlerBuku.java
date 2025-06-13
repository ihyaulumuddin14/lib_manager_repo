package filehandler;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import models.Buku;

public class FileHandlerBuku {
    private String NAMA_FILE = "tbl_buku.txt";
    private String DELIMITER = "|";
    private String DAFTAR_PENULIS_DELIMITER = ",";

    public FileHandlerBuku(){
    }

    public void simpanData(Map<String, Buku> daftarBuku) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(NAMA_FILE))) {
            for (String key : daftarBuku.keySet()) {
                Buku buku = daftarBuku.get(key);
                writer.write(buku.getKodeBuku() + DELIMITER);
                writer.write(buku.getNamaBuku() + DELIMITER);
                writer.write(String.join(DAFTAR_PENULIS_DELIMITER, buku.getPenulis()) + DELIMITER);
                writer.write(buku.getStok() + DELIMITER);
                writer.write(buku.getTahunTerbit() + DELIMITER);
                writer.write(buku.getKategori() + DELIMITER);
                writer.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<String, Buku> bacaData() {
        Map<String, Buku> daftarBuku = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(NAMA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(DELIMITER);
                String kodeBuku = data[0];
                String namaBuku = data[1];
                String daftarPenulis = data[2];
                int stok = Integer.parseInt(data[3]);
                int tahunTerbit = Integer.parseInt(data[4]);
                String kategori = data[5];
                    
                Buku buku = new Buku(kodeBuku, namaBuku, new HashSet<>(), stok, tahunTerbit, kategori);
                    
                String[] penulisArray = daftarPenulis.split(DAFTAR_PENULIS_DELIMITER);
                for (String penulis : penulisArray) {
                    if (!penulis.isEmpty()) {
                        buku.getPenulis().add(penulis);
                    }
                }  
                daftarBuku.put(kodeBuku, buku);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return daftarBuku;
        }
    }
}