package src.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
        this.setMaximumSize(new Dimension(200, 30));
        this.textField = new JTextField();
        this.searchButton = new RoundedButton(btnText);
        this.searchButton.setBackground(Color.decode("#e9ecef"));
        this.searchButton.setForeground(Color.decode("#1e1e1e"));
        this.textField.setBackground(Color.WHITE);
        this.textField.setForeground(Color.BLACK);
        this.textField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        this.textField.setPreferredSize(new Dimension(100, 30));
        this.searchButton.addActionListener(e -> {
            if (listener != null) {
                listener.onSearch(textField.getText());
            }
        });

        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(new JLabel(title), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(textField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(searchButton, gbc);


        this.setVisible(true);
    }

    public void setSearchListener(SearchListener listener) {
        this.listener = listener;
    }
}
