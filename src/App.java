package src;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import src.components.*;
import src.fragments.DataScrollPane;

public class App extends JFrame {
    JPanel wrapperPanel = new JPanel();
    JPanel sideBarPanel = new JPanel();
    JPanel mainPanel = new JPanel();
    String mode = "mahasiswa";
    final String DEFAULT_BG_COLOR = "#868e96";
    final String DEFAULT_NAV_BTN_COLOR = "#e9ecef";
    final String DEFAULT_NAV_BTN_TEXT_COLOR = "#343a40";
    final String SELECTED_NAV_BTN_COLOR = "#343a40";
    final String SELECTED_NAV_BTN_TEXT_COLOR = "#e9ecef";


    public App() {
        this.setTitle("Library Manager Application");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(Color.decode("#868e96"));
        this.setSize(1920, 1080);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        wrapperPanel.setLayout(new BorderLayout());
        createSideBarPanel();
        createMainPanel();
        this.add(wrapperPanel);

        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }


    private void createSideBarPanel() {
        sideBarPanel.setLayout(new BorderLayout());
        sideBarPanel.setBackground(Color.decode(DEFAULT_BG_COLOR));
        sideBarPanel.setPreferredSize(new Dimension(350, 0));
        sideBarPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JPanel sideBar = new RoundedPanel(40);
        sideBar.setLayout(new BorderLayout());
        sideBar.setBackground(Color.decode("#e9ecef"));
        sideBar.setBorder(BorderFactory.createEmptyBorder(40, 20, 40, 20));
        sideBarPanel.add(sideBar, BorderLayout.CENTER);

        JPanel sideBarHeader = new JPanel(new FlowLayout(FlowLayout.CENTER));
        sideBar.add(sideBarHeader, BorderLayout.NORTH);

        JPanel sideBarBody = new JPanel();
        sideBarBody.setLayout(new GridLayout(8, 1, 0, 10));
        sideBarBody.setBorder(BorderFactory.createEmptyBorder(60, 0, 0, 0));
        sideBar.add(sideBarBody, BorderLayout.CENTER);

        JPanel sideBarFooter = new JPanel(new FlowLayout(FlowLayout.CENTER));
        sideBar.add(sideBarFooter, BorderLayout.SOUTH);

        // sideBarHeader
        JLabel titleApps = new JLabel("LibMan Apps");
        titleApps.setForeground(Color.BLACK);
        titleApps.setFont(new Font("Arial", Font.BOLD, 30));
        titleApps.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        sideBarHeader.add(titleApps);

        // sideBarBody
        String[] menus = {"Mahasiswa", "Buku", "Peminjaman", "", "", "", "", "Riwayat Peminjaman"};
        List<JButton> buttons = new ArrayList<>();

        for (int i = 0; i < menus.length; i++) {
            if (i <= 2 || i >= 7) {
                JButton navBtn = new RoundedButton(menus[i]);
                navBtn.setPreferredSize(new Dimension(250, 40));
                navBtn.setFont(new Font("Arial", Font.PLAIN, 20));
                navBtn.setFocusable(false);
                navBtn.addActionListener(e -> {
                    for (JButton button : buttons) {
                        button.setBackground(Color.decode(DEFAULT_NAV_BTN_COLOR));
                        button.setForeground(Color.decode(DEFAULT_NAV_BTN_TEXT_COLOR));
                        navBtn.setBackground(Color.decode(SELECTED_NAV_BTN_COLOR));
                        navBtn.setForeground(Color.decode(SELECTED_NAV_BTN_TEXT_COLOR));
                    }
                    mode = ((JButton) e.getSource()).getText().toLowerCase();
                });

                if (i == 0) {
                    navBtn.setBackground(Color.decode(SELECTED_NAV_BTN_COLOR));
                    navBtn.setForeground(Color.decode(SELECTED_NAV_BTN_TEXT_COLOR));
                }

                sideBarBody.add(navBtn);
                buttons.add(navBtn);
            } else {
                JPanel emptyPanel = new JPanel();
                emptyPanel.setPreferredSize(new Dimension(250, 40));
                sideBarBody.add(emptyPanel);
            }
        }

        wrapperPanel.add(sideBarPanel, BorderLayout.WEST);
    }
    
    
    
