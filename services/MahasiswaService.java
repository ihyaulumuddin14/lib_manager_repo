package services;

import models.Mahasiswa;

public abstract class MahasiswaService {
    public abstract void tambahMhs(Mahasiswa mhs);
    public abstract Mahasiswa cariMhs(String nim);
    public abstract void editMhs(Mahasiswa mhs);
    public abstract void hapusMhs(String nim);
}
