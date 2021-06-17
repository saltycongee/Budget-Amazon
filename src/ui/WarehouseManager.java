package ui;

import delegates.FirstLoginWindowDelegate;
import delegates.WarehouseManagerDelegate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * The class is only responsible for displaying and handling the login GUI.
 */
public class WarehouseManager extends JFrame implements ActionListener {

    // delegate
    private WarehouseManagerDelegate delegate;
    private FirstLoginWindowDelegate closeDelegate;

    public WarehouseManager() {
        super("Warehouse Manager");
    }

    public void showFrame(WarehouseManagerDelegate delegate, FirstLoginWindowDelegate delegate2) {
        this.delegate = delegate;
        this.closeDelegate = delegate2;

        JButton items = new JButton("Items");
        items.setActionCommand("Items");
        JButton workers = new JButton("Workers");
        workers.setActionCommand("Workers");
        JButton warehouses = new JButton("Warehouses");
        warehouses.setActionCommand("Warehouses");

        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);

        // layout components using the GridBag layout manager
        GridBagLayout gb = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        contentPane.setLayout(gb);
        contentPane.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        // place the login button
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(5, 10, 0, 10);
        c.anchor = GridBagConstraints.CENTER;
        gb.setConstraints(items, c);
        contentPane.add(items);

        // place the login button
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(5, 10, 5, 10);
        c.anchor = GridBagConstraints.CENTER;
        gb.setConstraints(workers, c);
        contentPane.add(workers);

        // place the login button
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(5, 10, 10, 10);
        c.anchor = GridBagConstraints.CENTER;
        gb.setConstraints(warehouses, c);
        contentPane.add(warehouses);

        // register login button with action event handler
        items.addActionListener(this);
        workers.addActionListener(this);
        warehouses.addActionListener(this);

        // anonymous inner class for closing the window
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                closeDelegate.whichUser("Reset");
            }
        });

        // size the window to obtain a best fit for the components
        this.pack();

        // center the frame
        Dimension d = this.getToolkit().getScreenSize();
        Rectangle r = this.getBounds();
        this.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );

        // make the window visible
        this.setVisible(true);
    }

    /**
     * ActionListener Methods
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == "Items") {
            delegate.items();
        } else if (e.getActionCommand() == "Workers") {
            delegate.workers();
        } else if (e.getActionCommand() == "Warehouses") {
            delegate.warehouses();
        }
    }
}