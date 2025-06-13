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
        return null;
    }
    public void editMhs(Mahasiswa mhs){

    }
    public void hapusMhs(String nim){

    }
}
