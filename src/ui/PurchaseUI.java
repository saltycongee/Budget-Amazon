package ui;

import database.DatabaseConnectionHandler;
import delegates.CreateAccountDelegate;
import model.Customer;
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
public class PurchaseUI extends JFrame implements ActionListener {
    private static final int TEXT_FIELD_WIDTH = 10;

    // components of the login window
    private JComboBox itemID;
    private JTextField itemCount;



    // delegate
    private CreateAccountDelegate delegate;
    private DatabaseConnectionHandler dbhandler;

    //Other
    private Customer customer;
    private BuyerUI owner;
    public PurchaseUI(DatabaseConnectionHandler dbHandler, Customer customer, BuyerUI owner) {
        super("Purchase Item");
        this.dbhandler = dbHandler;
        this.customer = customer;
        this.owner = owner;
    }

    public void showFrame() {

        JLabel itemIDLabel = new JLabel("Item ID:");
        JLabel itemCountLabel = new JLabel("item Count: ");

        itemID = new JComboBox(dbhandler.getItemID());
        itemCount = new JTextField(TEXT_FIELD_WIDTH);


        JButton purchaseItem = new JButton("Purchase Item");
        purchaseItem.setActionCommand("Purchase Item");

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


        // place the creatItem button
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(0, 1, 1, 1);
        c.anchor = GridBagConstraints.CENTER;
        gb.setConstraints(purchaseItem, c);
        contentPane.add(purchaseItem);

        // register login button with action event handler
        purchaseItem.addActionListener(this);

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
        int pitemCount = Integer.parseInt(itemCount.getText());
        int pitemID = (int)(itemID.getSelectedItem());
        int pitemStockCount = dbhandler.getItemCount(pitemID);
        if (pitemStockCount <= pitemCount) {
            dbhandler.modifyItem(pitemID, pitemStockCount);
            dbhandler.removeItem(pitemID);
        } else {
            dbhandler.modifyItem(pitemID, pitemCount);
        }

        owner.updateTable();
        this.dispose();
    }


}