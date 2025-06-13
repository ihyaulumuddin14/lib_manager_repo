package src.fragments;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import src.components.BasicInput;
import src.components.ComboBoxInput;
import src.components.RoundedButton;
import src.components.RoundedPanel;
import src.components.SearchInput;

public class FormInputPeminjaman extends RoundedPanel {
    final String SELECTED_NAV_BTN_COLOR = "#343a40";
    final String SELECTED_NAV_BTN_TEXT_COLOR = "#e9ecef";

    public FormInputPeminjaman() {
        super(40);
        this.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        this.setBackground(Color.WHITE);
        this.setLayout(new BorderLayout(20, 40));

        //title
        JLabel formInputTitle = new JLabel("Form Input Peminjaman");
        formInputTitle.setFont(new Font("Arial", Font.BOLD, 30));
        this.add(formInputTitle, BorderLayout.NORTH);


        //body
        JPanel formInputBody = new JPanel();
        formInputBody.setBackground(Color.WHITE);
        formInputBody.setLayout(new GridLayout(4, 2, 20, 20));

        JLabel titlePeminjam = new JLabel("Peminjam");
        titlePeminjam.setFont(new Font("Arial", Font.BOLD, 20));
        titlePeminjam.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 220)));
        formInputBody.add(titlePeminjam);

        JLabel titleBuku = new JLabel("Buku");
        titleBuku.setFont(new Font("Arial", Font.BOLD, 20));
        titleBuku.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 220)));
        formInputBody.add(titleBuku);

        SearchInput nimInput = new SearchInput("NIM", "Cari");
        formInputBody.add(nimInput);
        SearchInput kodeBukuInput = new SearchInput("Kode Buku", "Cari");
        formInputBody.add(kodeBukuInput);

        BasicInput namaMhsInput = new BasicInput("Nama Mahasiswa");
        formInputBody.add(namaMhsInput);

        BasicInput judulBukuInput = new BasicInput("Judul Buku");
        formInputBody.add(judulBukuInput);

        String[] daftarProdi = {"Teknik Informatika", "Sistem Informasi", "Teknik Komputer", "Teknologi Informasi", "Pendidikan Teknologi Informasi"};
        ComboBoxInput prodiInput = new ComboBoxInput("Program Studi", daftarProdi);
        formInputBody.add(prodiInput);

        String[] daftarKategori = {"Fantasi", "Horor", "Fiksi", "Pendidikan", "Sejarah", "Sains"};
        ComboBoxInput kategoriInput = new ComboBoxInput("Kategori", daftarKategori);
        formInputBody.add(kategoriInput);

        this.add(formInputBody, BorderLayout.CENTER);

        //side button
        JPanel formInputButton = new JPanel();
        formInputButton.setLayout(new GridLayout(4, 1, 10, 10));
        formInputButton.setPreferredSize(new Dimension(100, 0));
        formInputButton.setBackground(Color.WHITE);

        RoundedButton buttonAdd = new RoundedButton("Tambah");
        buttonAdd.setBackground(Color.decode(SELECTED_NAV_BTN_COLOR));
        buttonAdd.setForeground(Color.decode(SELECTED_NAV_BTN_TEXT_COLOR));
        buttonAdd.setMaximumSize(new Dimension(100, 50));
        formInputButton.add(buttonAdd);

        RoundedButton buttonEdit = new RoundedButton("Edit");
        buttonEdit.setBackground(Color.decode(SELECTED_NAV_BTN_COLOR));
        buttonEdit.setForeground(Color.decode(SELECTED_NAV_BTN_TEXT_COLOR));
        buttonEdit.setMaximumSize(new Dimension(100, 50));
        formInputButton.add(buttonEdit);

        RoundedButton buttonDelete = new RoundedButton("Hapus");
        buttonDelete.setBackground(Color.decode(SELECTED_NAV_BTN_COLOR));
        buttonDelete.setForeground(Color.decode(SELECTED_NAV_BTN_TEXT_COLOR));
        buttonDelete.setMaximumSize(new Dimension(100, 50));
        buttonDelete.setEnabled(false);
        formInputButton.add(buttonDelete);
        
        RoundedButton buttonClear = new RoundedButton("Clear");
        buttonClear.setBackground(Color.decode(SELECTED_NAV_BTN_COLOR));
        buttonClear.setForeground(Color.decode(SELECTED_NAV_BTN_TEXT_COLOR));
        buttonClear.setMaximumSize(new Dimension(100, 50));
        formInputButton.add(buttonClear);
        this.add(formInputButton, BorderLayout.EAST);

        JPanel formInputConfirm = new JPanel();
        formInputConfirm.setBackground(Color.WHITE);
        formInputConfirm.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));

        RoundedButton buttonCancel = new RoundedButton("Batal");
        buttonCancel.setBackground(Color.decode("#c2255c"));
        buttonCancel.setForeground(Color.decode("#ffffff"));
        buttonCancel.setEnabled(false);
        formInputConfirm.add(buttonCancel);
        
        RoundedButton buttonSave = new RoundedButton("Simpan");
        buttonSave.setBackground(Color.decode("#2f9e44"));
        buttonSave.setForeground(Color.decode("#ffffff"));
        buttonSave.setEnabled(false);
        formInputConfirm.add(buttonSave);
        this.add(formInputConfirm, BorderLayout.SOUTH);
    }
}