    private void createMainPanel() {
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(Color.decode("#868e96"));
        GridBagConstraints gbc = new GridBagConstraints();

        //form input dasar
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        JPanel formInputWrapper = new JPanel();
        formInputWrapper.setLayout(new BorderLayout());
        formInputWrapper.setBackground(Color.decode(DEFAULT_BG_COLOR));
        formInputWrapper.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        //form input
        JPanel formInputPanel = new RoundedPanel(40);
        // formInputPanel.setLayout(new BorderLayout());
        formInputPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        
        //judul
        JLabel formInputTitle = new JLabel("Form Input");
        formInputTitle.setFont(new Font("Arial", Font.BOLD, 30));
        formInputPanel.add(formInputTitle);
        
        //body
        JPanel formInputBody = new JPanel();
        formInputBody.setLayout(new BoxLayout(formInputBody, BoxLayout.Y_AXIS));
        formInputBody.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        formInputBody.setMaximumSize(new Dimension(0, 200));
        formInputBody.setBackground(Color.WHITE);

        List<JTextField> formInputFields = new ArrayList<>();
        JTextField formInputField = new JTextField("Judul");
        formInputFields.add(formInputField);
        JTextField formInputField2 = new JTextField("Judul");
        formInputFields.add(formInputField2);
        JTextField formInputField3 = new JTextField("Judul");
        formInputFields.add(formInputField3);
        JTextField formInputField4 = new JTextField("Judul");
        formInputFields.add(formInputField4);

        for (JTextField field : formInputFields) {
            field.setPreferredSize(new Dimension(600, 30));
        }
        formInputBody.add(formInputField);
        formInputBody.add(formInputField2);
        formInputBody.add(formInputField3);
        formInputBody.add(formInputField4);

        formInputPanel.add(formInputBody, BorderLayout.CENTER);
        
        //button
        JPanel formInputButton = new JPanel();
        formInputButton.setLayout(new BoxLayout(formInputButton, BoxLayout.Y_AXIS));
        formInputButton.setBorder(BorderFactory.createEmptyBorder(40, 20, 40, 20));
        formInputButton.setBackground(Color.WHITE);
        formInputButton.add(new JButton("Simpan"));
        formInputButton.add(new JButton("Simpan"));
        formInputButton.add(new JButton("Simpan"));
        formInputButton.add(new JButton("Simpan"));
        formInputPanel.add(formInputButton, BorderLayout.EAST);

        formInputPanel.setBackground(Color.WHITE);
        formInputWrapper.add(formInputPanel, BorderLayout.CENTER);
        mainPanel.add(formInputWrapper, gbc);


        //statistik
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.5;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        JPanel statWrapper = new JPanel();
        statWrapper.setLayout(new BorderLayout());
        statWrapper.setBackground(Color.decode(DEFAULT_BG_COLOR));
        statWrapper.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JPanel statPanel = new RoundedPanel(40);
        statPanel.setBackground(Color.decode(SELECTED_NAV_BTN_COLOR));
        statWrapper.add(statPanel, BorderLayout.CENTER);
        mainPanel.add(statWrapper, gbc);
        
        
        //tabel wrapper
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        JPanel tableWrapper = new JPanel();
        tableWrapper.setLayout(new BorderLayout());
        tableWrapper.setBackground(Color.decode(DEFAULT_BG_COLOR));
        tableWrapper.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 20));

        //tabel panel
        JPanel tablePanel = new RoundedPanel(40);
        tablePanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);

        // Data dummy 15 orang
        String[][] data = {
            {"24510001", "Ahmad Rizky Pratama", "Teknik Informatika"},
            {"24510002", "Siti Nurhaliza Putri", "Sistem Informasi"},
            {"24510003", "Budi Santoso", "Teknik Elektro"},
            {"24510004", "Dewi Kartika Sari", "Manajemen"},
            {"24510005", "Eko Prasetyo", "Akuntansi"},
            {"24510006", "Fitri Handayani", "Teknik Sipil"},
            {"24510007", "Gunawan Setiawan", "Teknik Mesin"},
            {"24510008", "Hana Maulida", "Psikologi"},
            {"24510009", "Indra Kurniawan", "Ekonomi"},
            {"24510010", "Joko Widodo", "Teknik Industri"},
            {"24510011", "Kartika Dewi", "Farmasi"},
            {"24510012", "Lukman Hakim", "Kedokteran"},
            {"24510013", "Maya Sari", "Hukum"},
            {"24510014", "Naufal Ahmad", "Arsitektur"},
            {"24510015", "Octavia Putri", "Desain Grafis"}
        };
        
        // Column headers
        String[] columns = {"NIM", "Nama", "Program Studi", "Status"};
        JScrollPane scrollPane = new DataScrollPane(columns, data);
        
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        tableWrapper.add(tablePanel, BorderLayout.CENTER);
        mainPanel.add(tableWrapper, gbc);

        wrapperPanel.add(mainPanel, BorderLayout.CENTER);
    }
    
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new App());
    }
}
