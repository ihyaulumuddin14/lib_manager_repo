package src.fragments;

import filehandler.FileHandlerBuku;
import filehandler.FileHandlerMahasiswa;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import models.Buku;
import models.Mahasiswa;
import services.ManajemenBuku;
import services.ManajemenMahasiswa;
import services.ManajemenPeminjaman;
import src.components.BasicInput;
import src.components.ComboBoxInput;
import src.components.RoundedButton;
import src.components.RoundedPanel;
import src.components.SearchInput;
import src.custom_listener.AddBookOnCart;
import src.pages.PeminjamanFormPage;

public class FormInputPeminjaman extends RoundedPanel {
    final String SELECTED_NAV_BTN_COLOR = "#343a40";
    final String SELECTED_NAV_BTN_TEXT_COLOR = "#e9ecef";
    SearchInput inputNim = new SearchInput("NIM", "Cari");
    SearchInput inputKodeBuku = new SearchInput("Kode Buku", "Cari");
    BasicInput inputNamaMhs = new BasicInput("Nama Mahasiswa");
    BasicInput inputJudulBuku = new BasicInput("Judul Buku");
    ComboBoxInput inputProdi = new ComboBoxInput("Program Studi", new String[]{"Teknik Informatika", "Sistem Informasi", "Teknik Komputer", "Teknologi Informasi", "Pendidikan Teknologi Informasi"});
    ComboBoxInput inputKategori = new ComboBoxInput("Kategori", new String[]{"Fantasi", "Horor", "Fiksi", "Pendidikan", "Sejarah", "Sains"});
    RoundedButton buttonAdd = new RoundedButton("Buat Pinjaman");
    RoundedButton buttonAddBook = new RoundedButton("Tambah Buku");
    RoundedButton buttonClear = new RoundedButton("Clear");
    RoundedButton buttonCancel = new RoundedButton("Batal");
    RoundedButton buttonSave = new RoundedButton("Simpan");
    FileHandlerMahasiswa fhMhs = new FileHandlerMahasiswa();
    ManajemenMahasiswa manajemenMhs = new ManajemenMahasiswa(fhMhs);
    FileHandlerBuku fhBuku = new FileHandlerBuku();
    ManajemenBuku manajemenBuku = new ManajemenBuku(fhBuku);
    ManajemenPeminjaman manajemenPeminjaman = new ManajemenPeminjaman(manajemenBuku, manajemenMhs);
    Runnable onSaveSuccess;
    AddBookOnCart addBookOnCart;
    String mode = "";

    public FormInputPeminjaman(Runnable onSaveSuccess) {
        super(40);

        this.onSaveSuccess = onSaveSuccess;
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

        inputNim.setSearchListener((String nim) -> {
            Mahasiswa mhs = manajemenMhs.cariMhs(nim);
            if (mhs != null) {
                inputNim.setInputText(mhs.getNim());
                inputNamaMhs.setInputText(mhs.getNama());
                inputProdi.setInputText(mhs.getProdi());
            } else {
                JOptionPane.showMessageDialog(null, "Mahasiswa dengan NIM " + nim + " tidak ditemukan.");
            }
        });
        inputNim.setInputEnabled(true);
        inputNamaMhs.setInputEnabled(false);
        inputProdi.setInputEnabled(false);

        inputKodeBuku.setSearchListener((String kode) -> {
            Buku buku = manajemenBuku.cariBuku(kode);
            if (buku != null) {
                inputKodeBuku.setInputText(buku.getKodeBuku());
                inputJudulBuku.setInputText(buku.getNamaBuku());
                inputKategori.setInputText(buku.getKategori());
            }
        });
        inputKodeBuku.setInputEnabled(false);
        inputJudulBuku.setInputEnabled(false);
        inputKategori.setInputEnabled(false);

        formInputBody.add(inputNim);
        formInputBody.add(inputKodeBuku);
        formInputBody.add(inputNamaMhs);
        formInputBody.add(inputJudulBuku);
        formInputBody.add(inputProdi);
        formInputBody.add(inputKategori);

        this.add(formInputBody, BorderLayout.CENTER);

        //side button
        JPanel formInputButton = new JPanel();
        formInputButton.setLayout(new GridLayout(4, 1, 10, 10));
        formInputButton.setPreferredSize(new Dimension(100, 0));
        formInputButton.setBackground(Color.WHITE);

        buttonAdd.addActionListener(e -> {
            if (!(inputNim.getInputText().isEmpty() || inputNamaMhs.getInputText().isEmpty() || inputProdi.getInputText().isEmpty())) {
                setMode("add");
            } else {
                JOptionPane.showMessageDialog(null, "Peminjam harus diisi");
            }
        });

        buttonAdd.setBackground(Color.decode(SELECTED_NAV_BTN_COLOR));
        buttonAdd.setForeground(Color.decode(SELECTED_NAV_BTN_TEXT_COLOR));
        buttonAdd.setMaximumSize(new Dimension(100, 50));
        formInputButton.add(buttonAdd);

        buttonAddBook.addActionListener(e -> {
            if (!(inputKodeBuku.getInputText().isEmpty() || inputJudulBuku.getInputText().isEmpty() || inputKategori.getInputText().isEmpty())) {
                if (addBookOnCart != null) {
                    addBookOnCart.onAdd(inputKodeBuku.getInputText());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Tidak ada buku terdeteksi");
            }
        });

        buttonAddBook.setBackground(Color.decode(SELECTED_NAV_BTN_COLOR));
        buttonAddBook.setForeground(Color.decode(SELECTED_NAV_BTN_TEXT_COLOR));
        buttonAddBook.setMaximumSize(new Dimension(100, 50));
        buttonAddBook.setEnabled(false);
        formInputButton.add(buttonAddBook);

        buttonClear.addActionListener(e -> {
            clearForm();
        });
        
        buttonClear.setBackground(Color.decode(SELECTED_NAV_BTN_COLOR));
        buttonClear.setForeground(Color.decode(SELECTED_NAV_BTN_TEXT_COLOR));
        buttonClear.setMaximumSize(new Dimension(100, 50));
        formInputButton.add(buttonClear);
        this.add(formInputButton, BorderLayout.EAST);

        JPanel formInputConfirm = new JPanel();
        formInputConfirm.setBackground(Color.WHITE);
        formInputConfirm.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));

