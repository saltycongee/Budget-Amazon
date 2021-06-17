package ui;

import database.DatabaseConnectionHandler;
import delegates.FirstLoginWindowDelegate;
import delegates.SellerUIDelegate;
import model.SellerAccount;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SellerUI extends JFrame implements ActionListener{
    String[] columnNames = {"Item ID", "Name", "Count", "Weight", "Price", "Seller ID", "Warehouse ID"};
    Object[][] data;
    private DatabaseConnectionHandler dbHandler;
    // delegate
    private SellerUIDelegate delegate;
    private FirstLoginWindowDelegate closeDelegate;
    private SellerAccount sellerAccount;
    private JTable table;
    public SellerUI(DatabaseConnectionHandler dbHandler, SellerAccount sellerAccount) {
        super("Seller UI");
        this.dbHandler = dbHandler;
        data = dbHandler.itemList();
        this.sellerAccount = sellerAccount;
    }

    public void showFrame(SellerUIDelegate delegate, FirstLoginWindowDelegate closeDelegate) {
        this.delegate = delegate;
        this.closeDelegate = closeDelegate;
        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);
        this.setSize(1000, 600);

        JButton addListing = new JButton("Add Listing");
        addListing.setActionCommand("Add");
//
//        // layout components using the GridBag layout manager
//        GridBagLayout gb = new GridBagLayout();
//        GridBagConstraints c = new GridBagConstraints();
//
//        contentPane.setLayout(gb);
//        contentPane.setBorder(BorderFactory.createEmptyBorder(400, 800, 400, 800));
        //JFrame frame = new JFrame();
        contentPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Budget Amazon", TitledBorder.CENTER, TitledBorder.TOP));



        table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(700, 500));
        table.setFillsViewportHeight(true);
        this.add(new JScrollPane(table));
        contentPane.add(addListing);
        addListing.addActionListener(this);
//        TableCellRenderer buttonRenderer = new JTableButtonRenderer();
//        table.getColumn("Purchase").setCellRenderer(buttonRenderer);
//        table.getColumn("Purchase").setCellRenderer(buttonRenderer);

//        c.gridwidth = GridBagConstraints.REMAINDER;
//        c.insets = new Insets(300, 600, 300, 600);
//        c.anchor = GridBagConstraints.CENTER;
//        gb.setConstraints(table, c);
//        contentPane.add(table);

//        // place the login button
//        c.gridwidth = GridBagConstraints.REMAINDER;
//        c.insets = new Insets(5, 10, 5, 10);
//        c.anchor = GridBagConstraints.CENTER;
//        gb.setConstraints(loginButton1, c);
//        contentPane.add(loginButton1);
//
//        // place the login button
//        c.gridwidth = GridBagConstraints.REMAINDER;
//        c.insets = new Insets(5, 10, 10, 10);
//        c.anchor = GridBagConstraints.CENTER;
//        gb.setConstraints(loginButton2, c);
//        contentPane.add(loginButton2);
//
//        // register login button with action event handler
//        loginButton1.addActionListener(this);
//        loginButton2.addActionListener(this);
//
        // anonymous inner class for closing the window
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                closeDelegate.whichUser("Reset");
            }
        });

        // size the window to obtain a best fit for the components
        //this.pack();

        // center the frame
        Dimension d = this.getToolkit().getScreenSize();
        Rectangle r = this.getBounds();
        this.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );

        // make the window visible
        this.setVisible(true);

    }

    private static class JTableButtonRenderer implements TableCellRenderer {
        @Override public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JButton button = (JButton)value;
            return button;
        }
    }
    public void updateTable(){
        data = dbHandler.itemList();
        this.showFrame(delegate, closeDelegate);
    }
    /**
     * ActionListener Methods
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == "Add") {
            delegate.addListing();
            AddItem item = new AddItem(dbHandler, sellerAccount, this);
            item.showFrame();
        }
    }
}
