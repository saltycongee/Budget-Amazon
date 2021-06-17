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
import java.util.Arrays;
import java.util.List;

public class BuyerUI extends JFrame implements ActionListener{
    String[] columnNames = {"Item_ID", "Item_Name", "Count", "Weight", "Price", "Seller_ID", "Warehouse_ID"};
    List<String> columnList = new ArrayList<>(Arrays.asList(columnNames));
    Integer[] columnOrder = {2, 1, 3, 4, 5, 6, 7};
    Object[][] data = null;
    JTable table = null;
    Customer customer;
    JTextField filterArea;
    // delegate
    private BuyerUIDelegate delegate;
    private FirstLoginWindowDelegate closeDelegate;
    private DatabaseConnectionHandler dbHandler;

    public BuyerUI(DatabaseConnectionHandler dbHandler, Customer customer) {
        super("Buyer UI");
        this.dbHandler = dbHandler;
        data = dbHandler.itemList();
        table = new JTable(data, columnNames);
        this.customer = customer;
    }

    public void showFrame(BuyerUIDelegate delegate, FirstLoginWindowDelegate delegate2) {
        this.delegate = delegate;
        this.closeDelegate = delegate2;

        filterArea = new JTextField(10);

        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);
        this.setSize(1000, 600);

        JButton purchaseButton = new JButton("Purchase");
        purchaseButton.setActionCommand("Purchase");
        JButton filterButton = new JButton("Filter");
        filterButton.setActionCommand("Filter");

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
        Dimension size = purchaseButton.getPreferredSize();
        purchaseButton.setBounds(0, 0, size.width, size.height);
        contentPane.add(filterButton);
        contentPane.add(filterArea);
        contentPane.add(purchaseButton);
        purchaseButton.addActionListener(this);
        filterButton.addActionListener(this);

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                delegate2.whichUser("Reset");
            }
        });

        // center the frame
        Dimension d = this.getToolkit().getScreenSize();
        Rectangle r = this.getBounds();
        this.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );

        // make the window visible
        this.setVisible(true);

    }


    public void updateTable() {
        data = dbHandler.itemList();
        this.showFrame(delegate, closeDelegate);
    }

    /**
     * ActionListener Methods
     */
    @Override
    public void actionPerformed(ActionEvent e) {
//        if (e.getActionCommand() == "Purchase") {
//
//            if ((int)(data[table.getSelectedRow()][2]) > 0){
//                if ((int) (data[table.getSelectedRow()][2]) == 1){
//                    dbHandler.removeItem(table.getSelectedRow());
//                }
//            }
//            Object[] purchaseData = null;
//            for (int k = 0; k < data.length; k++) {
//                purchaseData[k] = data[table.getSelectedRow()][k];
//            }
//            delegate.purchase(purchaseData);
//        }
        if(e.getActionCommand() == "Purchase") {
            delegate.purchase();
            PurchaseUI purchaseUI = new PurchaseUI(dbHandler, customer, this);
            purchaseUI.showFrame();
        } else if (e.getActionCommand() == "Filter") {
            String str = filterArea.getText();
            String[] parts = str.split(",");
            for (int index = 0; index < parts.length; index++) {
                parts[index] = parts[index].trim();
            }
            ArrayList<String> filterdList = new ArrayList<>(Arrays.asList(parts));
            ArrayList<Integer> sqlNumbers = new ArrayList<>();
            for (int k = 0; k < 7; k++) {
                if (filterdList.contains(columnList.get(k))) {
                    sqlNumbers.add(columnOrder[k]);
                }
            }
            delegate.filter(str, parts, sqlNumbers);
        }
    }
}
