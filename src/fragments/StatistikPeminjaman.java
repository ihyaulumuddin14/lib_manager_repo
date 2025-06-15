package src.fragments;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import models.Buku;
import models.Mahasiswa;
import src.components.RoundedPanel;

public class StatistikPeminjaman extends RoundedPanel {
    final String SELECTED_NAV_BTN_COLOR = "#343a40";
    JTextArea statValue;
    JLabel statStatus;

    public StatistikPeminjaman(Mahasiswa mhs) {
        super(40);
        this.setLayout(new BorderLayout(0, 20));
        this.setBackground(Color.decode(SELECTED_NAV_BTN_COLOR));
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        //header
        JLabel header = new JLabel("Statistik");
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 30));
        this.add(header, BorderLayout.NORTH);

        //body
        if (!mhs.getNim().isEmpty()) {
            Set<Buku> daftarBuku = mhs.getDaftarBuku();
            List<String> daftarNamaBuku = new ArrayList<>();
    
            for (Buku buku : daftarBuku) {
                daftarNamaBuku.add(buku.getNamaBuku());
            }
    
            statValue = new JTextArea();
            for (String buku : daftarNamaBuku) {
                statValue.append("- " + buku + "\n");
            }
            
            boolean isDenda = mhs.getKenaDenda();
            String stat = isDenda ? "Kena Denda Rp." + mhs.getDenda() : "Baik";
            statStatus = new JLabel("Status: " + stat);
        } else {
            statValue = new JTextArea("");
            statStatus = new JLabel("Status:");
        }

        statValue.setEditable(false);
        statValue.setOpaque(false);
        statValue.setForeground(Color.WHITE);
        statValue.setFont(new Font("Arial", Font.PLAIN, 16));
        this.add(statValue, BorderLayout.CENTER);

        //footer
        statStatus.setFont(new Font("Arial", Font.BOLD, 20));
        statStatus.setForeground(Color.WHITE);
        this.add(statStatus, BorderLayout.SOUTH);
    }
}
