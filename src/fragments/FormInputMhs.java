package src.fragments;

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
import services.ManajemenMahasiswa;
import src.components.BasicInput;
import src.components.ComboBoxInput;
import src.components.RoundedButton;
import src.components.RoundedPanel;
import src.components.SearchInput;
import src.pages.MahasiswaFormPage;

public class FormInputMhs extends RoundedPanel {
    final String SELECTED_NAV_BTN_COLOR = "#343a40";
    final String SELECTED_NAV_BTN_TEXT_COLOR = "#e9ecef";
    final String[] daftarProdi = {"Teknik Informatika", "Sistem Informasi", "Teknik Komputer", "Teknologi Informasi", "Pendidikan Teknologi Informasi"};
    SearchInput inputNim = new SearchInput("NIM", "Cari");
    BasicInput inputNama = new BasicInput("Nama Mahasiswa");
    ComboBoxInput inputProdi = new ComboBoxInput("Program Studi", this.daftarProdi);
    RoundedButton buttonAdd = new RoundedButton("Tambah");
    RoundedButton buttonEdit = new RoundedButton("Edit");
    RoundedButton buttonDelete = new RoundedButton("Hapus");
    RoundedButton buttonClear = new RoundedButton("Clear");
    RoundedButton buttonCancel = new RoundedButton("Batal");
    RoundedButton buttonSave = new RoundedButton("Simpan");
    FileHandlerMahasiswa fhMhs = new FileHandlerMahasiswa();
    ManajemenMahasiswa manajemenMhs = new ManajemenMahasiswa(fhMhs);
    Runnable onSaveSuccess;
    String mode = "";

