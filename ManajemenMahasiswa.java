import services.MahasiswaService;
import models.Mahasiswa;
import java.util.Map;
import filehandler.FileHandlerMahasiswa;


public class ManajemenMahasiswa extends MahasiswaService {
    private Map<String, Mahasiswa> daftarMahasiswa;
    FileHandlerMahasiswa fhMahasiswa;

    public ManajemenMahasiswa(Map<String, Mahasiswa> daftarMahasiswa) {
        this.daftarMahasiswa = daftarMahasiswa;
        this.fhMahasiswa = new FileHandlerMahasiswa();
    }
    public void tambahMhs(Mahasiswa mhs){
        mhs.setKenaDenda();
        mhs.setDenda();
        daftarMahasiswa.put(mhs.getNim(),mhs);
        fhMahasiswa.simpanData(daftarMahasiswa);
    }
    public Mahasiswa cariMhs(String nim){
        daftarMahasiswa = fhMahasiswa.bacaData();
        return daftarMahasiswa.get(nim);
    }
    public void editMhs(String nim){
        Mahasiswa mhs = cariMhs(nim);
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

