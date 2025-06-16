package src.pages;

import filehandler.FileHandlerMahasiswa;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import models.Mahasiswa;
import services.ManajemenMahasiswa;
import src.components.RoundedPanel;
import src.fragments.DataScrollPane;
import src.fragments.FormInputMhs;
import src.fragments.StatistikPeminjaman;

public class MahasiswaFormPage extends JPanel {
    final String DEFAULT_BG_COLOR = "#868e96";
    final String SELECTED_NAV_BTN_COLOR = "#343a40";
    String[] columns = {"NIM", "Nama", "Program Studi", "Status"};
    RoundedPanel tablePanel;
    DataScrollPane scrollPane;
    FormInputMhs formInputPanel;
    static RoundedPanel statPanel;
    static JPanel statWrapper;
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
        formInputPanel = new FormInputMhs(() -> {
            refreshTable();
        });
        formInputWrapper.add(formInputPanel, BorderLayout.CENTER);



        //statistik
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.3;
        gbc.fill = GridBagConstraints.BOTH;
        statWrapper = new JPanel();
        statWrapper.setLayout(new BorderLayout());
        statWrapper.setBackground(Color.decode(DEFAULT_BG_COLOR));
        statWrapper.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        Mahasiswa mhs = new Mahasiswa();
        statPanel = new StatistikPeminjaman(mhs);
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
            scrollPane = new DataScrollPane(columns, data, (nimData) -> {
            String nim = nimData;

            Mahasiswa mhs = manajemenMhs.cariMhs(nim);

            formInputPanel.setFieldInput(mhs.getNim(), mhs.getNama(), mhs.getProdi());

            refreshStatistik(mhs);
        });
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        tablePanel.revalidate();
        tablePanel.repaint();
    }

    public static void refreshStatistik(Mahasiswa mhs) {
        statWrapper.remove(statPanel);
        statPanel = new StatistikPeminjaman(mhs);
        statWrapper.add(statPanel, BorderLayout.CENTER);
        statWrapper.revalidate();
        statWrapper.repaint();
    }
}
