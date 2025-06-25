package src.fragments;

import filehandler.FileHandlerBuku;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.HashSet;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import models.Buku;
import services.ManajemenBuku;
import src.components.BasicInput;
import src.components.ComboBoxInput;
import src.components.RoundedButton;
import src.components.RoundedPanel;
import src.components.SearchInput;

public class FormInputBuku extends RoundedPanel {
    final String SELECTED_NAV_BTN_COLOR = "#343a40";
    final String SELECTED_NAV_BTN_TEXT_COLOR = "#e9ecef";

    //elemen input
    SearchInput inputKode = new SearchInput("Kode Buku", "Cari");
    BasicInput inputNama = new BasicInput("Nama Buku");
    BasicInput inputPenulis = new BasicInput("Penulis (ex: Raditya Dika, Henry Manampiring, ...)");
    BasicInput inputStok = new BasicInput("Stok");
    BasicInput inputTahunTerbit = new BasicInput("Tahun Terbit");
    ComboBoxInput inputKategori = new ComboBoxInput("Kategori", new String[]{"Fantasi", "Horor", "Fiksi", "Pendidikan", "Sejarah", "Sains"});

    //button
    RoundedButton buttonAdd = new RoundedButton("Tambah");
    RoundedButton buttonEdit = new RoundedButton("Edit");
    RoundedButton buttonDelete = new RoundedButton("Hapus");
    RoundedButton buttonClear = new RoundedButton("Clear");
    RoundedButton buttonCancel = new RoundedButton("Batal");
    RoundedButton buttonSave = new RoundedButton("Simpan");

    //manajemen
    FileHandlerBuku fhBuku = new FileHandlerBuku();
    ManajemenBuku manajemenBuku = new ManajemenBuku(fhBuku);
    Runnable onSaveSuccess;
    String mode = "";

