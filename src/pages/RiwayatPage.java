package src.pages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import src.components.RoundedButton;
import src.components.RoundedPanel;
import src.fragments.DataScrollPane;

public class RiwayatPage extends JPanel {
    final String DEFAULT_BG_COLOR = "#868e96";
    final String SELECTED_NAV_BTN_COLOR = "#343a40";

    public RiwayatPage() {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.decode(DEFAULT_BG_COLOR));
        this.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 20));
        
        //tabel panel
        JPanel tablePanel = new RoundedPanel(40);
        tablePanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        tablePanel.setLayout(new BorderLayout(0, 40));
        tablePanel.setBackground(Color.WHITE);

        JLabel title = new JLabel("Riwayat Peminjaman");
        title.setForeground(Color.BLACK);
        title.setBackground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 30));
        tablePanel.add(title, BorderLayout.NORTH);

        // Data dummy 15 orang
        String[][] data = {
            {"P001", "Mahasiswa 1", "Buku 1", "2023-06-01", "2023-06-15", "2 hari tersisa"},
            {"P002", "Mahasiswa 2", "Buku 2", "2023-06-01", "2023-06-15", "6 hari tersisa"},
            {"P003", "Mahasiswa 3", "Buku 3", "2023-06-01", "2023-06-15", "2 hari tersisa"},
            {"P004", "Mahasiswa 4", "Buku 4", "2023-06-01", "2023-06-15", "3 hari tersisa"},
            {"P005", "Mahasiswa 5", "Buku 5", "2023-06-01", "2023-06-15", "1 hari tersisa"},
            {"P006", "Mahasiswa 6", "Buku 6", "2023-06-01", "2023-06-15", "telat 3 hari"},
        };
        
        // Column headers
        String[] columns = {"Kode Peminjaman", "Nama Mahasiswa", "Buku Dipinjam", "Tanggal Pinjam", "Jatuh Tempo", "Keterangan"};
        JScrollPane scrollPane = new DataScrollPane(columns, data);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

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
}
