package ui;

import delegates.LoginWindowDelegate;
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
public class LoginWindow extends JFrame implements ActionListener {
	private static final int TEXT_FIELD_WIDTH = 10;
	private static final int MAX_LOGIN_ATTEMPTS = 99;

	// running accumulator for login attempts
	private int loginAttempts;

	// components of the login window
	private JTextField usernameField;
	private JPasswordField passwordField;
	
	// delegate
	private LoginWindowDelegate delegate;
	private FirstLoginWindowDelegate closeDelegate;

	public LoginWindow() {
		super("User Login");
	}

	public void showFrame(LoginWindowDelegate delegate, FirstLoginWindowDelegate delegate2) {
		this.delegate = delegate;
		this.closeDelegate = delegate2;
		loginAttempts = 0;

		JLabel usernameLabel = new JLabel("Enter email: ");
		JLabel passwordLabel = new JLabel("Enter password: ");

		usernameField = new JTextField(TEXT_FIELD_WIDTH);
		passwordField = new JPasswordField(TEXT_FIELD_WIDTH);
		passwordField.setEchoChar('*');

		JButton loginButton1 = new JButton("Buyer Log In");
		loginButton1.setActionCommand("Buyer");
		JButton loginButton2 = new JButton("Seller Log In");
		loginButton2.setActionCommand("Seller");
		JButton loginButton3 = new JButton("Create Account");
		loginButton3.setActionCommand("Create");

		JPanel contentPane = new JPanel();
		this.setContentPane(contentPane);

		// layout components using the GridBag layout manager
		GridBagLayout gb = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();

		contentPane.setLayout(gb);
		contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// place the username label 
		c.gridwidth = GridBagConstraints.RELATIVE;
		c.insets = new Insets(10, 10, 5, 0);
		gb.setConstraints(usernameLabel, c);
		contentPane.add(usernameLabel);

		// place the text field for the username 
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(10, 0, 5, 10);
		gb.setConstraints(usernameField, c);
		contentPane.add(usernameField);

		// place password label
		c.gridwidth = GridBagConstraints.RELATIVE;
		c.insets = new Insets(0, 10, 10, 0);
		gb.setConstraints(passwordLabel, c);
		contentPane.add(passwordLabel);

		// place the password field 
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(0, 0, 10, 10);
		gb.setConstraints(passwordField, c);
		contentPane.add(passwordField);

		// place the login button
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(5, 10, 0, 10);
		c.anchor = GridBagConstraints.CENTER;
		gb.setConstraints(loginButton1, c);
		contentPane.add(loginButton1);

		// place the login button
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(5, 10, 5, 10);
		c.anchor = GridBagConstraints.CENTER;
		gb.setConstraints(loginButton2, c);
		contentPane.add(loginButton2);

		// place the login button
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(5, 10, 10, 10);
		c.anchor = GridBagConstraints.CENTER;
		gb.setConstraints(loginButton3, c);
		contentPane.add(loginButton3);

		// register login button with action event handler
		loginButton1.addActionListener(this);
		loginButton2.addActionListener(this);
		loginButton3.addActionListener(this);

		// anonymous inner class for closing the window
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				delegate2.whichUser("Reset");
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

		// place the cursor in the text field for the username
		usernameField.requestFocus();
	}
	
	public void handleLoginFailed() {
		loginAttempts++;
		passwordField.setText(""); // clear password field
		JOptionPane.showMessageDialog(new JFrame(),
				"Invalid Username or Password",
				"Login Error",
				JOptionPane.ERROR_MESSAGE);
	}
	
	public boolean hasReachedMaxLoginAttempts() {
		return (loginAttempts >= MAX_LOGIN_ATTEMPTS);
	}
	
	/**
	 * ActionListener Methods
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "Buyer") {
			delegate.buyerLogin(usernameField.getText(), String.valueOf(passwordField.getPassword()));
		} else if (e.getActionCommand() == "Seller") {
			delegate.sellerLogin(usernameField.getText(), String.valueOf(passwordField.getPassword()));
		} else if (e.getActionCommand() == "Create") {
			delegate.create();
		}
	}
}
