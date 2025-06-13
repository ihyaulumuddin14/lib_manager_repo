package src.pages;

import filehandler.FileHandlerMahasiswa;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import models.Mahasiswa;
import services.ManajemenMahasiswa;
import src.components.RoundedPanel;
import src.fragments.DataScrollPane;
import src.fragments.FormInputMhs;

public class MahasiswaFormPage extends JPanel {
    final String DEFAULT_BG_COLOR = "#868e96";
    final String SELECTED_NAV_BTN_COLOR = "#343a40";
    String[] columns = {"NIM", "Nama", "Program Studi", "Status"};
    RoundedPanel tablePanel;
    DataScrollPane scrollPane;
    FileHandlerMahasiswa fhMhs = new FileHandlerMahasiswa();
    ManajemenMahasiswa manajemenMhs = new ManajemenMahasiswa(fhMhs);

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
        JPanel formInputPanel = new FormInputMhs(() -> {
            refreshTable();
        });
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
        tablePanel = new RoundedPanel(40);
        tablePanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        
        refreshTable();
        tableWrapper.add(tablePanel, BorderLayout.CENTER);
        this.add(tableWrapper, gbc);
    }

    public void refreshTable() {
        Map<String, Mahasiswa> daftarMahasiswa = fhMhs.bacaData();
        String[][] data = new String[daftarMahasiswa.size()][4];
        int i = 0;

        for (Map.Entry<String, Mahasiswa> mhs : daftarMahasiswa.entrySet()) {
            String nim = mhs.getKey();
            String nama = mhs.getValue().getNama();
            String prodi = mhs.getValue().getProdi();
            String status = mhs.getValue().getKenaDenda() ? "Kena Denda" : "Baik";
            
            String[] dataMhsPerRow = {nim, nama, prodi, status};
            data[i++] = dataMhsPerRow;
        }

        if (scrollPane != null) tablePanel.remove(scrollPane);
        scrollPane = new DataScrollPane(columns, data);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        tablePanel.revalidate();
        tablePanel.repaint();
    }
}
