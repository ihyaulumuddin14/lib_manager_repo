package filehandler;
import models.Mahasiswa;
import java.nio.file.attribute.FileAttributeView;
import java.util.Map;

public class FileHandlerMahasiswa {
    private String NAMA_FILE = "mahasiswa.txt";
    private String DELIMITER = ",";
    private String DAFTAR_BUKU_DELIMITER = ";";

    public FileHandlerMahasiswa() {

    }
    public void simpanData(Map<String, Mahasiswa> daftarMahasiswa) {

    }
    public Map<String, Mahasiswa> bacaData() {
        return null;
    }
}



