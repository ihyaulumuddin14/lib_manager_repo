package src;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import src.components.*;
import src.pages.BukuFormPage;
import src.pages.MahasiswaFormPage;
import src.pages.PeminjamanFormPage;
import src.pages.RiwayatPage;

public class App extends JFrame {
    JPanel wrapperPanel = new JPanel();
    JPanel sideBarPanel = new JPanel();
    JPanel mainPanel = new JPanel();
    String page = "mahasiswa";
    final String DEFAULT_BG_COLOR = "#868e96";
    final String DEFAULT_NAV_BTN_COLOR = "#e9ecef";
    final String DEFAULT_NAV_BTN_TEXT_COLOR = "#343a40";
    final String SELECTED_NAV_BTN_COLOR = "#343a40";
    final String SELECTED_NAV_BTN_TEXT_COLOR = "#e9ecef";


    public App() {
        this.setTitle("Library Manager Application");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(Color.decode(DEFAULT_BG_COLOR));
        this.setSize(1920, 1080);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        wrapperPanel.setLayout(new BorderLayout());
        createSideBarPanel();
        updateUI();
        this.add(wrapperPanel);

        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void updateUI() {
        wrapperPanel.remove(mainPanel);

        switch (page) {
            case "mahasiswa" -> mainPanel = new MahasiswaFormPage();
            case "buku" -> mainPanel = new BukuFormPage();
            case "peminjaman" -> mainPanel = new PeminjamanFormPage();
            case "riwayat peminjaman" -> mainPanel = new RiwayatPage();
            default -> {}
        }

        wrapperPanel.add(mainPanel, BorderLayout.CENTER);
        wrapperPanel.revalidate();
        wrapperPanel.repaint();
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
                    page = ((JButton) e.getSource()).getText().toLowerCase();
                    updateUI();
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
    
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new App());
    }
}
