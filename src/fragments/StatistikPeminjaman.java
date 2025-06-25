package src.fragments;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import models.Buku;
import models.Mahasiswa;
import models.Peminjaman;
import src.components.RoundedPanel;

public class StatistikPeminjaman extends RoundedPanel {
    final String SELECTED_NAV_BTN_COLOR = "#343a40";
    Mahasiswa mhs;
    Peminjaman peminjaman;
    JLabel header;
    JTextArea statValue;
    JTextArea statStatus;

    public StatistikPeminjaman(Object obj) {
        super(40);
        this.setLayout(new BorderLayout(0, 50));
        this.setBackground(Color.decode(SELECTED_NAV_BTN_COLOR));
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        //header
        header = new JLabel("Statistik");
        
        //body
        statValue = new JTextArea("""
            Daftar Buku:

            """);
            
        //footer
        statStatus = new JTextArea("Status: ");
        

        if (obj instanceof Mahasiswa) {
            mhs = (Mahasiswa) obj;
            //isi statistik jika ada data
            if (!mhs.getNim().isEmpty()) {
                
                for (Buku buku : mhs.getDaftarBuku()) {
                    statValue.append("- " + buku.getNamaBuku() + "\n");
                }
                
                boolean isDenda = mhs.getKenaDenda();
                String stat = isDenda ? "Kena Denda Rp." + mhs.getDenda() : "Baik";
                statStatus.append(stat);
            }
            
        } else if (obj instanceof Peminjaman) {
            peminjaman = (Peminjaman) obj;
            //isi statistik jika ada data
            if (peminjaman.getKodePeminjaman() != 0) {
                
                for (Buku buku : peminjaman.getDaftarBukuDipinjam()) {
                    statValue.append("- " + buku.getNamaBuku() + "\n");
                }

                statStatus.append(peminjaman.getStatus());
            }
        }
        
        //styling
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 30));
        this.add(header, BorderLayout.NORTH);

        statValue.setEditable(false);
        statValue.setOpaque(false);
        statValue.setForeground(Color.WHITE);
        statValue.setFont(new Font("Arial", Font.PLAIN, 16));
        this.add(statValue, BorderLayout.CENTER);

        statStatus.setEditable(false);
        statStatus.setOpaque(false);
        statStatus.setForeground(Color.WHITE);
        statStatus.setFont(new Font("Arial", Font.BOLD, 20));
        this.add(statStatus, BorderLayout.SOUTH);
    }
}
