package src.pages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Arrays;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import src.components.RoundedPanel;
import src.fragments.DataScrollPane;
import src.fragments.FormInputMhs;

public class MahasiswaFormPage extends JPanel {
    final String DEFAULT_BG_COLOR = "#868e96";
    final String SELECTED_NAV_BTN_COLOR = "#343a40";

    public MahasiswaFormPage() {
        this.setLayout(new GridBagLayout());
        this.setBackground(Color.decode(DEFAULT_BG_COLOR));
        GridBagConstraints gbc = new GridBagConstraints();

        
        //form input wrapper
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JPanel formInputWrapper = new JPanel();
        formInputWrapper.setLayout(new BorderLayout());
        formInputWrapper.setBackground(Color.decode(DEFAULT_BG_COLOR));
        formInputWrapper.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        this.add(formInputWrapper, gbc);

        //form input panel yang warna putih rounded
        JPanel formInputPanel = new FormInputMhs();
        formInputWrapper.add(formInputPanel, BorderLayout.CENTER);



        //statistik
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.BOTH;
        JPanel statWrapper = new JPanel();
        statWrapper.setLayout(new BorderLayout());
        statWrapper.setBackground(Color.decode(DEFAULT_BG_COLOR));
        statWrapper.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JPanel statPanel = new RoundedPanel(40);
        statPanel.setLayout(new BorderLayout(0, 20));
        statPanel.setBackground(Color.decode(SELECTED_NAV_BTN_COLOR));
        statPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        //header
        JLabel statLabel = new JLabel("Buku Sedang Dipinjam: ");
        statLabel.setForeground(Color.WHITE);
        statLabel.setFont(new Font("Arial", Font.BOLD, 20));
        statPanel.add(statLabel, BorderLayout.NORTH);

        //body
        String[] daftarBuku = {"Buku 1", "Buku 2", "Buku 3", "Buku 4", "Buku 5"};
        List<String> daftarBukuDummy = Arrays.asList(daftarBuku);

        JTextArea statValue = new JTextArea();
        for (String buku : daftarBukuDummy) {
            statValue.append("- " + buku + "\n");
        }

        statValue.setEditable(false);
        statValue.setOpaque(false);
        statValue.setForeground(Color.WHITE);
        statValue.setFont(new Font("Arial", Font.PLAIN, 16));
        statPanel.add(statValue, BorderLayout.CENTER);

        //footer
        boolean isDenda = false;
        String stat = isDenda ? "Kena Denda" : "Baik";
        JLabel statStatus = new JLabel("Status: " + stat);
        statStatus.setFont(new Font("Arial", Font.BOLD, 20));
        statStatus.setForeground(Color.WHITE);
        statPanel.add(statStatus, BorderLayout.SOUTH);

        statWrapper.add(statPanel, BorderLayout.CENTER);
        this.add(statWrapper, gbc);
        
        
        //tabel wrapper
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        JPanel tableWrapper = new JPanel();
        tableWrapper.setLayout(new BorderLayout());
        tableWrapper.setBackground(Color.decode(DEFAULT_BG_COLOR));
        tableWrapper.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 20));

        //tabel panel
        JPanel tablePanel = new RoundedPanel(40);
        tablePanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);

        // Data dummy 15 orang
        String[][] data = {
            {"24510001", "Ahmad Rizky Pratama", "Teknik Informatika"},
            {"24510002", "Siti Nurhaliza Putri", "Sistem Informasi"},
            {"24510003", "Budi Santoso", "Teknik Elektro"},
            {"24510004", "Dewi Kartika Sari", "Manajemen"},
            {"24510005", "Eko Prasetyo", "Akuntansi"},
            {"24510006", "Fitri Handayani", "Teknik Sipil"},
            {"24510007", "Gunawan Setiawan", "Teknik Mesin"},
            {"24510008", "Hana Maulida", "Psikologi"},
            {"24510009", "Indra Kurniawan", "Ekonomi"},
            {"24510010", "Joko Widodo", "Teknik Industri"},
            {"24510011", "Kartika Dewi", "Farmasi"},
            {"24510012", "Lukman Hakim", "Kedokteran"},
            {"24510013", "Maya Sari", "Hukum"},
            {"24510014", "Naufal Ahmad", "Arsitektur"},
            {"24510015", "Octavia Putri", "Desain Grafis"}
        };
        
        // Column headers
        String[] columns = {"NIM", "Nama", "Program Studi", "Status"};
        JScrollPane scrollPane = new DataScrollPane(columns, data);
        
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        tableWrapper.add(tablePanel, BorderLayout.CENTER);
        this.add(tableWrapper, gbc);
    }
}
