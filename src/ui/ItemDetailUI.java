package ui;

import database.DatabaseConnectionHandler;
import delegates.FirstLoginWindowDelegate;
import model.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ItemDetailUI extends JFrame implements ActionListener {
    // Objects we describe
    Item item;
    SellerAccount seller;
    CreditCardName creditCardName;
    PostalCodeLocation postalCodeLocation;
    Warehouse warehouse;
    // util stuff
    private DatabaseConnectionHandler dbHandler;
    private FirstLoginWindowDelegate delegate;

    // components of the details
    private JLabel itemID;
    private JLabel itemName;
    private JLabel itemCount;
    private JLabel itemPrice;
    private JLabel fname;
    private JLabel lname;
    private JLabel email;
    private JLabel wareHouseID;
    private JLabel wareHousePostalCode;
    private JLabel wareHouseStreetandNumber;

    public ItemDetailUI(DatabaseConnectionHandler dbHandler, Item item) {
        super("Items UI : "+item.getItem_Name());
        this.dbHandler = dbHandler;
        this.item = item;
        this.seller = dbHandler.getSellerAccount(item);
        this.creditCardName = dbHandler.getCreditCardName(seller.getCreditCardInfo());
        this.warehouse = dbHandler.getWareHouse(item);
        this.postalCodeLocation = dbHandler.getPostalCodeLocation(warehouse.getPostalCode());
    }

    public void showFrame(FirstLoginWindowDelegate delegate) {
        this.delegate = delegate;

        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);
        this.setSize(500, 400);

        contentPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Budget Amazon", TitledBorder.CENTER, TitledBorder.TOP));
        // layout components using the GridBag layout manager
        GridBagLayout gb = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        contentPane.setLayout(gb);
        contentPane.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));
        //Add labels for item details
        itemID = new JLabel("Item ID : "+item.getItemID());
        itemName = new JLabel("Item Name : " + item.getItem_Name());
        itemCount = new JLabel("Item Count : "+item.getCount());
        itemPrice = new JLabel("Item Price : $"+item.getPrice());
        fname = new JLabel("Seller Name : "+creditCardName.getName());
        lname = new JLabel("Seller Last Name : "+creditCardName.getlName());
        email = new JLabel("Seller email : "+seller.getEmail());
        wareHouseID = new JLabel("Warehouse ID : "+item.getWarehouseID());
        wareHousePostalCode = new JLabel("Warehouse Postal Code : "+ warehouse.getPostalCode());
        wareHouseStreetandNumber = new JLabel("Warehouse Address : " + postalCodeLocation.getCity()
                +", " +postalCodeLocation.getStreet()+" " + warehouse.getBuildingNumber());
        // place the Item ID label
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(10, 10, 0, 0);
        gb.setConstraints(itemID, c);
        contentPane.add(itemID);

        // place the text field for the itemName
        gb.setConstraints(itemName, c);
        contentPane.add(itemName);

        // place the text field for the itemCount
        gb.setConstraints(itemCount, c);
        contentPane.add(itemCount);

        // place the text field for the itemPrice
        gb.setConstraints(itemPrice, c);
        contentPane.add(itemPrice);

        // place the text field for the fname
        gb.setConstraints(fname, c);
        contentPane.add(fname);

        // place the text field for the lname
        gb.setConstraints(lname, c);
        contentPane.add(lname);

        // place the text field for the email
        gb.setConstraints(email, c);
        contentPane.add(email);

        // place the text field for the wareHouseID
        gb.setConstraints(wareHouseID, c);
        contentPane.add(wareHouseID);

        // place the text field for the wareHousePostalCode
        gb.setConstraints(wareHousePostalCode, c);
        contentPane.add(wareHousePostalCode);

        // place the text field for the wareHouseStreetandNumber
        gb.setConstraints(wareHouseStreetandNumber, c);
        contentPane.add(wareHouseStreetandNumber);


        // anonymous inner class for closing the window
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                delegate.whichUser("ItemsUI");
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
    }
}
