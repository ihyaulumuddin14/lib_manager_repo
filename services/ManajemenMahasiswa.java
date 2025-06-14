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
        if (daftarMahasiswa.containsKey(mhs.getNim())) {
            return false;
        }
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
        String nim = mhs.getNim();
        daftarMahasiswa = fhMahasiswa.bacaData();
        if(mhs != null){
            daftarMahasiswa.put(nim,mhs);
            fhMahasiswa.simpanData(daftarMahasiswa);
        }
    }
    @Override
    public void hapusMhs(String nim){
        Map<String, Mahasiswa> daftarMahasiswa = fhMahasiswa.bacaData();
        daftarMahasiswa.remove(nim);
        fhMahasiswa.simpanData(daftarMahasiswa);
    }
}

