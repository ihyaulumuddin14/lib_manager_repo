package services;
import filehandler.FileHandlerBuku;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import models.Buku;

public class ManajemenBuku extends BukuService {
    private Map<String, Buku> daftarBuku = new HashMap<>();
    private FileHandlerBuku fhBuku;

    public ManajemenBuku(FileHandlerBuku fhBuku) {
        this.fhBuku = fhBuku;
        this.daftarBuku = fhBuku.bacaData();
    }

    @Override
    public boolean tambahBuku(Buku buku) {
        daftarBuku = fhBuku.bacaData();
        if (daftarBuku.containsKey(buku.getKodeBuku())) return false;
        daftarBuku.put(buku.getKodeBuku(), buku);
        fhBuku.simpanData(daftarBuku);
        return true;
    }

    @Override
    public Buku cariBuku(String kodeBuku) {
        daftarBuku = fhBuku.bacaData();
        return daftarBuku.get(kodeBuku);
    }

    @Override
    public void editBuku(Buku buku) {
        daftarBuku = fhBuku.bacaData();
        if (daftarBuku.containsKey(buku.getKodeBuku())) {
            daftarBuku.put(buku.getKodeBuku(), buku);
            fhBuku.simpanData(daftarBuku);
        }
    }

    @Override
    public boolean hapusBuku(String kodeBuku) {
        daftarBuku = fhBuku.bacaData();
        if (!daftarBuku.containsKey(kodeBuku)) return false;
        daftarBuku.remove(kodeBuku);
        fhBuku.simpanData(daftarBuku);
        return true;
    }

    @Override
    public String generateKode() {
        daftarBuku = fhBuku.bacaData();
        Random rand = new Random();
        String kode;
        do {
            int angkaAcak = rand.nextInt(10000); 
            kode = String.format("BK%04d", angkaAcak); 
        } while (daftarBuku.containsKey(kode));
        return kode;
    }
}