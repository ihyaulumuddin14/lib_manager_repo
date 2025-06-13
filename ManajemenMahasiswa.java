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

    }
    public Mahasiswa cariMhs(String nim){
        return null;
    }
    public void editMhs(Mahasiswa mhs){

    }
    public void hapusMhs(String nim){

    }
}
