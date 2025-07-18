package src.fragments;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import src.custom_listener.OnSelectedRow;

public class DataScrollPane extends JScrollPane {
    private DefaultTableModel model;
    private JTable table;
    public OnSelectedRow onSelectedRow;
    

    public DataScrollPane(String[] columns, Object[][] data, OnSelectedRow onSelectedRow) {
        this.onSelectedRow = onSelectedRow;
        this.model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.table = new JTable(model);
        this.table.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String key = table.getValueAt(selectedRow, 0).toString();

                onSelectedRow.onSelected(key);
            }
            this.table.clearSelection();
        });
        this.setViewportView(table);

        // Table styling
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.setRowHeight(35);
        table.setShowGrid(false);
        table.setBackground(Color.WHITE);
        table.setBorder(BorderFactory.createEmptyBorder());
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(false);
        table.setDragEnabled(false);
        table.getTableHeader().setReorderingAllowed(false);

        
        // Header styling
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 13));
        header.setBorder(BorderFactory.createEmptyBorder());
        header.setPreferredSize(new Dimension(0, 50));
    }
}
