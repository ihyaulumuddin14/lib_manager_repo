import services.BukuService;
import models.Buku;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import filehandler.FileHandlerBuku;

public class ManajemenBuku extends BukuService {
    private Map<String, Buku> daftarBuku = new HashMap<>();
    private FileHandlerBuku fhBuku = new FileHandlerBuku();

    public void tambahBuku(Buku buku) {
        daftarBuku.put(buku.getKodeBuku(), buku);
        fhBuku.simpanData(daftarBuku);
    }

    public Buku cariBuku(String kodeBuku) {
        return daftarBuku.get(kodeBuku);
    }

    public void editBuku(Buku buku) {
        if (daftarBuku.containsKey(buku.getKodeBuku())) {
            daftarBuku.put(buku.getKodeBuku(), buku);
            fhBuku.simpanData(daftarBuku);
        }
    }

    public void hapusBuku(String kodeBuku) {
        daftarBuku.remove(kodeBuku);
        fhBuku.simpanData(daftarBuku);
    }

    public String generateKode() {
        Random rand = new Random();
        String kode;
        do {
            int angkaAcak = rand.nextInt(10000); 
            kode = String.format("BK%04d", angkaAcak); 
        } while (daftarBuku.containsKey(kode));
        return kode;
    }

    public Map<String, Buku> getDaftarBuku() {
        return new HashMap<>(daftarBuku); 
    }

    public void simpanKeFile() {
        fhBuku.simpanData(daftarBuku);
    }
}