package ui;

import delegates.FirstLoginWindowDelegate;
import database.DatabaseConnectionHandler;
import model.Warehouse;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WarehouseInfo extends JFrame implements ActionListener {
    String[] columnNames = {"Warehouse ID", "Postal Code", "Building Number"};
    Object[][] data = null;
    JTable table = null;
    Warehouse warehouse;
    private FirstLoginWindowDelegate delegate;
    private DatabaseConnectionHandler dbhandler;
    public WarehouseInfo(DatabaseConnectionHandler dbhandler) {
        super("Warehouse Info");
        this.dbhandler = dbhandler;
        //this.warehouse = warehouse;
        data = dbhandler.getWarehouses();
        table = new JTable(data, columnNames);
    }

    public void showFrame(FirstLoginWindowDelegate delegate) {
        this.delegate = delegate;

        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);
        this.setSize(1000, 600);

        contentPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Budget Amazon", TitledBorder.CENTER, TitledBorder.TOP));



        JTable table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(700, 500));
        table.setFillsViewportHeight(true);
        this.add(new JScrollPane(table));
        JButton details = new JButton("Division");
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
        DivisionUI ui = new DivisionUI(dbhandler);
        ui.showFrame();
    }
}
