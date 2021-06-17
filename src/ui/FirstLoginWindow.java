package ui;

import delegates.FirstLoginWindowDelegate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FirstLoginWindow extends JFrame implements ActionListener{
    private FirstLoginWindowDelegate delegate;

    public FirstLoginWindow() {
        super("Select Login Type");
    }

    public void showFrame(FirstLoginWindowDelegate delegate) {
        this.delegate = delegate;
        JButton loginButton1 = new JButton("Warehouse Login");
        loginButton1.setActionCommand("Warehouse");
        JButton loginButton2 = new JButton("Buyer/Seller Login");
        loginButton2.setActionCommand("B/S");

        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);

        // layout components using the GridBag layout manager
        GridBagLayout gb = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        contentPane.setLayout(gb);
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // place the login button 1
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(0, 10, 5, 10);
        c.anchor = GridBagConstraints.CENTER;
        gb.setConstraints(loginButton1, c);
        contentPane.add(loginButton1);

        // place the login button 2
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(5, 10, 10, 10);
        c.anchor = GridBagConstraints.CENTER;
        gb.setConstraints(loginButton2, c);
        contentPane.add(loginButton2);

        // register login button with action event handler
        loginButton1.addActionListener(this);
        loginButton2.addActionListener(this);

        // anonymous inner class for closing the window
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
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
        if (e.getActionCommand() == "Warehouse") {
            delegate.whichUser("Warehouse");
        } else if (e.getActionCommand() == "B/S") {
            delegate.whichUser("B/S");
        }
    }
}
