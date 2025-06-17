package models;
import java.util.*;

public class Mahasiswa {
    private String nim = "";
    private String nama;
    private String prodi;
    private Set<Buku> daftarBuku;
    private boolean kenaDenda;
    private double nominalDenda;
    // private ManajemenBuku manajemenBuku = new ManajemenBuku(new FileHandlerBuku());

    public Mahasiswa() {}

    public Mahasiswa(String nim, String nama, String prodi) {
        this.nim = nim;
        this.nama = nama;
        this.prodi = prodi;
        this.daftarBuku = new HashSet<>();
        this.kenaDenda = false;
        this.nominalDenda = 0;
    }

    public String getNim() {
        return nim;
    }
    public String getNama(){
        return nama;
    }
    public String getProdi() {
        return prodi;
    }
    public boolean getKenaDenda() {
        return kenaDenda;
    }
    public double getDenda() {
        return nominalDenda;
    }
    public void setNama(String nama) {
        this.nama = nama;
    }
    public void setProdi(String prodi) {
        this.prodi = prodi;
    }
    public void setKenaDenda(boolean kenaDenda) {
        this.kenaDenda = kenaDenda;
    }
    public void setDenda(double nominalDenda) {
        this.nominalDenda = nominalDenda;
    }
    public void tambahDenda(double nominalDenda) {
        this.nominalDenda += nominalDenda;
    }
    public void resetDenda() {
        this.nominalDenda = 0;
    }
    public void tambahPinjaman(Buku buku) {
        this.daftarBuku.add(buku);
    }
    public void kembaliPinjaman(String kodeBuku) {
        for (Buku buku : this.daftarBuku) {
            if (buku.getKodeBuku().equals(kodeBuku)) {
                this.daftarBuku.remove(buku);
            }
        }
    }
    public void setDaftarBuku(Set<Buku> daftarBuku) {
        this.daftarBuku = daftarBuku;
    }
    public Set<Buku> getDaftarBuku() {
        return daftarBuku;
    }
}