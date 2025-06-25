package src.fragments;
import filehandler.FileHandlerBuku;
import filehandler.FileHandlerMahasiswa;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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
import src.pages.MahasiswaFormPage;

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

        //TITLE
        JLabel formInputTitle = new JLabel("Form Input Pengembalian");
        formInputTitle.setFont(new Font("Arial", Font.BOLD, 30));
        this.add(formInputTitle, BorderLayout.NORTH);

        //BODY
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

        //SIDE BUTTON
        JPanel formInputButton = new JPanel();
        formInputButton.setLayout(new GridLayout(4, 1, 10, 10));
        formInputButton.setPreferredSize(new Dimension(100, 0));
        formInputButton.setBackground(Color.WHITE);
        //button add
        buttonAdd.setBackground(Color.decode(SELECTED_NAV_BTN_COLOR));
        buttonAdd.setForeground(Color.decode(SELECTED_NAV_BTN_TEXT_COLOR));
        buttonAdd.setMaximumSize(new Dimension(100, 50));
        formInputButton.add(buttonAdd);
        //button clear
        buttonClear.setBackground(Color.decode(SELECTED_NAV_BTN_COLOR));
        buttonClear.setForeground(Color.decode(SELECTED_NAV_BTN_TEXT_COLOR));
        buttonClear.setMaximumSize(new Dimension(100, 50));
        formInputButton.add(buttonClear);
        this.add(formInputButton, BorderLayout.EAST);
        
        //CONFIRM BUTTON, FOOTER            
        JPanel formInputConfirm = new JPanel();
        formInputConfirm.setBackground(Color.WHITE);
        formInputConfirm.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
        //button cancel
        buttonCancel.setBackground(Color.decode("#c2255c"));
        buttonCancel.setForeground(Color.decode("#ffffff"));
        buttonCancel.setEnabled(false);
        formInputConfirm.add(buttonCancel);
        //button save
        buttonSave.setBackground(Color.decode("#2f9e44"));
        buttonSave.setForeground(Color.decode("#ffffff"));
        buttonSave.setEnabled(false);
        formInputConfirm.add(buttonSave);
        this.add(formInputConfirm, BorderLayout.SOUTH);
        
        //LISTENER, ACTION
        buttonAdd.addActionListener(e -> {
            if (!(inputNim.getInputText().isEmpty() || inputNamaMhs.getInputText().isEmpty() || inputProdi.getInputText().isEmpty())) {
                setMode("add");
            }
        });

        buttonClear.addActionListener(e -> {
            clearForm();
        });

        buttonCancel.addActionListener(e -> {
            clearForm();
            setMode("");
            onSaveSuccess.run();
        });

        buttonSave.addActionListener(e -> {
            if (selectedKodePeminjaman != null && !selectedKodePeminjaman.isEmpty()) {

                //selectedKodePeminjaman dari bagian kode scrollpane PengembalianFormPage
                Peminjaman peminjaman = manajemenPeminjaman.cariPeminjaman(Integer.valueOf(selectedKodePeminjaman));
                boolean isDenda = manajemenPeminjaman.prosesPengembalian(Integer.valueOf(selectedKodePeminjaman));

                if (isDenda) {
                    JOptionPane.showInputDialog(null, "Mahasiswa dengan NIM " + inputNim.getInputText() + " telah terlambat.\nMohon segera membayar denda sebesar Rp." + peminjaman.getMhs().getDenda() + " (Input nominal tertera)", "Peringatan", JOptionPane.WARNING_MESSAGE);

                    //reset denda
                    Mahasiswa mhs = manajemenMhs.cariMhs(peminjaman.getMhs().getNim());
                    mhs.setKenaDenda(false);
                    mhs.resetDenda();
                    manajemenMhs.editMhs(mhs);

                    JOptionPane.showMessageDialog(null, "Denda berhasil terbayar", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Pengembalian buku berhasil.");
                }

                onSaveSuccess.run();
                clearForm();
                setMode("");
            } else {
                JOptionPane.showMessageDialog(null, "Silakan pilih data peminjaman dari tabel terlebih dahulu.");
            }
        });
        
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

