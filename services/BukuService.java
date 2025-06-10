package services;

import models.Buku;

public abstract class BukuService {
    public abstract void tambahBuku(Buku buku);
    public abstract Buku cariBuku(String kodeBuku);
    public abstract void editBuku(Buku buku);
    public abstract void hapusBuku(String kodeBuku);
    public abstract String generateKode();
}