    public FormInputMhs(Runnable onSaveSuccess) {
        super(40);
        
        this.onSaveSuccess = onSaveSuccess;
        this.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        this.setBackground(Color.WHITE);
        this.setLayout(new BorderLayout(20, 40));

        //title
        JLabel formInputTitle = new JLabel("Form Input Mahasiswa");
        formInputTitle.setFont(new Font("Arial", Font.BOLD, 30));
        this.add(formInputTitle, BorderLayout.NORTH);

        //body
        JPanel formInputBody = new JPanel();
        formInputBody.setBackground(Color.WHITE);
        formInputBody.setLayout(new GridLayout(3, 1, 0, 20));

        //listener cari nim
        inputNim.setSearchListener((String nim) -> {
            Mahasiswa mhs = manajemenMhs.cariMhs(nim);
            if (mhs != null) {
                inputNim.setInputText(mhs.getNim());
                inputNama.setInputText(mhs.getNama());
                inputProdi.setInputText(mhs.getProdi());
                MahasiswaFormPage.refreshStatistik(mhs);
            } else {
                JOptionPane.showMessageDialog(null, "Mahasiswa dengan NIM " + nim + " tidak ditemukan.");
            }
        });

        formInputBody.add(inputNim);
        formInputBody.add(inputNama);
        formInputBody.add(inputProdi);
        this.add(formInputBody, BorderLayout.CENTER);

        //side button
        JPanel formInputButton = new JPanel();
        formInputButton.setLayout(new GridLayout(4, 1, 0, 10)); // 4 baris, 10px antar baris
        formInputButton.setPreferredSize(new Dimension(200, 0));
        formInputButton.setBackground(Color.WHITE);

        buttonAdd.addActionListener(e -> {
            setMode("add");
        });

        buttonAdd.setBackground(Color.decode(SELECTED_NAV_BTN_COLOR));
        buttonAdd.setForeground(Color.decode(SELECTED_NAV_BTN_TEXT_COLOR));
        buttonAdd.setMaximumSize(new Dimension(200, 50));
        formInputButton.add(buttonAdd);

        buttonEdit.addActionListener(e -> {
            setMode("edit");
        });

        buttonEdit.setBackground(Color.decode(SELECTED_NAV_BTN_COLOR));
        buttonEdit.setForeground(Color.decode(SELECTED_NAV_BTN_TEXT_COLOR));
        buttonEdit.setMaximumSize(new Dimension(200, 50));
        formInputButton.add(buttonEdit);

        buttonDelete.addActionListener(e -> {
            setMode("delete");
        });

        buttonDelete.setBackground(Color.decode(SELECTED_NAV_BTN_COLOR));
        buttonDelete.setForeground(Color.decode(SELECTED_NAV_BTN_TEXT_COLOR));
        buttonDelete.setMaximumSize(new Dimension(200, 50));
        buttonDelete.setEnabled(false);
        formInputButton.add(buttonDelete);
        
        buttonClear.addActionListener(e -> {
            clearForm();
        });

        buttonClear.setBackground(Color.decode(SELECTED_NAV_BTN_COLOR));
        buttonClear.setForeground(Color.decode(SELECTED_NAV_BTN_TEXT_COLOR));
        buttonClear.setMaximumSize(new Dimension(200, 50));
        formInputButton.add(buttonClear);
        this.add(formInputButton, BorderLayout.EAST);

        //confirm footer
        JPanel formInputConfirm = new JPanel();
        formInputConfirm.setBackground(Color.WHITE);
        formInputConfirm.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));

        buttonCancel.addActionListener(e -> {
            setMode("");
        });

        buttonCancel.setBackground(Color.decode("#c2255c"));
        buttonCancel.setForeground(Color.decode("#ffffff"));
        buttonCancel.setEnabled(false);
        formInputConfirm.add(buttonCancel);
        
        buttonSave.addActionListener(e -> {
            if (validateInput()) {
                try {
                    switch (this.mode) {
                        case "add" -> {
                            boolean success = manajemenMhs.tambahMhs(getInputData());

                            if (success)
                                JOptionPane.showMessageDialog(new MahasiswaFormPage(), "Mahasiswa berhasil ditambahkan!", "Success", JOptionPane.INFORMATION_MESSAGE);
                            else
                                JOptionPane.showMessageDialog(new MahasiswaFormPage(), "Mahasiswa gagal ditambahkan!", "Failed", JOptionPane.ERROR_MESSAGE);
                        }
                        case "edit" -> {
                            Mahasiswa mhs = getInputData();
                            manajemenMhs.editMhs(mhs);
                            JOptionPane.showMessageDialog(new MahasiswaFormPage(), "Data berhasil diubah!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        }
                        case "delete" -> {
                            boolean success = manajemenMhs.hapusMhs(getInputData().getNim());

                            if (success) {
                                JOptionPane.showMessageDialog(new MahasiswaFormPage(), "Data berhasil dihapus!", "Success", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(new MahasiswaFormPage(), "Data gagal dihapus!", "Failed", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        default -> {}
                    }

                    if (onSaveSuccess != null) {
                        onSaveSuccess.run();
                    }
                } catch (Exception err) {
                    JOptionPane.showMessageDialog(new MahasiswaFormPage(), err.getMessage(), "Failed", JOptionPane.ERROR_MESSAGE);
                } finally {
                    setMode("");
                    clearForm();
                }
            } else {
                JOptionPane.showMessageDialog(new MahasiswaFormPage(), "Semua field harus diisi!");
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
        
        switch (this.mode) {
            case "add" -> {
                clearForm();
                buttonAdd.setEnabled(false);
                buttonEdit.setEnabled(false);
                buttonDelete.setEnabled(false);
                buttonClear.setEnabled(true);
                buttonCancel.setEnabled(true);
                buttonSave.setEnabled(true);
                inputNim.setInputEnabled(true);
                inputNama.setInputEnabled(true);
                inputProdi.setInputEnabled(true);
            }
            case "edit" -> {
                buttonAdd.setEnabled(false);
                buttonEdit.setEnabled(false);
                buttonDelete.setEnabled(false);
                buttonClear.setEnabled(true);
                buttonCancel.setEnabled(true);
                buttonSave.setEnabled(true);
                inputNim.setInputEnabled(false);
                inputNama.setInputEnabled(true);
                inputProdi.setInputEnabled(true);
            }
            case "delete" -> {
                buttonAdd.setEnabled(false);
                buttonEdit.setEnabled(false);
                buttonDelete.setEnabled(false);
                buttonClear.setEnabled(true);
                buttonCancel.setEnabled(true);
                buttonSave.setEnabled(true);
                inputNim.setInputEnabled(true);
                inputNama.setInputEnabled(false);
                inputProdi.setInputEnabled(false);
            }
            default -> {
                buttonAdd.setEnabled(true);
                buttonEdit.setEnabled(true);
                buttonDelete.setEnabled(true);
                buttonClear.setEnabled(true);
                buttonCancel.setEnabled(false);
                buttonSave.setEnabled(false);
                inputNim.setInputEnabled(true);
                inputNama.setInputEnabled(false);
                inputProdi.setInputEnabled(false);
            }
        }
    }

    private void clearForm() {
        this.inputNim.clearForm();
        this.inputNama.clearForm();
        this.inputProdi.clearForm();
    }

    private Mahasiswa getInputData() {
        return new Mahasiswa(
                inputNim.getInputText(),
                inputNama.getInputText(),
                inputProdi.getInputText()
                );
    }

    private boolean validateInput() {
        return !(inputNim.getInputText().isEmpty() || inputNama.getInputText().isEmpty() || inputProdi.getInputText().isEmpty());
    }

    public void setFieldInput(String nim, String nama, String prodi) {
        inputNim.setInputText(nim);
        inputNama.setInputText(nama);
        inputProdi.setInputText(prodi);
    }
}
