import filehandler.FileHandlerMahasiswa;
import java.util.Map;
import models.Mahasiswa;
import services.MahasiswaService;


public class ManajemenMahasiswa extends MahasiswaService {
    private Map<String, Mahasiswa> daftarMahasiswa;
    FileHandlerMahasiswa fhMahasiswa;

    public ManajemenMahasiswa(Map<String, Mahasiswa> daftarMahasiswa) {
        this.daftarMahasiswa = daftarMahasiswa;
        this.fhMahasiswa = new FileHandlerMahasiswa();
    }
    public void tambahMhs(Mahasiswa mhs){
        mhs.setKenaDenda(false);
        mhs.setDenda(0);
        daftarMahasiswa.put(mhs.getNim(),mhs);
        fhMahasiswa.simpanData(daftarMahasiswa);
    }
    public Mahasiswa cariMhs(String nim){
        daftarMahasiswa = fhMahasiswa.bacaData();
        return daftarMahasiswa.get(nim);
    }
    public void editMhs(Mahasiswa mhs){
        String nim = mhs.getNim();
        daftarMahasiswa = fhMahasiswa.bacaData();
        if(mhs != null){
            daftarMahasiswa.put(nim,mhs);
            fhMahasiswa.simpanData(daftarMahasiswa);
        }
    }
    public void hapusMhs(String nim){
        Map<String, Mahasiswa> daftarMahasiswa = fhMahasiswa.bacaData();
        daftarMahasiswa.remove(nim);
        fhMahasiswa.simpanData(daftarMahasiswa);
    }
}

