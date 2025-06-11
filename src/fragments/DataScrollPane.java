package src.fragments;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class DataScrollPane extends JScrollPane {
    private DefaultTableModel model;
    private JTable table;

    public DataScrollPane(String[] columns, Object[][] data) {
        this.model = new DefaultTableModel(data, columns);
        this.table = new JTable(model);
        this.setViewportView(table);

        // Table styling
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.setRowHeight(35);
        table.setShowGrid(false);
        table.setBackground(Color.WHITE);
        table.setBorder(BorderFactory.createEmptyBorder());
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(false);
        
        // Header styling
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 13));
        header.setBorder(BorderFactory.createEmptyBorder());
        header.setPreferredSize(new Dimension(0, 50));
    }
}
