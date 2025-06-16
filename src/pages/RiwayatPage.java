package src.pages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import filehandler.FileHandlerBuku;
import filehandler.FileHandlerMahasiswa;
import models.Buku;
import models.Peminjaman;
import src.components.RoundedButton;
import src.components.RoundedPanel;
import src.fragments.DataScrollPane;
import services.ManajemenBuku;
import services.ManajemenMahasiswa;
import services.ManajemenPeminjaman;
import src.fragments.FormInputPeminjaman;

public class RiwayatPage extends JPanel {
    FormInputPeminjaman formInputPanel;
    ManajemenMahasiswa manajemenMahasiswa = new ManajemenMahasiswa(new FileHandlerMahasiswa());
    ManajemenBuku manajemenBuku = new ManajemenBuku(new FileHandlerBuku());
    ManajemenPeminjaman manajemenPeminjaman = new ManajemenPeminjaman(manajemenBuku, manajemenMahasiswa);
    JScrollPane scrollPane;
    RoundedPanel tablePanel;
    final String DEFAULT_BG_COLOR = "#868e96";
    final String SELECTED_NAV_BTN_COLOR = "#343a40";

        String[] columns = {"Kode Peminjaman", "Nama Mahasiswa", "Buku Dipinjam", "Tanggal Pinjam", "Jatuh Tempo", "Keterangan"};

    public RiwayatPage() {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.decode(DEFAULT_BG_COLOR));
        this.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 20));
        
        //tabel panel
        tablePanel = new RoundedPanel(40);
        tablePanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        tablePanel.setLayout(new BorderLayout(0, 40));
        tablePanel.setBackground(Color.WHITE);

        JLabel title = new JLabel("Riwayat Peminjaman");
        title.setForeground(Color.BLACK);
        title.setBackground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 30));
        tablePanel.add(title, BorderLayout.NORTH);

        refreshTable();
        // Column headers


        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(Color.WHITE);
        tablePanel.add(buttonPanel, BorderLayout.SOUTH);

        RoundedButton deleteButton = new RoundedButton("Hapus");
        deleteButton.setBackground(Color.decode(SELECTED_NAV_BTN_COLOR));
        deleteButton.setForeground(Color.WHITE);
        buttonPanel.add(deleteButton);

        RoundedButton deleteAllButton = new RoundedButton("Hapus Semua");
        deleteAllButton.setBackground(Color.decode(SELECTED_NAV_BTN_COLOR));
        deleteAllButton.setForeground(Color.WHITE);
        buttonPanel.add(deleteAllButton);

        this.add(tablePanel, BorderLayout.CENTER);
    }
    
    public void refreshTable() {
        List<Peminjaman> daftarPeminjaman = manajemenPeminjaman.fhRiwayat.bacaRiwayat();   
        String[][] data = new String[daftarPeminjaman.size()][6];  
        int i = 0;   
        for (Peminjaman pem : daftarPeminjaman) {    
        if (pem == null) {      
        System.out.println("Peminjaman tidak ditemukan");
        
        continue;
        
        }
        
        String kodePeminjaman = Integer.toString(pem.getKodePeminjaman());
        
        String nama = pem.getMhs().getNama();
        
        List<String> daftarBukuList = new ArrayList<>();
        
        for (Buku buku : pem.getDaftarBukuDipinjam()) {
        
        daftarBukuList.add(buku.getNamaBuku());
        
        }
        
        String buku = String.join(", ", daftarBukuList);
        
        String tanggalPinjam = pem.getTanggalPinjam().toString();
        
        String jatuhTempo = pem.getBatasTanggalKembali().toString();
      
        String status = pem.getStatus();
        
        String[] dataPeminjamanPerRow = {kodePeminjaman, nama, buku, tanggalPinjam, jatuhTempo, status};
        
        data[i++] = dataPeminjamanPerRow;
        
        } 


        if (scrollPane != null) tablePanel.remove(scrollPane);

        scrollPane = new DataScrollPane(columns, data, (kodePeminjaman) -> {});
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        tablePanel.revalidate();
        tablePanel.repaint();
    }
}