        buttonCancel.addActionListener(e -> {
            clearForm();
            setMode("");
        });

        buttonCancel.setBackground(Color.decode("#c2255c"));
        buttonCancel.setForeground(Color.decode("#ffffff"));
        buttonCancel.setEnabled(false);
        formInputConfirm.add(buttonCancel);
        
        buttonSave.addActionListener(e -> {
            if (!PeminjamanFormPage.daftarBuku.isEmpty()) {
                
                int durasiPeminjaman = 7;
                Mahasiswa mhs = manajemenMhs.cariMhs(inputNim.getInputText());
                Set<Buku> daftarBukuSet = new HashSet<>();

                for (Map.Entry<String, String> buku : PeminjamanFormPage.daftarBuku.entrySet()) {
                    daftarBukuSet.add(manajemenBuku.cariBuku(buku.getKey()));
                }

                boolean success = manajemenPeminjaman.prosesPeminjaman(mhs, daftarBukuSet, durasiPeminjaman);

                if (success) {
                    JOptionPane.showMessageDialog(null, "Peminjaman berhasil disimpan");
                } else {
                    JOptionPane.showMessageDialog(null, "Peminjaman gagal disimpan");
                }
                
                onSaveSuccess.run();
                clearForm();
                setMode("");
            } else {
                JOptionPane.showMessageDialog(null, "Tidak ada buku terdeteksi");
            }
        });

        buttonSave.setBackground(Color.decode("#2f9e44"));
        buttonSave.setForeground(Color.decode("#ffffff"));
        buttonSave.setEnabled(false);
        formInputConfirm.add(buttonSave);
        this.add(formInputConfirm, BorderLayout.SOUTH);

        updateFormUI();
    }

    private void setMode(String mode) {
        this.mode = mode;
        updateFormUI();
    }

    private void updateFormUI() {
        
        switch(this.mode) {
            case "add" -> {
                buttonAdd.setEnabled(false);
                buttonAddBook.setEnabled(true);
                buttonClear.setEnabled(true);
                buttonCancel.setEnabled(true);
                buttonSave.setEnabled(true);
                inputNim.setInputEnabled(false);
                inputKodeBuku.setInputEnabled(true);
            }
            default -> {
                buttonAdd.setEnabled(true);
                buttonAddBook.setEnabled(false);
                buttonClear.setEnabled(true);
                buttonCancel.setEnabled(false);
                buttonSave.setEnabled(false);
                inputNim.setInputEnabled(true);
                inputKodeBuku.setInputEnabled(false);
            }
        }
    }
    
    private void clearForm() {
        this.inputNim.clearForm();
        this.inputKodeBuku.clearForm();
        this.inputNamaMhs.clearForm();
        this.inputJudulBuku.clearForm();
        this.inputProdi.clearForm();
        this.inputKategori.clearForm();
    }

    public void setOnCart(AddBookOnCart addBookOnCart) {
        this.addBookOnCart = addBookOnCart;
    }
}
