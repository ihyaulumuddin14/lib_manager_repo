package src.pages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import src.components.RoundedPanel;
import src.fragments.DataScrollPane;
import src.fragments.FormInputBuku;

public class BukuFormPage extends JPanel {
    final String DEFAULT_BG_COLOR = "#868e96";
    final String SELECTED_NAV_BTN_COLOR = "#343a40";

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
        JPanel formInputPanel = new FormInputBuku();
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
        JPanel tablePanel = new RoundedPanel(40);
        tablePanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);

        // Data dummy 15 orang
        String[][] data = {
            {"B001", "Pemrograman Web", "Dwi Sutrisno", "15", "2022", "Pendidikan"},
            {"B002", "Pemrograman Mobile", "Dwi Sutrisno", "15", "2022", "Pendidikan"},
            {"B003", "Pemrograman Desktop", "Dwi Sutrisno", "15", "2022", "Pendidikan"},
        };
        
        // Column headers
        String[] columns = {"Kode Buku", "Nama Buku", "Penulis", "Stok", "Tahun Terbit", "Kategori"};
        JScrollPane scrollPane = new DataScrollPane(columns, data);
        
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        tableWrapper.add(tablePanel, BorderLayout.CENTER);
        this.add(tableWrapper, gbc);
    }
}
