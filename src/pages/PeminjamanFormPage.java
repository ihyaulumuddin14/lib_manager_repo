package src.pages;

import filehandler.FileHandlerBuku;
import filehandler.FileHandlerMahasiswa;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import models.Buku;
import models.Peminjaman;
import services.ManajemenBuku;
import services.ManajemenMahasiswa;
import services.ManajemenPeminjaman;
import src.components.RoundedPanel;
import src.custom_listener.AddBookOnCart;
import src.fragments.DataScrollPane;
import src.fragments.FormInputPeminjaman;

public class PeminjamanFormPage extends JPanel {
    final String DEFAULT_BG_COLOR = "#868e96";
    final String SELECTED_NAV_BTN_COLOR = "#343a40";
    FormInputPeminjaman formInputPanel;
    ManajemenMahasiswa manajemenMahasiswa = new ManajemenMahasiswa(new FileHandlerMahasiswa());
    ManajemenBuku manajemenBuku = new ManajemenBuku(new FileHandlerBuku());
    ManajemenPeminjaman manajemenPeminjaman = new ManajemenPeminjaman(manajemenBuku, manajemenMahasiswa);
    public static Map<String, String> daftarBuku = new HashMap<>();
    JTextArea statValue = new JTextArea();
    DataScrollPane scrollPane;    
    RoundedPanel tablePanel;
    RoundedPanel statPanel = new RoundedPanel(40);
    String[] columns = {"Kode Peminjaman", "Nama Mahasiswa", "Buku Dipinjam", "Tanggal Pinjam", "Jatuh Tempo", "Keterangan"};

    public PeminjamanFormPage() {
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

        formInputPanel = new FormInputPeminjaman(() -> {
            daftarBuku.clear();
            loadKeranjang();
            refreshTable();
        });
        formInputPanel.setOnCart(new AddBookOnCart() {
            @Override
            public void onAdd(String kode) {
                daftarBuku.put(kode, manajemenBuku.cariBuku(kode).getNamaBuku());
                loadKeranjang();
            }
        });
        formInputWrapper.add(formInputPanel, BorderLayout.CENTER);



        //keranjang buku
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.3;
        gbc.fill = GridBagConstraints.BOTH;
        JPanel statWrapper = new JPanel();
        statWrapper.setLayout(new BorderLayout());
        statWrapper.setBackground(Color.decode(DEFAULT_BG_COLOR));
        statWrapper.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        statPanel.setLayout(new BorderLayout(0, 50));
        statPanel.setBackground(Color.decode(SELECTED_NAV_BTN_COLOR));
        statPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        //header
        JLabel statLabel = new JLabel("Keranjang Buku:");
        statLabel.setForeground(Color.WHITE);
        statLabel.setFont(new Font("Arial", Font.BOLD, 30));
        statPanel.add(statLabel, BorderLayout.NORTH);

        //body
        loadKeranjang();

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
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        tableWrapper.add(tablePanel, BorderLayout.CENTER);
        this.add(tableWrapper, gbc);
    }

    private void loadKeranjang() {
        statValue.setText("");
        List<String> daftarBukuList = new ArrayList<>();

        for (Map.Entry<String, String> buku : daftarBuku.entrySet()) {
            daftarBukuList.add(buku.getValue());
        }

        statValue = new JTextArea();
        statValue.setEditable(false);
        statValue.setOpaque(false);
        statValue.setForeground(Color.WHITE);
        statValue.setFont(new Font("Arial", Font.PLAIN, 16));

        for (String buku : daftarBukuList) {
            statValue.append("- " + buku + "\n");
        }

        statPanel.add(statValue, BorderLayout.CENTER);
        statPanel.revalidate();
        statPanel.repaint(); 
    }

    private void refreshTable() {
        Map<Integer, Peminjaman> daftarPeminjaman = manajemenPeminjaman.fhPeminjaman.bacaData();
        String[][] data = new String[daftarPeminjaman.size()][4];
        int i = 0;

        for (Map.Entry<Integer, Peminjaman> pem : daftarPeminjaman.entrySet()) {
            String kodePeminjaman = pem.getKey().toString();
            String nama = pem.getValue().getMhs().getNama();
            List<String> daftarBukuList = new ArrayList<>();
            for (Buku buku : pem.getValue().getDaftarBukuDipinjam()) {
                daftarBukuList.add(buku.getNamaBuku());
            }
            String buku = String.join(", ", daftarBukuList);
            String tanggalPinjam = pem.getValue().getTanggalPinjam().toString();
            String jatuhTempo = pem.getValue().getBatasTanggalKembali().toString();

            manajemenPeminjaman.periksaKeterlambatan(Integer.valueOf(kodePeminjaman));
            String status = pem.getValue().getStatus();
            
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
