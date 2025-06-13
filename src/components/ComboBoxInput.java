package src.components;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ComboBoxInput extends JPanel {
    JComboBox<String> comboBox;

    public ComboBoxInput(String title, String[] items) {
        this.setBackground(Color.WHITE);

        // Inisialisasi komponen
        comboBox = new JComboBox<>(items);

        // Warna dan tampilan
        comboBox.setBackground(Color.WHITE);
        comboBox.setForeground(Color.BLACK);
        comboBox.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        comboBox.setSelectedItem(null);

        // Layout: GridBagLayout
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Label atas
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 10, 0);
        this.add(new JLabel(title), gbc);
        
        // ComboBox
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 0, 0);
        this.add(comboBox, gbc);
    }
}
