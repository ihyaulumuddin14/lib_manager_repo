package src.pages;

import filehandler.FileHandlerBuku;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import models.Buku;
import services.ManajemenBuku;
import src.components.RoundedPanel;
import src.fragments.DataScrollPane;
import src.fragments.FormInputBuku;

public class BukuFormPage extends JPanel {
    final String DEFAULT_BG_COLOR = "#868e96";
    final String SELECTED_NAV_BTN_COLOR = "#343a40";
    String[] columns = {"Kode Buku", "Nama Buku", "Penulis", "Stok", "Tahun Terbit", "Kategori"};
    RoundedPanel tablePanel;
    DataScrollPane scrollPane;
    FormInputBuku formInputPanel;
    FileHandlerBuku fhBuku = new FileHandlerBuku();
    ManajemenBuku manajemenBuku = new ManajemenBuku(fhBuku);

    public BukuFormPage() {
        this.setLayout(new GridBagLayout());
        this.setBackground(Color.decode(DEFAULT_BG_COLOR));
        GridBagConstraints gbc = new GridBagConstraints();

        
        //form input wrapper
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JPanel formInputWrapper = new JPanel();
        formInputWrapper.setLayout(new BorderLayout());
        formInputWrapper.setBackground(Color.decode(DEFAULT_BG_COLOR));
        formInputWrapper.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 20));
        this.add(formInputWrapper, gbc);

        //form input panel yang warna putih rounded
        formInputPanel = new FormInputBuku(() -> {
            refreshTable();
        });
        formInputWrapper.add(formInputPanel, BorderLayout.CENTER);        
         
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
        Map<String, Buku> daftarBuku = fhBuku.bacaData();
        String[][] data = new String[daftarBuku.size()][4];
        int i = 0;

        for (Map.Entry<String, Buku> buku : daftarBuku.entrySet()) {
            String kodeBuku = buku.getKey();
            String namaBuku = buku.getValue().getNamaBuku();
            String penulis = String.join(", ", buku.getValue().getPenulis());
            String stok = String.valueOf(buku.getValue().getStok());
            String tahunTerbit = String.valueOf(buku.getValue().getTahunTerbit());
            String kategori = buku.getValue().getKategori();

            String[] dataBukuPerRow = {kodeBuku, namaBuku, penulis, stok, tahunTerbit, kategori};
            data[i++] = dataBukuPerRow;
        }

        if (scrollPane != null) tablePanel.remove(scrollPane);
            scrollPane = new DataScrollPane(columns, data, (kodeBuku) -> {
            String kode = kodeBuku;

            Buku buku = manajemenBuku.cariBuku(kode);
            formInputPanel.setFieldInput(buku.getKodeBuku(), buku.getNamaBuku(), String.join(",", buku.getPenulis()), String.valueOf(buku.getStok()), String.valueOf(buku.getTahunTerbit()), buku.getKategori());
        });
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        tablePanel.revalidate();
        tablePanel.repaint();
    }
}
