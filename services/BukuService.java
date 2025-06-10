package services;

import models.Buku;

public interface BukuService {
    public void tambahBuku(Buku buku);
    public Buku cariBuku(String kodeBuku);
    public void editBuku(Buku buku);
    public void hapusBuku(String kodeBuku);
    public String generateKode();
}