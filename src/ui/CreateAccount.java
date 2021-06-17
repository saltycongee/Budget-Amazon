package ui;

import delegates.CreateAccountDelegate;
import delegates.FirstLoginWindowDelegate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * The class is only responsible for displaying and handling the login GUI.
 */
public class CreateAccount extends JFrame implements ActionListener {
    private static final int TEXT_FIELD_WIDTH = 10;

    // components of the login window
    private JTextField fname;
    private JTextField lname;
    private JTextField email;
    private JTextField phone;
    private JTextField creditCard;
    private JTextField postalCode;
    private JTextField buildingNumber;
    private JPasswordField passwordField;


    // delegate
    private CreateAccountDelegate delegate;
    private FirstLoginWindowDelegate closeDelegate;

    public CreateAccount() {
        super("Create Account");
    }

    public void showFrame(CreateAccountDelegate delegate, FirstLoginWindowDelegate closeDelegate) {
        this.delegate = delegate;
        this.closeDelegate = closeDelegate;
        JLabel fNameLabel = new JLabel("First Name: ");
        JLabel lNameLabel = new JLabel("Last Name: ");
        JLabel emailLabel = new JLabel("Email: ");
        JLabel phoneLabel = new JLabel("Phone: ");
        JLabel creditCardLabel = new JLabel("Credit Card: ");
        JLabel postalCodeLabel = new JLabel("Postal Code: ");
        JLabel buildingNumberLabel = new JLabel("Building Number: ");
        JLabel passwordLabel = new JLabel("Password: ");
        fname = new JTextField(TEXT_FIELD_WIDTH);
        lname = new JTextField(TEXT_FIELD_WIDTH);
        email = new JTextField(TEXT_FIELD_WIDTH);
        phone = new JTextField(TEXT_FIELD_WIDTH);
        creditCard = new JTextField(TEXT_FIELD_WIDTH);
        postalCode = new JTextField(TEXT_FIELD_WIDTH);
        buildingNumber = new JTextField(TEXT_FIELD_WIDTH);
        passwordField = new JPasswordField(TEXT_FIELD_WIDTH);
        passwordField.setEchoChar('*');

        JButton createBuyer = new JButton("Create Buyer Account");
        createBuyer.setActionCommand("Create Buyer");

        JButton createSeller = new JButton("Create Seller Account");
        createSeller.setActionCommand("Create Seller");

        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);

        // layout components using the GridBag layout manager
        GridBagLayout gb = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        contentPane.setLayout(gb);
        contentPane.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));

        // place the username label
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(10, 10, 0, 0);
        gb.setConstraints(fNameLabel, c);
        contentPane.add(fNameLabel);

        // place the text field for the username
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(10, 0, 0, 10);
        gb.setConstraints(fname, c);
        contentPane.add(fname);

        // place the username label
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(9, 9, 0, 0);
        gb.setConstraints(lNameLabel, c);
        contentPane.add(lNameLabel);

        // place the text field for the username
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(9, 0, 0, 9);
        gb.setConstraints(lname, c);
        contentPane.add(lname);

        // place the username label
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(8, 8, 0, 0);
        gb.setConstraints(emailLabel, c);
        contentPane.add(emailLabel);

        // place the text field for the username
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(8, 0, 0, 8);
        gb.setConstraints(email, c);
        contentPane.add(email);

        // place the username label
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(7, 7, 0, 0);
        gb.setConstraints(phoneLabel, c);
        contentPane.add(phoneLabel);

        // place the text field for the username
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(7, 0, 0, 7);
        gb.setConstraints(phone, c);
        contentPane.add(phone);

        // place the username label
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(6, 6, 0, 0);
        gb.setConstraints(creditCardLabel, c);
        contentPane.add(creditCardLabel);

        // place the text field for the username
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(6, 0, 0, 6);
        gb.setConstraints(creditCard, c);
        contentPane.add(creditCard);

        // place the username label
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(5, 5, 0, 0);
        gb.setConstraints(postalCodeLabel, c);
        contentPane.add(postalCodeLabel);

        // place the text field for the username
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(5, 0, 0, 5);
        gb.setConstraints(postalCode, c);
        contentPane.add(postalCode);

        // place the username label
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(4, 4, 0, 0);
        gb.setConstraints(buildingNumberLabel, c);
        contentPane.add(buildingNumberLabel);

        // place the text field for the username
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(4, 0, 0, 4);
        gb.setConstraints(buildingNumber, c);
        contentPane.add(buildingNumber);

        // place password label
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(3, 3, 0, 0);
        gb.setConstraints(passwordLabel, c);
        contentPane.add(passwordLabel);

        // place the password field
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(3, 0, 0, 3);
        gb.setConstraints(passwordField, c);
        contentPane.add(passwordField);

        // place the login button
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(0, 2, 2, 2);
        c.anchor = GridBagConstraints.CENTER;
        gb.setConstraints(createBuyer, c);
        contentPane.add(createBuyer);

        // place the login button
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(0, 2, 2, 2);
        c.anchor = GridBagConstraints.CENTER;
        gb.setConstraints(createSeller, c);
        contentPane.add(createSeller);

        // register login button with action event handler
        createBuyer.addActionListener(this);
        createSeller.addActionListener(this);

        // anonymous inner class for closing the window
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                closeDelegate.whichUser("B/S");
            }
        });

        // size the window to obtain a best fit for the components
        this.pack();

        // center the frame
        Dimension d = this.getToolkit().getScreenSize();
        Rectangle r = this.getBounds();
        this.setLocation((d.width - r.width) / 2, (d.height - r.height) / 2);

        // make the window visible
        this.setVisible(true);

        // place the cursor in the text field for the username
        fname.requestFocus();
    }



    /**
     * ActionListener Methods
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == "Create Buyer") {
            delegate.createBuyerAccount((int)Math.floor(Math.random()*(9999 - 1000 + 1) + 1000), fname.getText(),
                    lname.getText(), email.getText(), Integer.parseInt(phone.getText()), creditCard.getText(),
                    postalCode.getText(), Integer.parseInt(buildingNumber.getText()),
                    String.valueOf(passwordField.getPassword()));
        } else if (e.getActionCommand() == "Create Seller") {
            delegate.createSellerAccount((int)Math.floor(Math.random()*(9999 - 1000 + 1) + 1000), fname.getText(),
                    lname.getText(), email.getText(), Integer.parseInt(phone.getText()), creditCard.getText(),
                    postalCode.getText(), Integer.parseInt(buildingNumber.getText()),
                    String.valueOf(passwordField.getPassword()));
        }
    }
}