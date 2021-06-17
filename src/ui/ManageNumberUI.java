package ui;

import database.DatabaseConnectionHandler;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class ManageNumberUI extends JFrame {
    String[] columnNames = {"Manager ID", "Count"};

    Object[][] data;
    JTable table;
    private DatabaseConnectionHandler dbHandler;

    public ManageNumberUI(DatabaseConnectionHandler dbHandler) {
        super("Number of people per manager");
        this.dbHandler = dbHandler;
        data = dbHandler.getManageCounts();

    }

    public void showFrame() {

        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);
        this.setSize(1000, 600);

        contentPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Budget Amazon", TitledBorder.CENTER, TitledBorder.TOP));

        table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(700, 500));
        table.setFillsViewportHeight(true);
        this.add(new JScrollPane(table));


        // center the frame
        Dimension d = this.getToolkit().getScreenSize();
        Rectangle r = this.getBounds();
        this.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );

        // make the window visible
        this.setVisible(true);

    }
}
