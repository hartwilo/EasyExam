/**
 * 
 */
package GUI;

/**
 * @author bachir
 *
 */
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Window.Type;

public class LoginFrame extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Container container = getContentPane();
	JLabel userLabel = new JLabel("USERNAME");
	JLabel passwordLabel = new JLabel("PASSWORD");
	JTextField userTextField = new JTextField();
	JPasswordField passwordField = new JPasswordField();
	JButton loginButton = new JButton("LOGIN");
	JButton resetButton = new JButton("RESET");
	JCheckBox showPassword = new JCheckBox("Password zeigen");



	ImageIcon logo = new ImageIcon("logo1.png");
	private final JLabel lblPic = new JLabel("pic");

	public LoginFrame() {
		setType(Type.UTILITY);
		getContentPane().setBackground(new Color(153, 204, 204));
		setTitle("EasyExam");
		setLocationRelativeTo(null);
		setLayoutManager();
		setLocationAndSize();
		addComponentsToContainer();
		addActionEvent();
		lblPic.setText("");
		lblPic.setIcon(logo);

	}

	public void setLayoutManager() {
		container.setLayout(null);
	}

	public void setLocationAndSize() {
		userLabel.setBounds(50, 150, 100, 30);
		passwordLabel.setBounds(50, 220, 100, 30);
		userTextField.setBounds(150, 150, 150, 30);
		passwordField.setBounds(150, 220, 150, 30);
		showPassword.setBounds(150, 250, 150, 30);
		loginButton.setBounds(50, 364, 100, 30);
		resetButton.setBounds(200, 364, 100, 30);

	}

	public void addComponentsToContainer() {
		container.add(userLabel);
		container.add(passwordLabel);
		container.add(userTextField);
		container.add(passwordField);
		container.add(showPassword);
		container.add(loginButton);
		container.add(resetButton);
		lblPic.setBounds(150, 10, 150, 130);

		getContentPane().add(lblPic);
	}

	public void addActionEvent() {
		loginButton.addActionListener(this);
		resetButton.addActionListener(this);
		showPassword.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		// Coding Part of LOGIN button
		if (e.getSource() == loginButton) {
			String userText;
			String pwdText;
			userText = userTextField.getText();
			pwdText = passwordField.getText();
			if (userText.equalsIgnoreCase("User") && pwdText.equalsIgnoreCase("12345")) {
				JOptionPane.showMessageDialog(this, "Login ist erfolgreich");
				Startseite start = new Startseite();
				start.setVisible(true);
				start.setExtendedState(JFrame.MAXIMIZED_BOTH); 
				start.setLocationRelativeTo(null);
				start.setTitle("EasyExam");
			} else {
				JOptionPane.showMessageDialog(this,
						"falsche Username oder Password,Bitte richtige Username oder Password eingeben");
			}

		}
		// Coding Part of RESET button
		if (e.getSource() == resetButton) {
			userTextField.setText("");
			passwordField.setText("");
		}
		// Coding Part of showPassword JCheckBox
		if (e.getSource() == showPassword) {
			if (showPassword.isSelected()) {
				passwordField.setEchoChar((char) 0);
			} else {
				passwordField.setEchoChar('*');
			}

		}
	}
}
