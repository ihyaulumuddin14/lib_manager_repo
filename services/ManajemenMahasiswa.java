package services;
import filehandler.FileHandlerMahasiswa;
import java.util.Map;
import models.Mahasiswa;


public class ManajemenMahasiswa extends MahasiswaService {
    private Map<String, Mahasiswa> daftarMahasiswa;
    FileHandlerMahasiswa fhMahasiswa;

    public ManajemenMahasiswa(FileHandlerMahasiswa fhMahasiswa) {
        this.daftarMahasiswa = fhMahasiswa.bacaData();
        this.fhMahasiswa = fhMahasiswa;
    }
    @Override
    public boolean tambahMhs(Mahasiswa mhs) {
        daftarMahasiswa = fhMahasiswa.bacaData();
        if (daftarMahasiswa.containsKey(mhs.getNim())) return false;
        mhs.setKenaDenda(false);
        mhs.setDenda(0);
        daftarMahasiswa.put(mhs.getNim(),mhs);
        fhMahasiswa.simpanData(daftarMahasiswa);
        return true;
    }
    @Override
    public Mahasiswa cariMhs(String nim){
        daftarMahasiswa = fhMahasiswa.bacaData();
        return daftarMahasiswa.get(nim);
    }
    @Override
    public void editMhs(Mahasiswa mhs){
        daftarMahasiswa = fhMahasiswa.bacaData();
        String nim = mhs.getNim();
        if(mhs != null){
            daftarMahasiswa.put(nim,mhs);
            fhMahasiswa.simpanData(daftarMahasiswa);
        }
    }
    @Override
    public boolean hapusMhs(String nim){
        daftarMahasiswa = fhMahasiswa.bacaData();
        if (!daftarMahasiswa.containsKey(nim)) return false;
        daftarMahasiswa.remove(nim);
        fhMahasiswa.simpanData(daftarMahasiswa);
        return true;
    }
}

