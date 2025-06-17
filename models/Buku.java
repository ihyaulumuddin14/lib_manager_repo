package models;
import java.util.Set;

public class Buku {
    private String kodeBuku;
    private String namaBuku;
    private Set<String> penulis;
    private int stok;
    private int tahunTerbit;
    private String kategori;

    public Buku(String kodeBuku, String namaBuku, Set<String> penulis, int stok, int tahunTerbit, String kategori) {
        this.kodeBuku = kodeBuku;
        this.namaBuku = namaBuku;
        this.penulis = penulis;
        this.stok = stok;
        this.tahunTerbit = tahunTerbit;
        this.kategori = kategori;
    }

    public String getKodeBuku() {
        return kodeBuku;
    }
    public String getNamaBuku() {
        return namaBuku;
    }
    public void setNama(String nama) {
        this.namaBuku = nama;
    }
    public Set<String> getPenulis() {
        return penulis;
    }
    public void setPenulis(Set<String> penulis) {
        this.penulis = penulis;
    }
    public int getStok() {
        return stok;
    }
    public void setStok(int stok) {
        this.stok = stok;
    }
    public void tambahStok() {
        this.stok++;
    }
    public void kurangiStok() {
        if (this.stok > 0) {
            this.stok--;
        }
    }
    public int getTahunTerbit() {
        return tahunTerbit;
    }
    public void setTahun(int tahun) {
        this.tahunTerbit = tahun;
    }
    public String getKategori() {
        return kategori;
    }
    public void setKategori(String kategori) {
        this.kategori = kategori;
    }
}