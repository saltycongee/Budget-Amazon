package ui;

import database.DatabaseConnectionHandler;
import delegates.FirstLoginWindowDelegate;
import model.Item;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ItemsUI extends JFrame implements ActionListener {
    String[] columnNames = {"Item ID", "Name", "Count", "Weight", "Price", "Seller ID", "Warehouse ID"};
    Object[][] data;
    JTable table;
    private DatabaseConnectionHandler dbHandler;
    private FirstLoginWindowDelegate delegate;
    public ItemsUI(DatabaseConnectionHandler dbHandler) {
        super("Items UI");
        this.dbHandler = dbHandler;
        this.data = this.dbHandler.itemList();
    }

    public void showFrame(FirstLoginWindowDelegate delegate) {
        this.delegate = delegate;

        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);
        this.setSize(1000, 600);

        contentPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Budget Amazon", TitledBorder.CENTER, TitledBorder.TOP));



        table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(700, 500));
        table.setFillsViewportHeight(true);
        this.add(new JScrollPane(table));

        JButton details = new JButton("Details");
        details.addActionListener(this);
        contentPane.add(details);
        // anonymous inner class for closing the window
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                delegate.whichUser("Warehouse");
            }
        });

        // center the frame
        Dimension d = this.getToolkit().getScreenSize();
        Rectangle r = this.getBounds();
        this.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );

        // make the window visible
        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int index = table.getSelectedRow();
        Item item = new Item((String)data[index][1],
                (int)data[index][0],
                (int)data[index][2],
                (int)data[index][3],
                (double)data[index][4],
                (int)data[index][5],
                (int)data[index][6]);
        ItemDetailUI itemDetailUI = new ItemDetailUI(dbHandler,item);
        itemDetailUI.showFrame(delegate);
        this.dispose();
    }
}