package ui;

import database.DatabaseConnectionHandler;
import delegates.CreateAccountDelegate;
import model.Item;
import model.SellerAccount;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;

/**
 * The class is only responsible for displaying and handling the login GUI.
 */
public class AddItem extends JFrame implements ActionListener {
    private static final int TEXT_FIELD_WIDTH = 10;

    // components of the login window
    private JTextField itemID;
    private JTextField itemName;
    private JTextField itemCount;
    private JTextField itemWeight;
    private JTextField itemPrice;
    private JComboBox warehouseID;



    // delegate
    private CreateAccountDelegate delegate;
    private DatabaseConnectionHandler dbhandler;

    //Other
    private SellerAccount sellerAccount;
    private SellerUI owner;
    public AddItem(DatabaseConnectionHandler dbHandler, SellerAccount sellerAccount, SellerUI owner) {
        super("Add Item");
        this.dbhandler = dbHandler;
        this.sellerAccount = sellerAccount;
        this.owner = owner;
    }

    public void showFrame() {

        JLabel itemIDLabel = new JLabel("Item ID:");
        JLabel itemNameLabel = new JLabel("Item Name: ");
        JLabel itemCountLabel = new JLabel("item Count: ");
        JLabel itemWeightLabel = new JLabel("Item Weight: ");
        JLabel itemPriceLabel = new JLabel("Item Price: ");
        JLabel warehouseIDLabel = new JLabel("Warehouse ID: ");
        itemID = new JTextField(TEXT_FIELD_WIDTH);
        itemName = new JTextField(TEXT_FIELD_WIDTH);
        itemCount = new JTextField(TEXT_FIELD_WIDTH);
        itemWeight = new JTextField(TEXT_FIELD_WIDTH);
        itemPrice = new JTextField(TEXT_FIELD_WIDTH);
        warehouseID = new JComboBox(dbhandler.getWarehouseID());

        JButton createItem = new JButton("Create Item");
        createItem.setActionCommand("Create Item");

        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);

        // layout components using the GridBag layout manager
        GridBagLayout gb = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        contentPane.setLayout(gb);
        contentPane.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));

        // place the ItemID label
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(10, 10, 0, 0);
        gb.setConstraints(itemIDLabel, c);
        contentPane.add(itemIDLabel);

        // place the text field for the itemID
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(10, 0, 0, 10);
        gb.setConstraints(itemID, c);
        contentPane.add(itemID);

        // place the itemName label
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(9, 9, 0, 0);
        gb.setConstraints(itemNameLabel, c);
        contentPane.add(itemNameLabel);

        // place the text field for the itemName
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(9, 0, 0, 9);
        gb.setConstraints(itemName, c);
        contentPane.add(itemName);

        // place the itemCount label
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(8, 8, 0, 0);
        gb.setConstraints(itemCountLabel, c);
        contentPane.add(itemCountLabel);

        // place the text field for the itemCount
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(8, 0, 0, 8);
        gb.setConstraints(itemCount, c);
        contentPane.add(itemCount);

        // place the itemWeight label
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(7, 7, 0, 0);
        gb.setConstraints(itemWeightLabel, c);
        contentPane.add(itemWeightLabel);

        // place the text field for the itemWeight
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(7, 0, 0, 7);
        gb.setConstraints(itemWeight, c);
        contentPane.add(itemWeight);

        // place the itemPrice label
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(6, 6, 0, 0);
        gb.setConstraints(itemPriceLabel, c);
        contentPane.add(itemPriceLabel);

        // place the text field for the itemPrice
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(6, 0, 0, 6);
        gb.setConstraints(itemPrice, c);
        contentPane.add(itemPrice);

        // place the warehouseID label
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(5, 5, 0, 0);
        gb.setConstraints(warehouseIDLabel, c);
        contentPane.add(warehouseIDLabel);

        // place the jComboBox for the warehouseID
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(5, 0, 0, 5);
        gb.setConstraints(warehouseID, c);
        contentPane.add(warehouseID);


        // place the creatItem button
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(0, 1, 1, 1);
        c.anchor = GridBagConstraints.CENTER;
        gb.setConstraints(createItem, c);
        contentPane.add(createItem);

        // register login button with action event handler
        createItem.addActionListener(this);

        // anonymous inner class for closing the window
//        this.addWindowListener(new WindowAdapter() {
//            public void windowClosing(WindowEvent e) {
//                System.exit(0);
//            }
//        });

        // size the window to obtain a best fit for the components
        this.pack();

        // center the frame
        Dimension d = this.getToolkit().getScreenSize();
        Rectangle r = this.getBounds();
        this.setLocation((d.width - r.width) / 2, (d.height - r.height) / 2);

        // make the window visible
        this.setVisible(true);

        // place the cursor in the text field for the username
//        usernameField.requestFocus();
    }



    /**
     * ActionListener Methods
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Item item = new Item(itemName.getText(),
                Integer.parseInt(itemID.getText()),
                Integer.parseInt(itemCount.getText()),
                Integer.parseInt(itemWeight.getText()),
                Double.parseDouble(itemPrice.getText()),
                sellerAccount.getSellerID(),
                (int)(warehouseID.getSelectedItem()));
        if (dbhandler.getItemCount(item) != 0) {
            dbhandler.modifyItem(item);
        } else {
            dbhandler.addItem(item);
        }
        owner.updateTable();
        this.dispose();
    }
}