package src.fragments;
import filehandler.FileHandlerBuku;
import filehandler.FileHandlerMahasiswa;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import models.Buku;
import models.Mahasiswa;
import models.Peminjaman;
import services.ManajemenBuku;
import services.ManajemenMahasiswa;
import services.ManajemenPeminjaman;
import src.components.BasicInput;
import src.components.ComboBoxInput;
import src.components.RoundedButton;
import src.components.RoundedPanel;
import src.components.SearchInput;
import src.custom_listener.AddBookOnCart;
import src.pages.BukuFormPage;
import src.pages.MahasiswaFormPage;
import src.pages.PengembalianFormPage;

public class FormInputPengembalian extends RoundedPanel {
    final String SELECTED_NAV_BTN_COLOR = "#343a40";
    final String SELECTED_NAV_BTN_TEXT_COLOR = "#e9ecef";
    SearchInput inputNim = new SearchInput("NIM", "Cari");
    SearchInput inputKodeBuku = new SearchInput("Kode Buku", "Cari");
    BasicInput inputNamaMhs = new BasicInput("Nama Mahasiswa");
    BasicInput inputJudulBuku = new BasicInput("Judul Buku");
    ComboBoxInput inputProdi = new ComboBoxInput("Program Studi", new String[]{"Teknik Informatika", "Sistem Informasi", "Teknik Komputer", "Teknologi Informasi", "Pendidikan Teknologi Informasi"});
    ComboBoxInput inputKategori = new ComboBoxInput("Kategori", new String[]{"Fantasi", "Horor", "Fiksi", "Pendidikan", "Sejarah", "Sains"});
    RoundedButton buttonAdd = new RoundedButton("Kembalikan");
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
    //x
    private String selectedKodePeminjaman;
    //x

    public FormInputPengembalian(Runnable onSaveSuccess) {
        super(40);

        this.onSaveSuccess = onSaveSuccess;
        this.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        this.setBackground(Color.WHITE);
        this.setLayout(new BorderLayout(20, 40));

        //title
        JLabel formInputTitle = new JLabel("Form Input Pengembalian");
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

        //listener cari nim
        inputNim.setSearchListener((String nim) -> {
            Mahasiswa mhs = manajemenMhs.cariMhs(nim);
            if (mhs != null) {
                inputNim.setInputText(mhs.getNim());
                inputNamaMhs.setInputText(mhs.getNama());
                inputProdi.setInputText(mhs.getProdi());
                //g
                MahasiswaFormPage.refreshStatistik(mhs);
            } else {
                JOptionPane.showMessageDialog(null, "Mahasiswa dengan NIM " + nim + " tidak ditemukan.");
            }
        });
        inputNim.setInputEnabled(true);
        inputNamaMhs.setInputEnabled(false);
        inputProdi.setInputEnabled(false);
        this.add(formInputBody, BorderLayout.CENTER);


        formInputBody.add(inputNim);
        formInputBody.add(inputNamaMhs);
        formInputBody.add(inputProdi);

        this.add(formInputBody, BorderLayout.CENTER);

        //side button
        JPanel formInputButton = new JPanel();
        formInputButton.setLayout(new GridLayout(4, 1, 10, 10));
        formInputButton.setPreferredSize(new Dimension(100, 0));
        formInputButton.setBackground(Color.WHITE);

        buttonAdd.addActionListener(e -> {
            if (!(inputNim.getInputText().isEmpty() || inputNamaMhs.getInputText().isEmpty() || inputProdi.getInputText().isEmpty())) {
                setMode("add");
            }
        });

        buttonAdd.setBackground(Color.decode(SELECTED_NAV_BTN_COLOR));
        buttonAdd.setForeground(Color.decode(SELECTED_NAV_BTN_TEXT_COLOR));
        buttonAdd.setMaximumSize(new Dimension(100, 50));
        formInputButton.add(buttonAdd);


        
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
            if (selectedKodePeminjaman != null && !selectedKodePeminjaman.isEmpty()) {
                // pake selectedKodePeminjaman dari bagian kodee scrollpane PengembalianFormPage
                manajemenPeminjaman.prosesPengembalian(Integer.parseInt(selectedKodePeminjaman));
                
                onSaveSuccess.run();
                clearForm();
                setMode("");
            } else {
                JOptionPane.showMessageDialog(null, "Silakan pilih data peminjaman dari tabel terlebih dahulu.");
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
                buttonClear.setEnabled(true);
                buttonCancel.setEnabled(true);
                buttonSave.setEnabled(true);
                inputNim.setInputEnabled(false);
                inputKodeBuku.setInputEnabled(true);
            }
            default -> {
                buttonAdd.setEnabled(true);
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
        //x
        this.selectedKodePeminjaman = null;
        //x
    }

	public void setFieldInput(String nim, String nama, String prodi) {
        inputNim.setInputText(nim);
        inputNamaMhs.setInputText(nama);
        inputProdi.setInputText(prodi);
    }
    //x
    public void setSelectedKodePeminjaman(String kodePeminjaman) {
        this.selectedKodePeminjaman = kodePeminjaman;
    }
    //x
}

