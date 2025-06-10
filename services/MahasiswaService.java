package services;

import models.Mahasiswa;

public interface MahasiswaService {
    public void tambahMhs(Mahasiswa mhs);
    public Mahasiswa cariMhs(String nim);
    public void editMhs(Mahasiswa mhs);
    public void hapusMhs(String nim);
}
