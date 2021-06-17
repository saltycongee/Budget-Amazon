package ui;

import database.DatabaseConnectionHandler;
import delegates.BuyerUIDelegate;
import delegates.FirstLoginWindowDelegate;

import model.Customer;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class BuyerFilterUI extends JFrame{
    String[] columnNames = null;
    Object[][] data = null;
    JTable table = null;
    // delegate
    private BuyerUIDelegate delegate;
    private FirstLoginWindowDelegate closeDelegate;
    private DatabaseConnectionHandler dbHandler;

    public BuyerFilterUI(DatabaseConnectionHandler dbHandler, String str, String[] cols, ArrayList<Integer> colNumbers) {
        super("Buyer UI");
        columnNames = cols;
        this.dbHandler = dbHandler;
        data = dbHandler.filteredItemList(str, colNumbers);
        table = new JTable(data, columnNames);
    }

    public void showFrame(BuyerUIDelegate delegate, FirstLoginWindowDelegate delegate2) {
        this.delegate = delegate;
        this.closeDelegate = delegate2;

        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);
        this.setSize(1000, 600);

        contentPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Budget Amazon", TitledBorder.CENTER, TitledBorder.TOP));



        table.setPreferredScrollableViewportSize(new Dimension(700, 400));
        table.setFillsViewportHeight(true);
        this.add(new JScrollPane(table));
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 7;
            }
        };

        table.setModel(tableModel);

//        this.addWindowListener(new WindowAdapter() {
//            public void windowClosing(WindowEvent e) {
//                delegate2.whichUser("Reset");
//            }
//        });

        // center the frame
        Dimension d = this.getToolkit().getScreenSize();
        Rectangle r = this.getBounds();
        this.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );

        // make the window visible
        this.setVisible(true);

    }
}



