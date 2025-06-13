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

