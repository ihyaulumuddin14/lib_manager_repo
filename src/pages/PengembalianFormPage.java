package src.pages;


import filehandler.FileHandlerBuku;
import filehandler.FileHandlerMahasiswa;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import models.Buku;
import models.Mahasiswa;
import models.Peminjaman;
import services.ManajemenBuku;
import services.ManajemenMahasiswa;
import services.ManajemenPeminjaman;
import src.components.RoundedPanel;
import src.fragments.DataScrollPane;
import src.fragments.FormInputPengembalian;
import src.fragments.StatistikPeminjaman;
//

public class PengembalianFormPage extends JPanel {
    final String DEFAULT_BG_COLOR = "#868e96";
    final String SELECTED_NAV_BTN_COLOR = "#343a40";
    FormInputPengembalian formInputPanel;
    ManajemenMahasiswa manajemenMahasiswa = new ManajemenMahasiswa(new FileHandlerMahasiswa());
    ManajemenBuku manajemenBuku = new ManajemenBuku(new FileHandlerBuku());
    ManajemenPeminjaman manajemenPeminjaman = new ManajemenPeminjaman(manajemenBuku, manajemenMahasiswa);
    public static Map<String, String> daftarBuku = new HashMap<>();
    JTextArea statValue = new JTextArea();
    //
    FileHandlerMahasiswa fhMhs = new FileHandlerMahasiswa();
    ManajemenMahasiswa manajemenMhs = new ManajemenMahasiswa(fhMhs);
    //
    DataScrollPane scrollPane;    
    RoundedPanel tablePanel;
    //
    static JPanel statWrapper;
    static RoundedPanel statPanel = new RoundedPanel(40);
    //
    String[] columns = {"Kode Peminjaman", "Nama Mahasiswa", "Buku Dipinjam", "Tanggal Pinjam", "Jatuh Tempo", "Keterangan"};

    public PengembalianFormPage() {
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

        formInputPanel = new FormInputPengembalian(() -> {
            refreshStatistik(new Peminjaman());
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
        Map<Integer, Peminjaman> daftarPeminjaman = manajemenPeminjaman.fhPeminjaman.bacaData();
        String[][] data = new String[daftarPeminjaman.size()][6];
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
        scrollPane = new DataScrollPane(columns, data, (kodePeminjaman) -> {
            Peminjaman peminjaman = manajemenPeminjaman.fhPeminjaman.bacaData().get(Integer.parseInt(kodePeminjaman));
            Mahasiswa mhs = peminjaman.getMhs();
        
            formInputPanel.setFieldInput(mhs.getNim(), mhs.getNama(), mhs.getProdi());
            //x
            formInputPanel.setSelectedKodePeminjaman(kodePeminjaman);
            //x
            refreshStatistik(peminjaman);
        });
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        tablePanel.revalidate();
        tablePanel.repaint();
    }

    public static void refreshStatistik(Peminjaman pem) {
        statWrapper.remove(statPanel);
        statPanel = new StatistikPeminjaman(pem);
        statWrapper.add(statPanel, BorderLayout.CENTER);
        statWrapper.revalidate();
        statWrapper.repaint();
    }
}
