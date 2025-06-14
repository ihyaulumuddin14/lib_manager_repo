package src.components;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class BasicInput extends JPanel {
    JTextField textField;

    public BasicInput(String title) {
        this.setBackground(Color.WHITE);

        // Inisialisasi komponen
        textField = new JTextField();

        // Warna dan tampilan
        textField.setBackground(Color.WHITE);
        textField.setForeground(Color.BLACK);
        textField.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

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
        
        // basic input
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 0, 0);
        this.add(textField, gbc);
    }

    public void clearForm() {
        textField.setText(null);
    }

    public void setInputEnabled(boolean enabled) {
        textField.setEnabled(enabled);
    }

    public String getInputText() {
        return textField.getText();
    }

    public void setInputText(String text) {
        textField.setText(text);
    }
}
