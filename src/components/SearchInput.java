package src.components;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import src.custom_listener.SearchListener;

public class SearchInput extends JPanel {
    JTextField textField;
    JButton searchButton;
    private SearchListener listener;

    public SearchInput(String title, String btnText) {
        this.setBackground(Color.WHITE);

        // Inisialisasi komponen
        textField = new JTextField();
        searchButton = new RoundedButton(btnText);

        // Warna dan tampilan
        searchButton.setBackground(Color.decode("#e9ecef"));
        searchButton.setForeground(Color.decode("#1e1e1e"));
        textField.setBackground(Color.WHITE);
        textField.setForeground(Color.BLACK);
        textField.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        // Action listener
        searchButton.addActionListener(e -> {
            if (listener != null) {
                listener.onSearch(textField.getText());
            }
        });

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
        
        // TextField kiri
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 0, 20);
        this.add(textField, gbc);
        
        // Button kanan
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 0, 0);
        this.add(searchButton, gbc);
    }

    public void setSearchListener(SearchListener listener) {
        this.listener = listener;
    }

    public void clearForm() {
        textField.setText(null);
    }

    public void setInputEnabled(boolean enabled) {
        textField.setEnabled(enabled);
        searchButton.setEnabled(enabled);
    }

    public String getInputText() {
        return textField.getText();
    }
}