    public FormInputBuku(Runnable onSaveSuccess) {
        super(40);

        this.onSaveSuccess = onSaveSuccess;
        this.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        this.setBackground(Color.WHITE);
        this.setLayout(new BorderLayout(20, 40));

        //TITLE
        JLabel formInputTitle = new JLabel("Form Input Buku");
        formInputTitle.setFont(new Font("Arial", Font.BOLD, 30));
        this.add(formInputTitle, BorderLayout.NORTH);

        //BODY
        JPanel formInputBody = new JPanel();
        formInputBody.setBackground(Color.WHITE);
        formInputBody.setLayout(new GridLayout(3, 2, 20, 20));
        formInputBody.add(inputKode);
        formInputBody.add(inputNama);
        formInputBody.add(inputPenulis);
        formInputBody.add(inputStok);
        formInputBody.add(inputTahunTerbit);
        formInputBody.add(inputKategori);
        this.add(formInputBody, BorderLayout.CENTER);

        //SIDE BUTTON
        JPanel formInputButton = new JPanel();
        formInputButton.setLayout(new GridLayout(4, 1, 0, 10)); // 4 baris, 10px antar baris
        formInputButton.setPreferredSize(new Dimension(200, 0));
        formInputButton.setBackground(Color.WHITE);
        //button add
        buttonAdd.setBackground(Color.decode(SELECTED_NAV_BTN_COLOR));
        buttonAdd.setForeground(Color.decode(SELECTED_NAV_BTN_TEXT_COLOR));
        buttonAdd.setMaximumSize(new Dimension(200, 50));
        formInputButton.add(buttonAdd);
        //button edit
        buttonEdit.setBackground(Color.decode(SELECTED_NAV_BTN_COLOR));
        buttonEdit.setForeground(Color.decode(SELECTED_NAV_BTN_TEXT_COLOR));
        buttonEdit.setMaximumSize(new Dimension(200, 50));
        formInputButton.add(buttonEdit);
        //button delete
        buttonDelete.setBackground(Color.decode(SELECTED_NAV_BTN_COLOR));
        buttonDelete.setForeground(Color.decode(SELECTED_NAV_BTN_TEXT_COLOR));
        buttonDelete.setMaximumSize(new Dimension(200, 50));
        buttonDelete.setEnabled(false);
        formInputButton.add(buttonDelete);
        //button clear
        buttonClear.setBackground(Color.decode(SELECTED_NAV_BTN_COLOR));
        buttonClear.setForeground(Color.decode(SELECTED_NAV_BTN_TEXT_COLOR));
        buttonClear.setMaximumSize(new Dimension(200, 50));
        formInputButton.add(buttonClear);
        this.add(formInputButton, BorderLayout.EAST);

        //FOOTER, CONFIRM BUTTON
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
        inputKode.setSearchListener((String kode) -> {
            Buku buku = manajemenBuku.cariBuku(kode);
            if (buku != null) {
                inputNama.setInputText(buku.getNamaBuku());
                inputPenulis.setInputText(String.join(", ", buku.getPenulis()));
                inputTahunTerbit.setInputText(String.valueOf(buku.getTahunTerbit()));
                inputKategori.setInputText(buku.getKategori());
            } else {
                JOptionPane.showMessageDialog(null, "Buku dengan kode " + kode + " tidak ditemukan.");
            }
        });

        buttonAdd.addActionListener(e -> setMode("add"));

        buttonEdit.addActionListener(e -> {
            if (validateInput()) {
                setMode("edit");
            } else {
                JOptionPane.showMessageDialog(this, "Tidak ada buku yang dipilih.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            }
        });
        
        buttonDelete.addActionListener(e -> {
            if (validateInput()) {
                setMode("delete");
            } else {
                JOptionPane.showMessageDialog(this, "Tidak ada buku yang dipilih.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            }
        });

        buttonClear.addActionListener(e -> clearForm());

        buttonCancel.addActionListener(e -> {
            setMode("");
        });
        
        buttonSave.addActionListener(e -> {
            if ((validateInput() && this.mode.equals("add")) || (validateInput() && !inputKode.getInputText().isEmpty())) {
                try {
                    switch (this.mode) {
                        case "add" -> {
                            boolean success = manajemenBuku.tambahBuku(createBuku());
    
                            if (success) {
                                JOptionPane.showMessageDialog(this, "Buku berhasil ditambahkan.");
                            } else {
                                JOptionPane.showMessageDialog(this, "Gagal menambahkan buku.", "Failed", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        case "edit" -> {
                            Buku buku = createBuku();
                            manajemenBuku.editBuku(buku);
                            JOptionPane.showMessageDialog(this, "Buku berhasil diedit.");
                        }
                        case "delete" -> {
                            boolean success = manajemenBuku.hapusBuku(inputKode.getInputText());
    
                            if (success) {
                                JOptionPane.showMessageDialog(this, "Buku berhasil dihapus.");
                            } else {
                                JOptionPane.showMessageDialog(this, "Gagal menghapus buku.", "Failed", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        default -> {}
                    }
    
                    if (onSaveSuccess != null) {
                        onSaveSuccess.run();
                    }
                } catch (Exception err) {
                    JOptionPane.showMessageDialog(this, err.getMessage());
                } finally {
                    setMode("");
                    clearForm();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Semua field harus diisi!");
            }

        });

        updateFormUI();
    }

    private void setMode(String mode) {
        this.mode = mode;
        updateFormUI();
    }

    private void updateFormUI() {
        switch (this.mode) {
            case "add" -> {
                buttonAdd.setEnabled(false);
                buttonEdit.setEnabled(false);
                buttonDelete.setEnabled(false);
                buttonClear.setEnabled(true);
                buttonCancel.setEnabled(true);
                buttonSave.setEnabled(true);
                inputKode.setInputEnabled(false);
                inputNama.setInputEnabled(true);
                inputPenulis.setInputEnabled(true);
                inputStok.setInputEnabled(true);
                inputTahunTerbit.setInputEnabled(true);
                inputKategori.setInputEnabled(true);
            }
            case "edit" -> {
                buttonAdd.setEnabled(false);
                buttonEdit.setEnabled(false);
                buttonDelete.setEnabled(false);
                buttonClear.setEnabled(true);
                buttonCancel.setEnabled(true);
                buttonSave.setEnabled(true);
                inputKode.setInputEnabled(false);
                inputNama.setInputEnabled(true);
                inputPenulis.setInputEnabled(true);
                inputStok.setInputEnabled(true);
                inputTahunTerbit.setInputEnabled(true);
                inputKategori.setInputEnabled(true);
            }
            case "delete" -> {
                buttonAdd.setEnabled(false);
                buttonEdit.setEnabled(false);
                buttonDelete.setEnabled(false);
                buttonClear.setEnabled(true);
                buttonCancel.setEnabled(true);
                buttonSave.setEnabled(true);
                inputKode.setInputEnabled(true);
                inputNama.setInputEnabled(false);
                inputPenulis.setInputEnabled(false);
                inputStok.setInputEnabled(false);
                inputTahunTerbit.setInputEnabled(false);
                inputKategori.setInputEnabled(false);
            }
            default -> {
                buttonAdd.setEnabled(true);
                buttonEdit.setEnabled(true);
                buttonDelete.setEnabled(true);
                buttonClear.setEnabled(true);
                buttonCancel.setEnabled(false);
                buttonSave.setEnabled(false);
                inputKode.setInputEnabled(true);
                inputNama.setInputEnabled(false);
                inputPenulis.setInputEnabled(false);
                inputStok.setInputEnabled(false);
                inputTahunTerbit.setInputEnabled(false);
                inputKategori.setInputEnabled(false);
            }
        }
    }

    private void clearForm() {
        this.inputKode.clearForm();
        this.inputNama.clearForm();
        this.inputPenulis.clearForm();
        this.inputStok.clearForm();
        this.inputTahunTerbit.clearForm();
        this.inputKategori.clearForm();
    }

    private Buku createBuku() {
        String kode;

        if (inputKode.getInputText().isEmpty()) {
            kode = manajemenBuku.generateKode();
        } else {
            kode = inputKode.getInputText();
        }

        Set<String> penulis = new HashSet<>();
        String[] penulisArr = inputPenulis.getInputText().split(",");
        if (penulisArr.length != 0) {
            for (String p : penulisArr) {
                penulis.add(p.trim());
            }
        }
        
        return new Buku(
                kode,
                inputNama.getInputText(),
                penulis,
                Integer.parseInt(inputStok.getInputText()),
                Integer.parseInt(inputTahunTerbit.getInputText()),
                inputKategori.getInputText()
                );
    }

    private boolean validateInput() {
        return !(inputNama.getInputText().isEmpty() || inputPenulis.getInputText().isEmpty() || inputTahunTerbit.getInputText().isEmpty() || inputKategori.getInputText().isEmpty());
    }

    public void setFieldInput(String kode, String nama, String penulis, String stok, String tahunTerbit, String kategori) {
        inputKode.setInputText(kode);
        inputNama.setInputText(nama);
        inputPenulis.setInputText(penulis);
        inputStok.setInputText(stok);
        inputTahunTerbit.setInputText(tahunTerbit);
        inputKategori.setInputText(kategori);
    }
}
