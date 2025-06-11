package models;
import java.util.*;

public class Mahasiswa {
    private String nim;
    private String nama;
    private String prodi;
    private Set<Buku> daftarBuku;
    private boolean kenaDenda;
    private double nominalDenda;

    public Mahasiswa(String nim, String prodi) {
        this.nim = nim;
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
    public void setKenaDenda() {
        
    }
    public void setDenda() {
        
    }
    public void tambahPinjaman(Buku buku) {
        this.daftarBuku.add(buku);
    }
    public void kembaliPinjaman(Buku buku) {
        this.daftarBuku.remove(buku);
    }
    public String toString(){
        return "";
    }
}