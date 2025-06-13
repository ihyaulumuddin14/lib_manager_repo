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

public class FormInputBuku extends RoundedPanel {
    final String SELECTED_NAV_BTN_COLOR = "#343a40";
    final String SELECTED_NAV_BTN_TEXT_COLOR = "#e9ecef";

    public FormInputBuku() {
        super(40);
        this.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        this.setBackground(Color.WHITE);
        this.setLayout(new BorderLayout(20, 40));

        //title
        JLabel formInputTitle = new JLabel("Form Input Buku");
        formInputTitle.setFont(new Font("Arial", Font.BOLD, 30));
        this.add(formInputTitle, BorderLayout.NORTH);

        //body
        JPanel formInputBody = new JPanel();
        formInputBody.setBackground(Color.WHITE);
        formInputBody.setLayout(new GridLayout(3, 2, 20, 20));

        SearchInput input1 = new SearchInput("Kode Buku", "Cari");
        formInputBody.add(input1);
        BasicInput input2 = new BasicInput("Nama Buku");
        formInputBody.add(input2);
        BasicInput input3 = new BasicInput("Penulis (ex: Raditya Dika, Henry Manampiring, ...)");
        formInputBody.add(input3);
        BasicInput input4 = new BasicInput("Penerbit");
        formInputBody.add(input4);
        BasicInput input5 = new BasicInput("Tahun Terbit");
        formInputBody.add(input5);

        String[] kategoriBuku = {"Fantasi", "Horor", "Fiksi", "Pendidikan", "Sejarah", "Sains"};
        ComboBoxInput input6 = new ComboBoxInput("Kategori", kategoriBuku);
        formInputBody.add(input6);
        this.add(formInputBody, BorderLayout.CENTER);

        //side button
        JPanel formInputButton = new JPanel();
        formInputButton.setLayout(new GridLayout(4, 1, 0, 10)); // 4 baris, 10px antar baris
        formInputButton.setPreferredSize(new Dimension(200, 0));
        formInputButton.setBackground(Color.WHITE);

        RoundedButton buttonAdd = new RoundedButton("Tambah");
        buttonAdd.setBackground(Color.decode(SELECTED_NAV_BTN_COLOR));
        buttonAdd.setForeground(Color.decode(SELECTED_NAV_BTN_TEXT_COLOR));
        buttonAdd.setMaximumSize(new Dimension(200, 50));
        formInputButton.add(buttonAdd);

        RoundedButton buttonEdit = new RoundedButton("Edit");
        buttonEdit.setBackground(Color.decode(SELECTED_NAV_BTN_COLOR));
        buttonEdit.setForeground(Color.decode(SELECTED_NAV_BTN_TEXT_COLOR));
        buttonEdit.setMaximumSize(new Dimension(200, 50));
        formInputButton.add(buttonEdit);

        RoundedButton buttonDelete = new RoundedButton("Hapus");
        buttonDelete.setBackground(Color.decode(SELECTED_NAV_BTN_COLOR));
        buttonDelete.setForeground(Color.decode(SELECTED_NAV_BTN_TEXT_COLOR));
        buttonDelete.setMaximumSize(new Dimension(200, 50));
        buttonDelete.setEnabled(false);
        formInputButton.add(buttonDelete);
        
        RoundedButton buttonClear = new RoundedButton("Clear");
        buttonClear.setBackground(Color.decode(SELECTED_NAV_BTN_COLOR));
        buttonClear.setForeground(Color.decode(SELECTED_NAV_BTN_TEXT_COLOR));
        buttonClear.setMaximumSize(new Dimension(200, 50));
        formInputButton.add(buttonClear);
        this.add(formInputButton, BorderLayout.EAST);

        JPanel formInputConfirm = new JPanel();
        formInputConfirm.setBackground(Color.WHITE);
        formInputConfirm.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));

        //confirm button
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
