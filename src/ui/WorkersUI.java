package ui;

import database.DatabaseConnectionHandler;
import delegates.FirstLoginWindowDelegate;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WorkersUI extends JFrame implements  ActionListener{
    String[] columnNames = {"Worker ID", "Manager ID", "Name", "Phone Number", "Wage"};
    Object[][] data;
    JTable table;
    private DatabaseConnectionHandler dbHandler;

    private FirstLoginWindowDelegate delegate;
    public WorkersUI(DatabaseConnectionHandler dbHandler) {
        super("Workers UI");
        this.dbHandler = dbHandler;
        data = dbHandler.getWorkers();
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

        JButton managed = new JButton("Managed");
        managed.addActionListener(this);
        contentPane.add(managed);
        managed.setActionCommand("Managed");

        JButton managedNum = new JButton("Num Per Manager");
        managedNum.addActionListener(this);
        contentPane.add(managedNum);
        managedNum.setActionCommand("managedNum");

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
        if (e.getActionCommand().equals("Managed")){
            int mid = (int) data[table.getSelectedRow()][0];
            String name = (String) data[table.getSelectedRow()][2];
            ManagedUI managedUI = new ManagedUI(dbHandler, mid, name);
            managedUI.showFrame();
        } else {
            ManageNumberUI ui = new ManageNumberUI(dbHandler);
            ui.showFrame();
        }

    }
}
